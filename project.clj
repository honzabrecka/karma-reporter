(defproject karma-reporter "0.1.0-SNAPSHOT"
  :description "A plugin for running clojurescript tests with Karma."
  :url "https://github.com/honzabrecka/colored-reporter"
  :license {:name "MIT License"
            :url "http://www.opensource.org/licenses/mit-license.php"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "0.0-3308"]
                 [karma-reporter "0.1.0-SNAPSHOT"]]
  :plugins [[lein-cljsbuild "1.0.6"]]
  :cljsbuild {:builds [{:id "test"
                        :source-paths ["example_src"]
                        :compiler {:output-to "target/public/test/foo.js"
                                   :output-dir "target/public/test"
                                   :main foo.test-runner
                                   :optimizations :none}}]})
