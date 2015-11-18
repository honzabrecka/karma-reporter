(ns jx.reporter.karma
  (:require [cljs.env :as env]
            [cljs.analyzer :as ana]
            [cljs.analyzer.api :as api]))

(defmacro tests-count [& namespaces]
  `(+ ~@(map (fn [[quote ns]]
               (count
                 (filter #(true? (get-in % [1 :test]))
                         (cljs.analyzer.api/ns-publics ns))))
             namespaces)))

(defmacro run-tests [karma & namespaces]
  `(do (jx.reporter.karma/start ~karma (tests-count ~@namespaces))
       (cljs.test/run-tests (cljs.test/empty-env ::karma) ~@namespaces)))
