(ns foo.test-runner
  (:require [jx.reporter.karma :refer-macros [run-tests run-all-tests]]
            [foo.bar-test]))

(enable-console-print!)

(defn ^:export run [karma]
  (run-tests karma 'foo.bar-test))

(defn ^:export run-all [karma]
  (run-all-tests karma))

(defn ^:export run-all-regex [karma]
  (run-all-tests karma #".*-test$"))
