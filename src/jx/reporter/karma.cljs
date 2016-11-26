(ns jx.reporter.karma
  (:require [cljs.test]
            [fipp.clojure]
            [pjstadig.humane-test-output]
            [pjstadig.util :as util])
  (:require-macros [jx.reporter.karma :as karma]))

(def karma (volatile! nil))

(defn karma? [] (not (nil? @karma)))

(defn- karma-info! [m]
  (when (karma?)
    (.info @karma (clj->js m))))

(defn- karma-result! [m]
  (when (karma?)
    (.result @karma (clj->js m))))

(defn- karma-complete! []
  (when (karma?)
    (.complete @karma #js {})))

(defn- now []
  (.getTime (js/Date.)))

(defn indent [n s]
  (let [indentation (reduce str "" (repeat n " "))]
    (clojure.string/replace s #"\n" (str "\n" indentation))))

(defn format-fn [[c & q]]
  (let [e (->> q
               (map #(with-out-str (fipp.clojure/pprint %)))
               (apply str)
               (str "\n"))]
    (str "(" c (indent 12 (subs e 0 (dec (count e)))) ")")))

(defn- format-log [{:keys [expected actual message] :as result}]
  (with-out-str (#'util/report- result)))

(def test-var-result (volatile! []))

(def test-var-time-start (volatile! (now)))

(defmethod cljs.test/report :karma [_])

(defmethod cljs.test/report [::karma :begin-test-var] [_]
  (vreset! test-var-time-start (now))
  (vreset! test-var-result []))

(defmethod cljs.test/report [::karma :end-test-var] [m]
  (let [var-meta (meta (:var m))
        result   {"suite"       [(:ns var-meta)]
                  "description" (:name var-meta)
                  "success"     (zero? (count @test-var-result))
                  "skipped"     nil
                  "time"        (- (now) @test-var-time-start)
                  "log"         (map format-log @test-var-result)}]
    (karma-result! result)))

(defmethod cljs.test/report [::karma :fail] [m]
  (cljs.test/inc-report-counter! :fail)
  (vswap! test-var-result conj m))

(defmethod cljs.test/report [::karma :error] [m]
  (cljs.test/inc-report-counter! :error)
  (vswap! test-var-result conj m))

(defmethod cljs.test/report [::karma :end-run-tests] [_]
  (karma-complete!))

(defn start [tc total-count]
  (vreset! karma tc)
  (karma-info! {:total total-count}))
