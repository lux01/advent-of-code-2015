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

(defn has-non-overlapping-pair
  [s]
  (some identity
        (for [i (range 0 (count s))
              j (range (+ i 2) (count s))]
          (and (= (get s i) (get s j))
               (= (get s (inc i)) (get s (inc j)))))))

(defn has-repeated-letter-with-gap
  [s]
  (some identity
        (for [i (range 0 (- (count s) 2))
              :when (= (get s i)
                       (get s (+ i 2)))]
          true)))

(defn very-nice?
  [s]
  (and (has-non-overlapping-pair s)
       (has-repeated-letter-with-gap s)))

(defn part-2
  [input]
  (count (filter very-nice?
                 (clojure.string/split-lines input))))

(defn day-05
  [input-file]
  
  (let [input (slurp input-file)]
    (println "Day 5")
    (println "Number of nice strings: " (part-1 input))
    (println "Number of very nice strings: " (part-2 input))))
