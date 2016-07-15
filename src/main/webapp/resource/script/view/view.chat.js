define(function(require) {
  'use strict';
  
  var Backbone = require('backbone'),
    _ = require('underscore'),
    template = require('text!template/chat-template.html');
  
  var ChatView = Backbone.View.extend({
    template: _.template(template),
    initialize: function(options) {
      this.model = {};
      this.options = options;
    },
    render: function() {
      this.$el.html(this.template(this.model));
      return this;
    }
  });
  
  return ChatView;
});