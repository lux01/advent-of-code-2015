(ns advent-of-code-2015.day-05
  (:gen-class))

(defn has-three-vowels
  [string]
  (<= 3 (count (re-seq #"[aeiou]" string))))

(defn has-repeated-letter
  [string]
  (->> string
       (partition 2 1)
       (map (partial apply =))
       (some identity)))

(defn has-no-naughty-string
  [string]
  (->> ["ab" "cd" "pq" "xy"]
       (map (partial clojure.string/includes? string))
       (not-any? identity)))

(defn nice?
  [s]
  (and (has-three-vowels s)
       (has-repeated-letter s)
       (has-no-naughty-string s)))

(defn part-1
  [input]
  (count (filter nice?
                 (clojure.string/split-lines input))))

(defn day-05
  [input-file]
  
  (let [input (slurp input-file)]
    (println "Day 5")
    (println "Number of nice strings: " (part-1 input))))
