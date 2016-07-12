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
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint(value="/chat/{room}", encoders = ChatMessageEncoder.class, decoders = ChatMessageDecoder.class)
public class ChatEndpoint {
    private final Logger log = Logger.getLogger(getClass().getName());
    private static Set<Session> sessionsSet = new HashSet<>();

    @OnOpen
    public void open(final Session session, @PathParam("room") final String room) {
        sessionsSet.add(session);
        log.info("session opened and bound to room: " + room);
        session.getUserProperties().put("room", room);
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
}