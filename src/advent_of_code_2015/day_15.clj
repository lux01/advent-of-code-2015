(ns advent-of-code-2015.day-15
  (:require [clojure.math.combinatorics :as combi]
            [clojure.set])
  (:gen-class))

(def kitchen
  {:calories [3 3 8 8], :capacity [2 0 0 0], :durability [0 5 0 -1], :flavor [-2 -3 5 0], :texture [0 0 -1 5]})

(defn cookie-score
  [a b c d]
  (* (max 0 (* 2 a))
     (max 0 (+ (* 5 b) (* -1 d)))
     (max 0 (+ (* -2 a) (* -3 b) (* 5 c)))
     (max 0 (+ (* -1 c) (* 5 d)))))

(defn cookie-calories
  [a b c d]
  (+ (* 3 a) (* 3 b) (* 8 c) (* 8 d)))

(defn part-1
  []
  (apply max
         (for [a (range 0 101)
               b (range 0 (- 101 a))
               c (range 0 (- 101 a b))
               :let [d (- 100 a b c)]]
           (cookie-score a b c d))))

(defn part-2
  []
  (apply max
         (for [a (range 0 101)
               b (range 0 (- 101 a))
               c (range 0 (- 101 a b))
               :let [d (- 100 a b c)]
               :when (= 500 (cookie-calories a b c d))]
           (cookie-score a b c d))))

(defn day-15
  [input]
  (println "Day 15")
  (println "Maximum cookie score: " (part-1))
  (println "Maximum meal replacement cookie score: " (part-2)))
