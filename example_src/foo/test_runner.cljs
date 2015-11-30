(ns foo.test-runner
  (:require [jx.reporter.karma :refer-macros [run-tests]]
            [foo.bar-test]))

(enable-console-print!)

(defn ^:export run [karma]
  (run-tests karma 'foo.bar-test))
