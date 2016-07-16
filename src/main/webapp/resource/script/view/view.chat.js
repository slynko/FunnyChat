define(function(require) {
  'use strict';
  
  var Backbone = require('backbone'),
    _ = require('underscore'),
    template = require('text!template/chat-template.html'),
    MessageView = require('view/view.message'),
    MessageModel = require('model/model.message');
  
  var ChatView = Backbone.View.extend({
    template: _.template(template),
    events: {
      'submit #do-chat': 'sendMessage',
      'click #leave-room': 'leaveRoom'
    },
    initialize: function(options) {
      this.options = options;
      _.bindAll(this, 'onMessageReceived');
    },
    render: function() {
      this.$el.html(this.template(this.model.toJSON()));
      this.afterRender();
      return this;
    },
    afterRender: function() {
      this.messagesView = new MessageView();
      this.messagesView.setElement('#response', this.$el);
      
      var serviceLocation = "ws://" + window.location.host + "/chat/";
      this.wsocket = new WebSocket(serviceLocation + this.model.get('chatRoom')
          + "?nickname=" + this.model.get('nickName'));
      this.wsocket.onmessage = this.onMessageReceived;
    },
    onMessageReceived: function(evt) {
      var messageJson = JSON.parse(evt.data); // native API
      this.messagesView.append(messageJson);
    },
    sendMessage: function() {
      var messageJsonString = this.getMessageJsonString();
      
      this.wsocket.send(messageJsonString);
      
      $('#message', this.$el).val('').focus();
      return false;
    },
    leaveRoom: function() {
      this.wsocket.close();
      Backbone.history.navigate("!/login", true);
    },
    getMessageJsonString: function() {
      var nickName = this.model.get('nickName');
      var message = $('#message', this.$el).val();
      
      var messageJsonString = '{"message":"' + message + '", "sender":"'
        + nickName + '", "received":""}';
      
      return messageJsonString;
    }
  });
  
  return ChatView;
});