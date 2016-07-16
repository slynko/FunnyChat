define(function(require) {
  'use strict';

  var Backbone = require('backbone');

  var UserModel = Backbone.Model.extend({
    defaults: {
      'nickName': 'undefinedUser',
      'chatRoom': '1'
    }
  });

  return UserModel;
});