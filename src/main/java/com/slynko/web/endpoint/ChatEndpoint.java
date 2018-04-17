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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ServerEndpoint(value="/chat/{room}/{nickname}", encoders = ChatMessageEncoder.class, decoders = ChatMessageDecoder.class)
public class ChatEndpoint {
    private final Logger LOG = Logger.getLogger(getClass().getName());
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void open(final Session session,
        @PathParam("room") final String room,
        @PathParam("nickname") final String nickname) {
        ChatMessage hasConnectedMessage = ChatUtils.getHasConnectedMessage(nickname);
        sendMessageToAll(session, room, hasConnectedMessage);
        sessions.add(session);
        LOG.info("Session opened and bound to room: " + room);
    }

    @OnMessage
    public void onMessage(final Session session, final ChatMessage chatMessage) {
        String room = (String) session.getUserProperties().get("room");
            sessions.stream()
                .filter(Session::isOpen)
                .filter(s -> room.equals(s.getUserProperties().get("room")))
                .map(Session::getBasicRemote)
                .forEach(basicRemote -> {
                    try {
                        basicRemote.sendObject(chatMessage);
                    }
                    catch (IOException | EncodeException e) {
                        LOG.log(Level.SEVERE, "onMessage failed", e);
                    }
                });
    }

    @OnClose
    public void close(final Session session,
        @PathParam("room") final String room,
        @PathParam("nickname") final String nickname) {
        sessions.remove(session);
        sendMessageToAll(session, room, ChatUtils.getHasDisconnectedMessage(nickname));
        LOG.info("Session closed and unbound to room: " + room);
    }

    @OnError
    public void error(Throwable t) {
        LOG.log(Level.SEVERE, "Connection error.");
        throw new RuntimeException(t);
    }

    public static List<String> getLoggedInUsersByRoom(String room) {
        return sessions.stream()
            .filter(session -> room.equals(session.getUserProperties().get("room")))
            .map(session -> session.getRequestParameterMap().get("nickname").get(0))
            .collect(Collectors.toList());
    }

    private void sendMessageToAll(final Session session, final String room, ChatMessage message) {
        session.getUserProperties().put("room", room);
        onMessage(session, message);
    }
}