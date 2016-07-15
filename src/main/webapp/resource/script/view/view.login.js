define(function(require) {
  'use strict';
  
  var Backbone = require('backbone'),
    _ = require('underscore'),
    template = require('text!template/login-template.html'),
    ChatView = require('view/view.chat');
  
  var LoginView = Backbone.View.extend({
    template: _.template(template),
    events: {
      'change #nickname': 'changeNickname',
      'change #chatroom': 'changeChatRoom',
      'click #enterRoom': 'enterRoom'
    },
    initialize: function(options) {
      this.model = {};
      this.options = options;
      
      this.message = $('#message');
      this.chatWindow = $('#response');
    },
    render: function() {
      this.$el.html(this.template(this.model));

      this.afterRender();

      return this;
    },
    afterRender: function() {
      this.nickname = $('#nickname', this.$el);
      this.chatRoom = $('#chatroom', this.$el);
    },
    enterRoom: function(evt) {
      evt.preventDefault();
      this.connectToChatserver();
      var room = this.chatRoom.val();

      var chatView = new ChatView();
      chatView.setElement(this.$el);
      chatView.render();
      
      // $('.chat-wrapper h2').text('Chat name: ' + room + '. Your nick name: ' + this.nickName);
      // $('.chat-signin').hide();
      // $('.chat-wrapper').show();
      //this.message.focus();
    },
    connectToChatserver: function () {
      var serviceLocation = "ws://" + window.location.host + "/chat/";
      var room = this.chatRoom.val();
      var wsocket = new WebSocket(serviceLocation + room + "?nickname=" + this.nickName);
      wsocket.onmessage = this.onMessageReceived;
    },
    onMessageReceived: function() {
      var msg = JSON.parse(evt.data); // native API
      var $messageLine = $('<tr><td class="received">' + msg.received
        + '</td><td class="user label label-info">' + msg.sender
        + '</td><td class="message badge">' + msg.message
        + '</td></tr>');
      this.chatWindow.append($messageLine);
    },
    changeNickname: function() {
      this.model.nickname = this.nickname.val();
    },
    changeChatRoom: function() {
      this.model.chatRoom = $(':selected', this.chatRoom).val();
    }
  });
  
  return LoginView;
});