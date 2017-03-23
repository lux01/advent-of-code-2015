(ns advent-of-code-2015.day-10
  (:gen-class))

(defn look-and-say
  [s]
  (->> (partition-by identity s)
       (map #(list (count %) (first %)))
       (mapcat identity)
       (apply str)))

(defn part-1
  [input]
  (count (first (drop 40 (iterate look-and-say input)))))

(defn part-2
  [input]
  (count (first (drop 50 (iterate look-and-say input)))))


(defn day-10
  [input]
  (println "Day 10")
  (println "Look and say 40: " (part-1 input))
  (println "Look and say 50: " (part-2 input)))
