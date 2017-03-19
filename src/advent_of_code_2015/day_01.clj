(ns advent-of-code-2015.day-01
  (:gen-class))

(defn- char->int
  [char]
  (condp = char
    \( 1
    \) -1
    0))

(defn- find-floor
  [input]
  (->> input
       (map char->int)
       (reduce +)))

(defn- find-basement
  ([input]
   (find-basement (map char->int input) 0 0))

  ([input floor offset]
   (if (< floor 0) offset
       (recur (rest input)
              (+ floor (first input))
              (inc offset)))))

(defn part1
  [input]
  (find-floor input))

(defn part2
  [input]
  (find-basement input))
