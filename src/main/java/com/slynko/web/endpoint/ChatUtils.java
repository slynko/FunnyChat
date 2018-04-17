package com.slynko.web.endpoint;

import com.slynko.db.model.ChatMessage;

public final class ChatUtils {
  private ChatUtils() {}


  public static ChatMessage getHasConnectedMessage(String connectedUserName) {
    ChatMessage hasConnectedMessage = new ChatMessage();
    hasConnectedMessage.setSender(connectedUserName);
    hasConnectedMessage.setMessage(connectedUserName + " has connected.");
    hasConnectedMessage.setConnected(true);
    return hasConnectedMessage;
  }

  public static ChatMessage getHasDisconnectedMessage(String disconnectedUserName) {
    ChatMessage hasDisconnectedMessage = new ChatMessage();
    hasDisconnectedMessage.setSender(disconnectedUserName);
    hasDisconnectedMessage.setMessage(disconnectedUserName + " has disconnected.");
    hasDisconnectedMessage.setDisconnected(true);
    return hasDisconnectedMessage;
  }

}
