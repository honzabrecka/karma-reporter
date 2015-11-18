var path = require('path');

module.exports = function (config) {

  var root = 'target/public/test';


  config.basePath = __dirname;


  config.set({
    // frameworks to use
    // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    frameworks: ['cljs-test'],


    client: {
      args: ['foo.test_runner.run']
    },


    // list of files / patterns to load in the browser
    files: [
      root + '/goog/base.js',
      root + '/cljs_deps.js',
      root + '/foo.js',
      {pattern: root + '/*.js', included: false},
      {pattern: root + '/**/*.js', included: false}
    ],


    // list of files to exclude
    exclude: [],

    // test results reporter to use
    // possible values: 'dots', 'progress'
    // available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ['dots'],


    // web server port
    port: 9876,


    // enable / disable colors in the output (reporters and logs)
    colors: true,


    // level of logging
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    // logLevel: config.LOG_DEBUG,
    logLevel: config.LOG_WARN,


    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: true,


    // start these browsers
    // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
    browsers: ['Firefox'],


    // Continuous Integration mode
    // if true, Karma captures browsers, runs the tests and exits
    singleRun: true
  });
};
