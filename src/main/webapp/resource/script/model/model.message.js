define(function(require) {
  'use strict';

  var Backbone = require('backbone');

  var MessageModel = Backbone.Model.extend({
    defaults: {
      'received': 'dd/mm/yyyy',
      'nickName': '',
      'messageContent': ''
    }
  });

  return MessageModel;
});