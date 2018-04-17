package com.slynko.web.json;

import com.slynko.db.model.ChatMessage;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.slynko.web.json.constants.Constant.DATE_RECEIVED;
import static com.slynko.web.json.constants.Constant.HAS_CONNECTED;
import static com.slynko.web.json.constants.Constant.HAS_DISCONNECTED;
import static com.slynko.web.json.constants.Constant.IS_TYPING;
import static com.slynko.web.json.constants.Constant.MESSAGE;
import static com.slynko.web.json.constants.Constant.SENDER;

public class ChatMessageEncoder implements Encoder.Text<ChatMessage> {
    @Override
    public void init(final EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public String encode(final ChatMessage chatMessage) throws EncodeException {
        return Json.createObjectBuilder()
                .add(MESSAGE, chatMessage.getMessage())
                .add(SENDER, chatMessage.getSender())
                .add(DATE_RECEIVED, convertDateToString(chatMessage.getReceived()))
                .add(HAS_CONNECTED, chatMessage.hasConnected())
                .add(HAS_DISCONNECTED, chatMessage.hasDisconnected())
                .add(IS_TYPING, chatMessage.isTyping())
                .build()
                .toString();
    }

    private String convertDateToString(Date dateReceived) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm MMM dd, yyyy");
        return dateFormat.format(dateReceived);
    }
}