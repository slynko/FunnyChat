define(function(require) {
  'use strict';

  var Backbone = require('backbone');
  
  var UsersCollection = Backbone.Collection.extend({
    url: '/rest/users'
  });
  
  return UsersCollection;
});