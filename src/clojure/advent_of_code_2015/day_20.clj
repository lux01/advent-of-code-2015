(ns advent-of-code-2015.day-20
  (:import [advent_of_code_2015 Day20])
  (:gen-class))

(defn sigma
  [n]
  (reduce + (filter #(zero? (mod n %)) (range 1 (inc n)))))

(defn sigma-lower-bound
  "Given a value of sigma_1(n) computes a lower bound for the value of n."
  [sigma]
  (long (Math/floor (Math/sqrt sigma))))

(defn sigma-upper-bound
  "Given a value of sigma_1(n) computes an upper bound for the value of n."
  [sigma]
  (long (Math/ceil
         (+ (* 0.5 (+ 1.0 (* 2.0 sigma)))
            (* -0.5 (Math/sqrt (+ 1.0 (* 4.0 sigma))))))))

(defn part-1
  [bound]
  (let [sigma (quot bound 10)]
    (first
     (drop-while #(< (Day20/sigma %) sigma)
                 (range (sigma-lower-bound sigma) (inc (sigma-upper-bound sigma)))))))

(defn part-2
  [bound]
  (let [sigma (quot bound 11)]
    (first (drop-while #(< (Day20/sigma_2 %) sigma)
                       (range (sigma-lower-bound sigma)
                              (sigma-upper-bound sigma))))))

(defn day-20
  [input]
  (println "Day 20")
  (println "House number: " (part-1 (Integer. input)))
  (println "New house number: " (part-2 (Integer. input))))
