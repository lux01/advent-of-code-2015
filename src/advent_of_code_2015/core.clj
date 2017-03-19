(ns advent-of-code-2015.core
  (:use [clojure.pprint :only [pprint]])
  (:require [advent-of-code-2015.day-01 :as day-01]
            [advent-of-code-2015.day-02 :as day-02])
  (:gen-class))

(defn unimplemented
  [& args]
  "Unimplemented!")

(defn part-1
  [day]
  (-> {1 day-01/part1
       2 day-02/part-1}      
      (get day unimplemented)))

(defn part-2
  [day]
  (-> {1 day-01/part2
       2 day-02/part-2}
      (get day unimplemented)))

(defn input
  [day]
  (-> {1 "input/day-01.txt"
       2 "input/day-02.txt"}
      (get day)
      (slurp)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [day (Integer. (nth args 0))
        input-txt (input day)]
    (println "Day " day)
    (println "Part 1:\n")
    (pprint ((part-1 day) input-txt))
    (println "\nPart 2:\n")
    (pprint ((part-2 day) input-txt))
    (println "")))
