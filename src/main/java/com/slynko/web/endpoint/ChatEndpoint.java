package com.slynko.web.endpoint;

import com.slynko.db.model.ChatMessage;
import com.slynko.web.json.ChatMessageDecoder;
import com.slynko.web.json.ChatMessageEncoder;

import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint(value="/chat/{room}", encoders = ChatMessageEncoder.class, decoders = ChatMessageDecoder.class)
public class ChatEndpoint {
    private final Logger log = Logger.getLogger(getClass().getName());
    private static Set<Session> sessionsSet = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void open(final Session session, @PathParam("room") final String room) {
        sendIsConnectedMessageToAll(session, room);
        sessionsSet.add(session);
        log.info("session opened and bound to room: " + room);
    }

    @OnMessage
    public void onMessage(final Session session, final ChatMessage chatMessage) {
        String room = (String) session.getUserProperties().get("room");
        try {
            for (Session s : sessionsSet) {
                if (s.isOpen()
                        && room.equals(s.getUserProperties().get("room"))) {
                    s.getBasicRemote().sendObject(chatMessage);
                }
            }
        } catch (IOException | EncodeException e) {
            log.log(Level.WARNING, "onMessage failed", e);
        }
    }

    private void sendIsConnectedMessageToAll(final Session session, final String room) {
        ChatMessage isConnectedMessage = getIsConnectedMessage(
                session.getRequestParameterMap().get("nickname")
                        .get(0));
        session.getUserProperties().put("room", room);
        onMessage(session, isConnectedMessage);
    }

    private ChatMessage getIsConnectedMessage(String connectedUserName) {
        ChatMessage isConnectedMessage = new ChatMessage();
        isConnectedMessage.setMessage(connectedUserName + " is connected.");
        isConnectedMessage.setReceived(new Date());
        isConnectedMessage.setSender("");
        return isConnectedMessage;
    }
}