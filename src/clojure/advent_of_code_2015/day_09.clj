(ns advent-of-code-2015.day-09
  (:require [clojure.math.combinatorics :as combi])
  (:gen-class))

(defn parse-distances
  [s]
  (let [[_ a b len _] (re-find #"([\w]+) to ([\w]+) = ([\d]+)" s)
        len' (Integer/parseUnsignedInt len)]
    (list [a b] len' [b a] len')))

(defn adjacency-matrix
  [routes]
  (->> routes
       (map parse-distances)
       (mapcat identity)
       (apply hash-map)))

(def destintation-table
  (clojure.string/split-lines (slurp "input/day-09.txt")))

(def test-table
  "London to Dublin = 464
London to Belfast = 518
Dublin to Belfast = 141")

(defn destinations
  [adj]
  (apply sorted-set (flatten (keys adj))))

(defn route-length
  [adj route]
  (->> route
       (partition 2 1)
       (map #(get adj (vec %)))
       (reduce +)))

(defn part-1
  [input]
  (let [destination-table (clojure.string/split-lines input)
        adj (adjacency-matrix destination-table)
        dests (destinations adj)
        all-routes (combi/permutations dests)]
    (->> all-routes
         (map #(vector % (route-length adj %)))
         (sort-by second)
         (first))))

(defn part-2
  [input]
  (let [destination-table (clojure.string/split-lines input)
        adj (adjacency-matrix destination-table)
        dests (destinations adj)
        all-routes (combi/permutations dests)]
    (->> all-routes
         (map #(vector % (route-length adj %)))
         (sort-by second >)
         (first))))

(defn day-09
  [input-file]
  (let [input (slurp input-file)]
    (println "Day 9")
    (println "Shortest route: " (part-1 input))
    (println "Longest route:  " (part-2 input))))
