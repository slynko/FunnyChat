define(function(require) {
  var Backbone = require('backbone'),
    _ = require('underscore'),
    template = require('text!template/message-template.html');
  
  return Backbone.View.extend({
    template: _.template(template),
    append: function(messageJson) {
      this.$el.append(this.template(messageJson));
      return this;
    }
  });
});