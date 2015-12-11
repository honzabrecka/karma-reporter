(ns foo.bar-test
  (:require [cljs.test :refer-macros [deftest is testing run-tests]]))

(deftest add-test
  (is (= (+ 2 3) 5)))

(deftest subtract-test
  (is (= (- 7 4) 3)))

;; Uncomment to see how failures are logged
#_(deftest failing-test
  (is (= 1 3))
  (is (= 1 2) "One should be equal to two"))

(defn not-a-test [])
