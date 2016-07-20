define(function(require) {
  'use strict';
  
  var Backbone = require('backbone'),
    _ = require('underscore'),
    template = require('text!template/chat-template.html'),
    MessageView = require('view/view.message'),
    UsersCollection = require('collection/collection.users'),
    OnlineUsersView = require('view/view.online.user');
  
  var ChatView = Backbone.View.extend({
    template: _.template(template),
    events: {
      'submit #do-chat': 'sendMessage'
    },
    initialize: function(options) {
      this.options = options;
      _.bindAll(this, 'onMessageReceived', 'handleSuccess');
      this.loggedInUsers = new UsersCollection();
      this.listenTo(this.loggedInUsers, 'sync', this.handleSuccess);
    },
    render: function() {
      this.$el.html(this.template(this.model.toJSON()));
      this.afterRender();
      return this;
    },
    handleSuccess: function(c, jsonResponse) {
      var loggedInUsers = jsonResponse.users;
      this.loggedInUsers.render(loggedInUsers);
    },
    afterRender: function() {
      this.loggedInUsers.room = this.model.get('chatRoom');
      this.loggedInUsers.fetch();

      this.messagesView = new MessageView();
      this.messagesView.setElement('#response', this.$el);

      this.loggedInUsers = new OnlineUsersView();
      this.loggedInUsers.setElement($('.online-users-panel ._onlineUsers', this.$el));

      var serviceLocation = "ws://" + window.location.host + "/chat/" ;
      this.wsocket = new WebSocket(serviceLocation + this.model.get('chatRoom')
          + "/" + this.model.get('nickName'));
      this.wsocket.onmessage = this.onMessageReceived;

      $('#message', this.$el).focus();
    },
    onMessageReceived: function(evt) {
      var messageJson = JSON.parse(evt.data); // native API
      messageJson.myNickname = this.model.get('nickName');
      this.messagesView.append(messageJson);
      
      if (messageJson.hasConnected) {
        this.loggedInUsers.append(messageJson.sender);
      } else if (messageJson.hasDisconnected) {
        this.loggedInUsers.remove(messageJson.sender);
      }
      
      var $cont = $('.panel-body');
      $cont[0].scrollTop = $cont[0].scrollHeight;
    },
    sendMessage: function() {
      var messageJsonString = this.getMessageJsonString();
      
      this.wsocket.send(messageJsonString);
      
      $('#message', this.$el).val('').focus();
      return false;
    },
    getMessageJsonString: function() {
      var nickName = this.model.get('nickName');
      var message = $('#message', this.$el).val();
      
      var messageJsonString = '{"message":"' + message + '", "sender":"'
        + nickName + '", "received":"", "hasConnected":"", "hasDisconnected":""}';
      
      return messageJsonString;
    }
  });
  
  return ChatView;
});