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
  })
}
```

## Example

```clojure
(ns app.test-runner
  (:require [jx.reporter.karma :refer-macros [run-tests]]
            [foo.bar-test]))

(set-print-fn! #(.log js/console %))

(defn ^:export run [karma]
  (run-tests karma 'foo.bar-test))
```

To execute tests from command line:

```bash
./node_modules/.bin/karma start karma.conf.js --single-run
```

To execute tests from REPL (will use :cljs.test/default reporter):

```clojure
(app.test-runner/run nil)
```
