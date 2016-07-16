define(function(require) {
  'use strict';

  var Backbone = require('backbone');

  var MessageModel = Backbone.Model.extend({
    defaults: {
      'received': 'dd/mm/yyyy',
      'nickName': 'undefined',
      'messageContent': ''
    }
  });

  return MessageModel;
});