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
            [advent-of-code-2015.day-10 :as day-10]
            [advent-of-code-2015.day-11 :as day-11]
            [advent-of-code-2015.day-12 :as day-12]
            [advent-of-code-2015.day-13 :as day-13]
            [advent-of-code-2015.day-14 :as day-14]
            [advent-of-code-2015.day-15 :as day-15]
            [advent-of-code-2015.day-16 :as day-16]
            [advent-of-code-2015.day-17 :as day-17]
            [advent-of-code-2015.day-18 :as day-18]
            [advent-of-code-2015.day-19 :as day-19]
            [advent-of-code-2015.day-20 :as day-20]
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
      10 (day-10/day-10 "3113322113")
      11 (day-11/day-11 "hxbxwxba")
      12 (day-12/day-12 "input/day-12.txt")
      13 (day-13/day-13 "input/day-13.txt")
      14 (day-14/day-14 "input/day-14.txt")
      15 (day-15/day-15 "input/day-15.txt")
      16 (day-16/day-16 "input/day-16.txt")
      17 (day-17/day-17 "input/day-17.txt")
      18 (day-18/day-18 "input/day-18.txt")
      19 (day-19/day-19 "input/day-19.txt")
      20 (day-20/day-20 "29000000")
      (do (println "Usage: aoc2015 [day]")
          (println "where [day] is a number between 1 and 25 (inclusive).")))))
