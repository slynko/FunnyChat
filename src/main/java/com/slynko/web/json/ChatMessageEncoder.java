package com.slynko.web.json;

import com.slynko.db.model.ChatMessage;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.util.Calendar;
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
                .add("received", buildDateReceived(chatMessage.getReceived())).build()
                .toString();
    }

    private String buildDateReceived(Date dateReceived) {
        Calendar calendarReceived = Calendar.getInstance();
        calendarReceived.setTime(dateReceived);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(calendarReceived.get(Calendar.HOUR_OF_DAY))
                        .append(":").append(calendarReceived.get(Calendar.MINUTE))
                        .append(" ").append(calendarReceived.get(Calendar.DAY_OF_MONTH))
                        .append("/").append(calendarReceived.get(Calendar.MONTH))
                        .append("/").append(calendarReceived.get(Calendar.YEAR));
        return stringBuilder.toString();
    }
}