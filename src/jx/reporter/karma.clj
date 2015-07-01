(ns jx.reporter.karma
  (:require [cljs.env :as env]
            [cljs.analyzer :as ana]
            [cljs.analyzer.api :as api]))

(defmacro tests-count [& namespaces]
  `(+ ~@(map (fn [ns]
               (count (filter
                        #(:test (nth % 1) false)
                        (cljs.analyzer.api/ns-publics ns))))
             namespaces)))
