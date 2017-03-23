(ns advent-of-code-2015.day-04
  (:require [pandect.algo.md5 :refer [md5]])
  (:gen-class))

(defn- is-advent-coin
  [key x]
  (clojure.string/starts-with? (md5 (str key x)) "00000"))

(defn- is-super-advent-coin
  [key x]
  (clojure.string/starts-with? (md5 (str key x)) "000000"))

(defn- part-1
  [input]
  (first (drop-while (comp not (partial is-advent-coin input)) (iterate inc 1))))

(defn- part-2
  [input]
  (first (drop-while (comp not (partial is-super-advent-coin input))
                     (iterate inc 1))))

(defn day-04
  [input]
  (println "Day 4")
  (print "First AdventCoin: ")
  (println (part-1 input))
  (print "First super AdventCoin: ")
  (println (part-2 input)))
