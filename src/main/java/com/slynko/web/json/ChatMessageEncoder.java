package com.slynko.web.json;

import com.slynko.db.model.ChatMessage;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.text.SimpleDateFormat;
import java.util.Date;

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
                .add("message", chatMessage.getMessage())
                .add("sender", chatMessage.getSender())
                .add("received", convertDate(chatMessage.getReceived())).build()
                .toString();
    }

    private String convertDate(Date dateReceived) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm dd/mm/yyyy");
        return dateFormat.format(dateReceived);
    }
}