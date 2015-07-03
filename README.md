# karma-reporter [![Build Status](https://travis-ci.org/honzabrecka/karma-reporter.svg?branch=master)](https://travis-ci.org/honzabrecka/karma-reporter)

A plugin for running ClojureScript tests with Karma.

## Installation

The easiest way is to keep karma-reporter as a dependency in your project.clj:

[![Clojars Project](http://clojars.org/karma-reporter/latest-version.svg)](http://clojars.org/karma-reporter)

To be able to execute your ClojureScript tests with Karma, you have to install [karma-cljs-test](https://github.com/honzabrecka/karma-cljs-test) adapter. You can simply do it by:

```
npm install karma-cljs-test --save-dev
```

And of course, you need Karma. If you haven't installed it yet, follow [these instructions](http://karma-runner.github.io/0.12/intro/installation.html).

## Configuration

```js
// karma.conf.js
module.exports = function(config) {
  // same as :output-dir
  var root = 'target/public/dev';

  config.set({
    frameworks: ['cljs-test'],

    files: [
      root + '/goog/base.js',
      root + '/cljs_deps.js',
      root + '/app.js',// same as :output-to
      {pattern: root + '/*.js', included: false},
      {pattern: root + '/**/*.js', included: false}
    ],

    client: {
      // main function
      args: ['app.test_runner.run_with_karma']
    },
  });
};
```

## Example

```clojure
(ns app.test-runner
  (:require [cljs.test :refer-macros [run-tests]]
            [jx.reporter.karma :refer-macros [tests-count]]
            [foo.bar]))

(set-print-fn! #(.log js/console %))

(defn run [env]
  (run-tests env
             'foo.bar))

(defn ^:export run-with-karma [tc]
  (do (jx.reporter.karma/start tc (tests-count foo.bar))
      (run (cljs.test/empty-env :jx.reporter.karma/karma))))

```
