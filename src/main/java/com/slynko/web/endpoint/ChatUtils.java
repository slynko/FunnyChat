package com.slynko.web.endpoint;

import com.slynko.db.model.Message;

public final class ChatUtils {
  private ChatUtils() {}


  public static Message getHasConnectedMessage(String connectedUserName) {
    Message hasConnectedMessage = new Message();
    hasConnectedMessage.setSender(connectedUserName);
    hasConnectedMessage.setMessage(connectedUserName + " has connected.");
    hasConnectedMessage.setConnected(true);
    return hasConnectedMessage;
  }

  public static Message getHasDisconnectedMessage(String disconnectedUserName) {
    Message hasDisconnectedMessage = new Message();
    hasDisconnectedMessage.setSender(disconnectedUserName);
    hasDisconnectedMessage.setMessage(disconnectedUserName + " has disconnected.");
    hasDisconnectedMessage.setDisconnected(true);
    return hasDisconnectedMessage;
  }

}
