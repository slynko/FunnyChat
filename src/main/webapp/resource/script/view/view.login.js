define(function(require) {
  'use strict';
  
  var Backbone = require('backbone'),
    _ = require('underscore'),
    template = require('text!template/login-template.html'),
    ChatView = require('view/view.chat'),
    UserModel = require('model/model.user');
  
  var LoginView = Backbone.View.extend({
    template: _.template(template),
    events: {
      'change #nickname': 'changeNickname',
      'change #chatroom': 'changeChatRoom',
      'submit .form-signin': 'navigateToChatRoom'
    },
    initialize: function(options) {
      this.options = options;
      
      this.message = $('#message');
      this.chatWindow = $('#response');
    },
    render: function() {
      this.$el.html(this.template(this.model.toJSON()));

      this.afterRender();

      return this;
    },
    navigateToChatRoom: function(e){
      Backbone.history.navigate("!/chat/" + this.model.get('chatRoom'), true);
      return false;
    },
    afterRender: function() {
      this.nickName = $('#nickname', this.$el);
      this.chatRoom = $('#chatroom', this.$el);
    },
    enterRoom: function(evt) {
      evt.preventDefault();
      this.connectToChatserver();
      var room = this.chatRoom.val();

      
      // $('.chat-wrapper h2').text('Chat name: ' + room + '. Your nick name: ' + this.nickName);
      // $('.chat-signin').hide();
      // $('.chat-wrapper').show();
      //this.message.focus();
    },
    connectToChatserver: function () {
      // var serviceLocation = "ws://" + window.location.host + "/chat/";
      // var room = this.chatRoom.val();
      // var wsocket = new WebSocket(serviceLocation + room + "?nickname=" + this.nickName);
      // wsocket.onmessage = this.onMessageReceived;
    },
    changeNickname: function() {
      this.model.set({
          nickName: this.nickName.val() 
      });
    },
    changeChatRoom: function() {
      this.model.set({
        chatRoom: this.chatRoom.val()
      });
    }
  });
  
  return LoginView;
});