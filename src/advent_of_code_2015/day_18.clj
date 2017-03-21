(ns advent-of-code-2015.day-18
  (:gen-class))

(defn parse-input
  [input]
  (->> input
       (clojure.string/split-lines)
       (mapv (partial mapv (partial = \#)))
       ))

(defn tick-bulb
  [grid j i me]
  (let [lit-neighbours (count
                        (filter identity
                                (map #(get-in grid % false)
                                     [[(inc j) (dec i)] [(inc j) i] [(inc j) (inc i)]
                                      [j (dec i)] [j (inc i)]
                                      [(dec j) (dec i)] [(dec j) i] [(dec j) (inc i)]])))]
    (if me (<= 2 lit-neighbours 3) (= 3 lit-neighbours))))

(defn tick-grid
  [grid]
  (->> grid       
       (map-indexed #(map-indexed (partial tick-bulb grid %1) %2))
       (mapv (partial mapv identity))))

(def test-grid
  (parse-input
   ".#.#.#
...##.
#....#
..#...
#.#..#
####.."))

(defn show-grid
  [grid]
  (->> grid
       (map (partial map #(if % \# \.)))
       (map (partial apply str))
       (clojure.string/join \newline)
       (println)
       ))

(defn part-1
  [input]
  (->> input
       (parse-input)
       (iterate tick-grid)
       (drop 100)
       (first)
       (flatten)
       (filter identity)
       (count)))

(defn tick-broken-grid
  [grid]
  (-> grid
       (assoc-in [0 0] true)
       (assoc-in [0 99] true)
       (assoc-in [99 0] true)
       (assoc-in [99 99] true)
       (tick-grid)
       (assoc-in [0 0] true)
       (assoc-in [0 99] true)
       (assoc-in [99 0] true)
       (assoc-in [99 99] true)))

(defn part-2
  [input]
  (->> input
       (parse-input)
       (iterate tick-broken-grid)
       (drop 100)
       (first)
       (flatten)
       (filter identity)
       (count)))


(defn day-18
  [input-file]
  (let [input (slurp input-file)]
    (println "Day 18")
    (println "Number of lights on after 100 iterations: " (part-1 input))
    (println "Number of lights on after 100 broken iterations: " (part-2 input))))
