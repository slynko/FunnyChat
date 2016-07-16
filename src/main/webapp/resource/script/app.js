require.config({
  paths : {
    'jquery' : "lib/jquery-1.10.2.min",
    'backbone' : "lib/backbone-min",
    'underscore' : "lib/underscore-min",
    'template': '../template',
    'text' : 'lib/text',
    'require': 'lib/require.js'
  },
  map : {
    '*' : {
    }
  }
});

define(function(require) {
    'use strict';

    var MainRouter = require('router/router.main'),
      Backbone = require('backbone');

    var mainRouter = new MainRouter();
    Backbone.history.start();
    mainRouter.navigate('!/login');
  }
);

