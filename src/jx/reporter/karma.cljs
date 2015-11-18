(ns jx.reporter.karma
  (:require [cljs.test]))

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

(defn- format-log [{:keys [expected actual] :as result}]
  (str
    "Fail " (cljs.test/testing-vars-str result) "\n"
    "Expected " (pr-str expected) "\n"
    "Actual: " (pr-str actual) "\n"))

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
