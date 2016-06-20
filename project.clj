(defproject karma-reporter "1.0.1"

  :description "A plugin for running clojurescript tests with Karma."

  :url "https://github.com/honzabrecka/karma-reporter"

  :license {:name "MIT License"
            :url "http://www.opensource.org/licenses/mit-license.php"}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.170"]]

  :plugins [[lein-cljsbuild "1.1.1"]]

  :cljsbuild {:builds [{:id "test"
                        :source-paths ["example_src" "src"]
                        :compiler {:output-to "target/public/test/foo.js"
                                   :output-dir "target/public/test"
                                   :asset-path "test"
                                   :main foo.test-runner
                                   :optimizations :none}}]})
