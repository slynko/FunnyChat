define(function(require) {
  'use strict';
  
  var Backbone = require('backbone'),
    _ = require('underscore'),
    template = require('text!template/online-users-template.html');
  
  return Backbone.View.extend({
    template: _.template(template),
    render: function(loggedInUsers) {
      var self = this;
      loggedInUsers.forEach(function(user) {
        self.append(user);
      });
    },
    append: function(nickname) {
      var onlineUser = {
        'nickname': nickname
      };
      this.$el.append(this.template(onlineUser));
      return this;
    },
    remove: function(nickname) {
      var removingElement = $('#' + nickname, this.$el);
      removingElement.remove();
    },
    setTyping: function(nickname) {
      var start = Date.now();
      var isTypingElement = $('#' + nickname, this.$el);
      var isTypingMessage = $('#typing', isTypingElement);
      if (!isTypingMessage.html()) {
        isTypingElement.append('<span id="typing" class="text-info">&nbsp;&nbsp;&nbsp;is typing .</span>');
        isTypingMessage = $('#typing', isTypingElement);
        var timer = setInterval(function() {
          var timePassed = Date.now() - start;
          if (timePassed >= 2000) {
            
            isTypingMessage.remove();
            clearInterval(timer);
            return;
          }
          isTypingMessage.append('.');
        }, 400);
      }
    }
  });
});