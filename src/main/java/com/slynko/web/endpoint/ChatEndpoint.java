package com.slynko.web.endpoint;

import com.slynko.db.model.ChatMessage;
import com.slynko.web.json.ChatMessageDecoder;
import com.slynko.web.json.ChatMessageEncoder;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint(value="/chat/{room}/{nickname}", encoders = ChatMessageEncoder.class, decoders = ChatMessageDecoder.class)
public class ChatEndpoint {
    private final Logger log = Logger.getLogger(getClass().getName());
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void open(final Session session, @PathParam("room") final String room,
                     @PathParam("nickname") final String nickname) {
        ChatMessage hasConnectedMessage = getHasConnectedMessage(nickname);
        sendMessageToAll(session, room, hasConnectedMessage);
        sessions.add(session);
        log.info("Session opened and bound to room: " + room);
    }

    @OnMessage
    public void onMessage(final Session session, final ChatMessage chatMessage) {
        String room = (String) session.getUserProperties().get("room");
        try {
            for (Session s : sessions) {
                if (s.isOpen()
                        && room.equals(s.getUserProperties().get("room"))) {
                    s.getBasicRemote().sendObject(chatMessage);
                }
            }
        } catch (IOException | EncodeException e) {
            log.log(Level.SEVERE, "onMessage failed", e);
        }
    }

    @OnClose
    public void close(final Session session, @PathParam("room") final String room,
                      @PathParam("nickname") final String nickname) {
        sessions.remove(session);
        ChatMessage hasDisconnectedMessage = getHasDisconnectedMessage(nickname);
        sendMessageToAll(session, room, hasDisconnectedMessage);
        log.info("Session closed and unbound to room: " + room);
    }

    @OnError
    public void error(Throwable t) {
        log.log(Level.SEVERE, "Connection error.");
        throw new RuntimeException(t);
    }

    public static List<String> getLoggedInUsersByRoom(String room) {
        List<String> loggedInUsers = new ArrayList<>();
        for (Session s : sessions) {
            if(room.equals(s.getUserProperties().get("room"))) {
                loggedInUsers.add(s.getRequestParameterMap().get("nickname").get(0));
            }
        }
        return loggedInUsers;
    }

    private void sendMessageToAll(final Session session, final String room, ChatMessage message) {
        session.getUserProperties().put("room", room);
        onMessage(session, message);
    }

    private ChatMessage getHasConnectedMessage(String connectedUserName) {
        ChatMessage hasConnectedMessage = initializeChatMessage();
        hasConnectedMessage.setSender(connectedUserName);
        hasConnectedMessage.setMessage(connectedUserName + " has connected.");
        hasConnectedMessage.setConnected(true);
        return hasConnectedMessage;
    }

    private ChatMessage getHasDisconnectedMessage(String disconnectedUserName) {
        ChatMessage hasDisconnectedMessage = initializeChatMessage();
        hasDisconnectedMessage.setSender(disconnectedUserName);
        hasDisconnectedMessage.setMessage(disconnectedUserName + " has disconnected.");
        hasDisconnectedMessage.setDisconnected(true);
        return hasDisconnectedMessage;
    }

    private ChatMessage initializeChatMessage() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage("");
        chatMessage.setReceived(new Date());
        chatMessage.setSender("");
        return chatMessage;
    }
}