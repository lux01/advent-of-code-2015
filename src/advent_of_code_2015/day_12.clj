(ns advent-of-code-2015.day-12
  (:require [clojure.data.json :as json])
  (:gen-class))

(defn flatten-fn
  [m]
  (flatten (map #(if (map? %) (seq %) %) m)))

(defn flatten-map
  ([m]
   (flatten-map m nil))

  ([m prev]
   (if (= m prev) m
       (recur (flatten-fn m) m))))

(defn part-1
  [input]
  (->> input
       (json/read-str)
       (flatten-map)
       (filter number?)
       (reduce +)))

(defn day-12
  [input-file]
  (let [input (slurp input-file)]
    (println "Day 12")
    (println "Sum of numbers: " (part-1 input))))
