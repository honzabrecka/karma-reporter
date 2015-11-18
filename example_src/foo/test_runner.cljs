(ns foo.test-runner
  (:require [jx.reporter.karma :refer-macros [run-tests]]
            [foo.bar-test]))

(set-print-fn! #(.log js/console %))

(defn ^:export run [karma]
  (run-tests karma 'foo.bar-test))
