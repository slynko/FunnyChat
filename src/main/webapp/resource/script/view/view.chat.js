define(['backbone', 'underscore', 'require', 'text!template/main-page-template.html'], 
  function(Backbone, _, require, ChatTemplate) {
//  var ChatTemplate = require('template/main-page-template');
  
  var ChatView = Backbone.View.extend({
    template: _.template(ChatTemplate),
    initialize: function() {
      var z;
    },
    render: function() {
      this.$el.html(this.template());
      return this;
    }
  });
  
  return ChatView;
});