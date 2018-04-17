define(function(require) {
  'use strict';

  var Backbone = require('backbone');

  return Backbone.Model.extend({
    defaults: {
      'received': 'dd/mm/yyyy',
      'nickName': 'undefined',
      'messageContent': ''
    }
  });
});