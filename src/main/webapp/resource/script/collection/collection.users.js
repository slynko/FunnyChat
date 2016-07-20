define(function(require) {
  'use strict';

  var Backbone = require('backbone');
  
  var UsersCollection = Backbone.Collection.extend({
    url: function() {
      return '/rest/users/' + this.room;
    }
  });
  
  return UsersCollection;
});