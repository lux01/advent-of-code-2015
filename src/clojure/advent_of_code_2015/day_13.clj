(ns advent-of-code-2015.day-13
  (:require [clojure.math.combinatorics :as combi])
  (:gen-class))

(def happiness-regex
  #"([\w]+) would (gain|lose) ([\d]+) happiness units by sitting next to ([\w]+)\.")

(defn parse-happiness
  [line]
  (let [[_ a gain-lose amount b _](re-find happiness-regex line)]
    (list [a b] (* (Integer/parseUnsignedInt amount)
                    (if (= "gain" gain-lose) 1 -1)))))

(defn happiness-table
  [input]
  (->> input
       (clojure.string/split-lines)
       (map parse-happiness)
       (reduce #(assoc-in %1 (nth %2 0) (nth %2 1)) {})))

(defn total-happiness
  [table [left mid right _]]
  (+ (get-in table [mid left] 0)
     (get-in table [mid right] 0)))

(defn part-1
  [input]
  (let [happiness (happiness-table input)
        names (keys happiness)
        n (count names)]
    (apply max-key second
           (for [arrangement (combi/permutations names)]
             (->> arrangement
                  (cycle)
                  (partition 3 1)
                  (take n)
                  (map (partial total-happiness happiness))
                  (reduce +)
                  (vector arrangement))))))


(defn part-2
  [input]
  (let [happiness (happiness-table input)
        names (cons "You" (keys happiness))
        n (count names)]
    (apply max-key second
           (for [arrangement (combi/permutations names)]
             (->> arrangement
                  (cycle)
                  (partition 3 1)
                  (take n)
                  (map (partial total-happiness happiness))
                  (reduce +)
                  (vector arrangement))))))

(defn day-13
  [input-file]
  (let [input (slurp input-file)]
    (println "Day 13")
    (println "Optimal seating arrangement:" (part-1 input))
    (println "Optimal seating with you: " (part-2 input))))
