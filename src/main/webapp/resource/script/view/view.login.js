define(function(require) {
  'use strict';
  
  var Backbone = require('backbone'),
    _ = require('underscore'),
    template = require('text!template/login-template.html');
  
  var LoginView = Backbone.View.extend({
    template: _.template(template),
    events: {
      'submit .form-signin': 'login'
    },
    initialize: function(options) {
      this.options = options;
    },
    render: function() {
      this.$el.html(this.template(this.model.toJSON()));
      return this;
    },
    login: function(){
      this.initializeModelFields();
      
      if (this.model.get('nickName').trim()) {
        Backbone.history.navigate("!/chat/" + this.model.get('chatRoom'), true);
      } else {
        var emptyNickNameContainer = $('._emptyNickName', this.$el);
        emptyNickNameContainer.html('Your nickname is empty');
        emptyNickNameContainer.show();
      }
      return false;
    },
    initializeModelFields: function() {
      this.nickName = $('#nickname', this.$el);
      this.chatRoom = $('#chatroom', this.$el);
      
      this.model.set({
          nickName: this.nickName.val(),
          chatRoom: this.chatRoom.val()
      });
    }
  });
  
  return LoginView;
});