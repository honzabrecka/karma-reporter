(ns jx.reporter.karma
  (:require [cljs.env :as env]
            [cljs.analyzer :as ana]
            [cljs.analyzer.api :as api]
            [pjstadig.assert-expr :include-macros true]))

(defmacro tests-count
  [& namespaces]
  `(+ ~@(map (fn [[_ ns]]
               (count
                 (filter #(true? (get-in % [1 :test]))
                         (cljs.analyzer.api/ns-publics ns))))
             namespaces)))

(defmacro run-tests
  "Runs all tests in the given namespaces."
  [karma & namespaces]
  `(if (nil? ~karma)
     (cljs.test/run-tests ~@namespaces)
     (do (jx.reporter.karma/start ~karma (tests-count ~@namespaces))
         (cljs.test/run-tests (cljs.test/empty-env ::karma) ~@namespaces))))

(defmacro run-all-tests
  "Runs all tests in all namespaces.
  Optional argument is a regular expression; only namespaces with
  names matching the regular expression (with re-matches) will be
  tested."
  ([karma]
   `(jx.reporter.karma/run-all-tests ~karma #".*"))
  ([karma re]
   `(jx.reporter.karma/run-tests
      ~karma
      ~@(into '()
              (comp (filter #(re-matches re (name %)))
                    (map (fn [ns] `(quote ~ns))))
              (api/all-ns)))))
