define(function(require) {
  'use strict';
  
  var Backbone = require('backbone'),
    _ = require('underscore'),
    template = require('text!template/online-users-template.html');
  
  var OnlineUserView = Backbone.View.extend({
    template: _.template(template),
    initialize: function(options) {
      this.options = options;
    },
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
    }

  });
  
  return OnlineUserView;
});