define(function(require) {
  'use strict';

  var Backbone = require('backbone'),
    LoginView = require('view/view.login'),
    ChatView = require('view/view.chat'),
    UserModel = require('model/model.user');

  var MainRouter = Backbone.Router.extend({
    initialize: function() {
      this.chatContainer = $('.chat-main-wrapper');
      this.userModel = new UserModel();
    },
    routes: {
      '': 'login',
      '!/login': 'login',
      '!/chat/:page': 'connectToRoom'
    },
    login: function() {
      var loginView = new LoginView();
      this.bindModelAndElementToView(loginView, this.userModel, this.chatContainer);
      loginView.render();
    },
    connectToRoom: function() {
      if (this.userModel.get('nickName')) {
        var chatView = new ChatView();
        this.bindModelAndElementToView(chatView, this.userModel, this.chatContainer);
        chatView.render();
      }
      else {
        this.navigate('!/login', true);
      }
    },
    bindModelAndElementToView: function(view, model, element) {
      view.setElement(element);
      view.model = model;
    }
  });

  return MainRouter;
});