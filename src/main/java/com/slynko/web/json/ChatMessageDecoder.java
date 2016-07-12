package com.slynko.web.json;

import com.slynko.db.model.ChatMessage;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;
import java.util.Date;

public class ChatMessageDecoder implements Decoder.Text<ChatMessage> {

    @Override
    public void init(final EndpointConfig config) {
    }

    @Override
    public void destroy(){
    }

    public ChatMessage decode(final String textMessage) throws DecodeException {
        ChatMessage chatMessage = new ChatMessage();

        JsonObject jsonObject = Json.createReader(new StringReader(textMessage))
                .readObject();
        chatMessage.setMessage(jsonObject.getString("message"));
        chatMessage.setSender(jsonObject.getString("sender"));
        chatMessage.setReceived(new Date());
        return chatMessage;
    }

    @Override
    public boolean willDecode(final String s) {
        return true;
    }
}
