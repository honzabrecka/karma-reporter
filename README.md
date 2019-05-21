# karma-reporter [![CircleCI](https://circleci.com/gh/honzabrecka/karma-reporter/tree/master.svg?style=svg&circle-token=025b76c6b99f0f2ee5997dd580444ad075442e53)](https://circleci.com/gh/honzabrecka/karma-reporter/tree/master)

A plugin for running ClojureScript tests with Karma.

## Installation

The easiest way is to keep karma-reporter as a dependency in your project.clj:

[![Clojars Project](http://clojars.org/karma-reporter/latest-version.svg)](http://clojars.org/karma-reporter)

To be able to execute your ClojureScript tests with Karma, you have to install [karma-cljs-test](https://github.com/honzabrecka/karma-cljs-test) adapter. You can simply do it by:

```
npm install karma-cljs-test --save-dev
```

And of course, you need Karma. If you haven't installed it yet, follow [these instructions](https://karma-runner.github.io/4.0/intro/installation.html).

## Configuration

```js
// karma.conf.js
module.exports = function(config) {
  var root = 'target/public/dev'// same as :output-dir

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
      args: ['app.test_runner.run']
    },

    // singleRun set to false does not work!
    singleRun: true
  })
}
```

## Usage

```clojure
(ns app.test-runner
  (:require [jx.reporter.karma :refer-macros [run-tests run-all-tests]]
            [foo.bar-test]))

(enable-console-print!)

; runs all tests in all namespaces
(defn ^:export run-all [karma]
  (run-all-tests karma))

; runs all tests in all namespaces - only namespaces with names matching
; the regular expression will be tested
(defn ^:export run-all-regex [karma]
  (run-all-tests karma #".*-test$"))

; runs all tests in the given namespaces
(defn ^:export run [karma]
  (run-tests karma 'foo.bar-test))
```

To execute tests from command line:

```bash
./node_modules/.bin/karma start
```

To execute tests from REPL (will use `:cljs.test/default` reporter):

```clojure
(app.test-runner/run nil)
```
