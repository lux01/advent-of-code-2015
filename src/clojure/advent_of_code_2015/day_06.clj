(ns advent-of-code-2015.day-06
  (:gen-class))

(defn turn-on
  [_]
  1)

(defn turn-off
  [_]
  0)

(defn toggle
  [v]
  (if (= v 1) 0 1))

(defn to-instruction
  ([s]
   (condp #(clojure.string/includes? %2 %1) s
     "turn on" (constantly 1)
     "turn off" (constantly 0)
     "toggle" #(condp = % 1 0 0 1)))
  ([_ s]
   (condp #(clojure.string/includes? %2 %1) s
     "turn on" inc
     "turn off" #(max 0 (dec %))
     "toggle" (partial + 2))))

(defn to-range
  [s]
  (let [[x1 y1 x2 y2 _] (map #(Integer/parseInt %) (re-seq #"[\d]+" s))]
    (for [x (range x1 (inc x2))
          y (range y1 (inc y2))]
      (+ (* 1000 y) x))))

(defn update-grid
  ([grid [f range]]

   (loop [t-grid (transient grid)
          xy (first range)
          xys (rest range)]
     (if-not xy (persistent! t-grid)
             (recur (assoc! t-grid xy (f (get t-grid xy)))
                    (first xys)
                    (rest xys))))))

(def off-grid (vec (repeat (* 1000 1000) 0)))


(defn part-1
  [input]
  (->> input
       (clojure.string/split-lines)
       (map #(vector (to-instruction %) (to-range %)))
       (reduce update-grid off-grid)
       (filter (partial = 1))
       (count)))

(defn part-2
  [input]
  (->> input
       (clojure.string/split-lines)
       (map #(vector (to-instruction :proper %) (to-range %)))
       (reduce update-grid off-grid)
       (reduce +)))


(defn day-06
  [input-file]
  (let [input (slurp input-file)]
    (println "Day 6")
    (println "Number of lit lights: " (part-1 input))
    (println "Total brightness: " (part-2 input))))
