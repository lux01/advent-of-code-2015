(ns advent-of-code-2015.day-17
  (:require [clojure.math.combinatorics :refer [subsets]])
  (:gen-class))

(defn container-combinations
  [containers]
  (let [n (count containers)
        combinations (subsets (range 0 n))]
    (->> combinations
         (map (partial map (partial nth containers)))
         (map #(vector % (reduce + %))))))

(defn total-combinations-at-capacity
  ([capacity combinations]
   (->> combinations
        (filter (comp (partial = capacity) second))
        (count)))
  ([capacity length combinations]
   (->> combinations
        (filter #(and (= capacity (second %))
                      (= length (count (first %)))))
        (count))))

(defn min-containers-at-capacity
  [capacity combinations]
  (->> combinations
       (filter (comp (partial = capacity) second))
       (sort-by (comp count first))
       (ffirst)
       (count)))

(defn part-1
  [combinations]
  (->> combinations
       (total-combinations-at-capacity 150)))

(defn part-2
  [combinations]
  (let [min-containers (min-containers-at-capacity 150 combinations)]
    (total-combinations-at-capacity 150 min-containers combinations)))


(defn day-17
  [input-file]
  (let [input (slurp input-file)
        combinations (->> input
                          (clojure.string/split-lines)
                          (map #(Integer. %))
                          (container-combinations))]
    (println "Day 17")
    (println "Total combinations: " (part-1 combinations))
    (println "Total shortest combinations: " (part-2 combinations))))
