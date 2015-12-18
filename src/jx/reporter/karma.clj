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
  (if (nil? karma)
    `(cljs.test/run-tests ~@namespaces)
    `(do (jx.reporter.karma/start ~karma (tests-count ~@namespaces))
        (cljs.test/run-tests (cljs.test/empty-env ::karma) ~@namespaces))))

(defmacro run-all-tests
  ([karma] `(run-all-tests ~karma nil))
  ([karma re]
   (if (nil? karma)
     `(cljs.test/run-all-tests ~re)
     (let [namespaces# (map
                         (fn [ns] `(quote ~ns))
                         (cond->> (api/all-ns)
                                  re (filter #(re-matches re (name %)))))]
       `(do (jx.reporter.karma/start ~karma (tests-count ~@namespaces#))
            (cljs.test/run-all-tests ~re (cljs.test/empty-env ::karma)))))))
