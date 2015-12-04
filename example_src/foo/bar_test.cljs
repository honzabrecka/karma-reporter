(ns foo.bar-test
  (:require [cljs.test :refer-macros [deftest is testing run-tests]]))

(deftest add-test
  (is (= (+ 2 3) 5)))

(deftest subtract-test
  (is (= (- 7 4) 3)))

(defn not-a-test [])
