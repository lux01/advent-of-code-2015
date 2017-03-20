(ns advent-of-code-2015.core
  (:use [clojure.pprint :only [pprint]])
  (:require [advent-of-code-2015.day-01 :as day-01]
            [advent-of-code-2015.day-02 :as day-02]
            [advent-of-code-2015.day-03 :as day-03]
            [advent-of-code-2015.day-04 :as day-04]
            [advent-of-code-2015.day-05 :as day-05]
            [advent-of-code-2015.day-06 :as day-06]
            [advent-of-code-2015.day-07 :as day-07]
            [advent-of-code-2015.day-08 :as day-08]
            [advent-of-code-2015.day-09 :as day-09]
            )
  (:gen-class))

(defn -main
  [& args]
  
  (let [day (try (Integer/parseInt (nth args 0))
                 (catch NumberFormatException e 1))]
    (condp = day
      1 (day-01/day-01 "input/day-01.txt")
      2 (day-02/day-02 "input/day-02.txt")
      3 (day-03/day-03 "input/day-03.txt")
      4 (day-04/day-04 "iwrupvqb")
      5 (day-05/day-05 "input/day-05.txt")
      6 (day-06/day-06 "input/day-06.txt")
      7 (day-07/day-07 "input/day-07.txt")
      8 (day-08/day-08 "input/day-08.txt")
      9 (day-09/day-09 "input/day-09.txt")
      (do (println "Usage: aoc2015 [day]")
          (println "where [day] is a number between 1 and 25 (inclusive).")))))
