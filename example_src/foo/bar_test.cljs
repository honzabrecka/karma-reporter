(ns foo.bar-test
  (:require [cljs.test :refer-macros [deftest is testing run-tests]]))

(deftest add-test
  (is (= 5 (+ 2 3))))

(deftest subtract-test
  (is (= 3 (- 7 4))))
