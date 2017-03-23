(defproject advent-of-code-2015 "0.1.0"
  :description "Clojure solutions to the Advent of Code 2015."
  :url "https://github.com/lux01/advent-of-code-2015"
  :license {:name "MIT License"
            :url "https://github.com/lux01/advent-of-code-2015/blob/master/LICENSE"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/math.combinatorics "0.1.4"]
                 [org.clojure/data.json "0.2.6"]
                 [pandect "0.6.1"]]
  :main ^:skip-aot advent-of-code-2015.core
  :source-paths ["src/clojure"]
  :java-source-paths ["src/java"]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

