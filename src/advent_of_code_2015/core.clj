(ns advent-of-code-2015.core
  (:use [clojure.pprint :only [pprint]])
  (:require [advent-of-code-2015.day-01 :as day-01]
            [advent-of-code-2015.day-02 :as day-02]
            [advent-of-code-2015.day-03 :as day-03]
            [advent-of-code-2015.day-04 :as day-04])
  (:gen-class))

(defn unimplemented
  [& args]
  "Unimplemented!")

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  
  (let [day (try (Integer/parseInt (nth args 0))
                 (catch NumberFormatException e 1))]
    (condp = day
      1 (day-01/day-01 "input/day-01.txt")
      2 (day-02/day-02 "input/day-02.txt")
      3 (day-03/day-03 "input/day-03.txt")
      4 (day-04/day-04 "iwrupvqb")

      (do (println "Usage: aoc2015 [day]")
          (println "where [day] is a number between 1 and 25 (inclusive).")))))
