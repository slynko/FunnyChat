define(function(require) {
  'use strict';
  
  var Backbone = require('backbone'),
    _ = require('underscore'),
    template = require('text!template/chat-template.html');
  
  var ChatView = Backbone.View.extend({
    template: _.template(template),
    events: {
      'submit #do-chat': 'sendMessage',
      'click #leave-room': 'leaveRoom'
    },
    initialize: function(options) {
      this.options = options;
    },
    render: function() {
      this.$el.html(this.template(this.model.toJSON()));
      this.afterRender();
      return this;
    },
    afterRender: function() {
      var serviceLocation = "ws://" + window.location.host + "/chat/";
      this.wsocket = new WebSocket(serviceLocation + this.model.get('chatRoom') + "?nickname=" + this.model.get('nickName'));
      this.wsocket.onmessage = this.onMessageReceived;
    },
    onMessageReceived: function(evt) {
      var msg = JSON.parse(evt.data); // native API
      var $messageLine = $('<tr><td class="received">' + msg.received
        + '</td><td class="user label label-info">' + msg.sender
        + '</td><td class="message badge">' + msg.message
        + '</td></tr>');
      $('#response', this.$el).append($messageLine);
    },
    sendMessage: function() {
      var msg = '{"message":"' + $('#message', this.$el).val() + '", "sender":"'
        + this.model.get('nickName') + '", "received":""}';
      this.wsocket.send(msg);
      $('#message', this.$el).val('').focus();
      return false;
    },
    leaveRoom: function() {
      this.wsocket.close();
      Backbone.history.navigate("!/login", true);
    }
  });
  
  return ChatView;
});