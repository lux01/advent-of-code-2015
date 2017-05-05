(ns advent-of-code-2015.day-25
  (:gen-class))

(defn diagonalise
  "Given a (row, col) pair returns the location in the diagonalisation of the infinite grid of that location. The origin is (1, 1)."
  [row col]
  (let [r (+ row (dec col))]
    (+ col (/ (* r (dec r)) 2))))

(defn generate-code
  [row col]
  (let [gc (fn [code n]
                         (if (zero? n) code
                             (recur (mod (* 252533 code) 33554393) (dec n))))]
    (gc 20151125 (dec (diagonalise row col)))))

(defn day-25
  []
  (println "Day 25")
  (println "Code: " (generate-code 3010 3019)))
