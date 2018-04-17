package com.slynko.web.json;

import com.slynko.db.model.Message;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;
import java.util.Date;

import static com.slynko.web.json.constants.Constant.HAS_CONNECTED;
import static com.slynko.web.json.constants.Constant.HAS_DISCONNECTED;
import static com.slynko.web.json.constants.Constant.IS_TYPING;
import static com.slynko.web.json.constants.Constant.MESSAGE;
import static com.slynko.web.json.constants.Constant.SENDER;

public class ChatMessageDecoder implements Decoder.Text<Message> {

    @Override
    public void init(final EndpointConfig config) {
    }

    @Override
    public void destroy(){
    }

    public Message decode(final String textMessage) {
        JsonObject jsonObject = Json.createReader(new StringReader(textMessage)).readObject();
        Message chatMessage = new Message();
        chatMessage.setMessage(jsonObject.getString(MESSAGE));
        chatMessage.setSender(jsonObject.getString(SENDER));
        chatMessage.setConnected(Boolean.parseBoolean(jsonObject.getString(HAS_CONNECTED)));
        chatMessage.setDisconnected(Boolean.parseBoolean(jsonObject.getString(HAS_DISCONNECTED)));
        chatMessage.setTyping(Boolean.parseBoolean(jsonObject.getString(IS_TYPING)));
        chatMessage.setReceived(new Date());
        return chatMessage;
    }

    @Override
    public boolean willDecode(final String s) {
        return true;
    }
}
