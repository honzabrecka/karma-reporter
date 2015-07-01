(ns foo.test-runner
  (:require [cljs.test :refer-macros [run-tests]]
            [jx.reporter.karma :refer-macros [tests-count]]
            [foo.bar-test]))

(set-print-fn! #(.log js/console %))

(defn run [env]
  (run-tests env
             'foo.bar-test))

(defn ^:export run-with-karma [tc]
  (do (jx.reporter.karma/start tc (tests-count foo.bar-test))
      (run (cljs.test/empty-env :jx.reporter.karma/karma))))
