define(function(require) {
  'use strict';

  var Backbone = require('backbone');

  var UsersCollection = Backbone.Collection.extend({
    restServiceUrl: '/rest/users/',
    room: '1',
    url: function() {
      return this.restServiceUrl + this.room;
    }
  });

  return UsersCollection;
});