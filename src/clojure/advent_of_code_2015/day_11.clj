(ns advent-of-code-2015.day-11
  (:gen-class))

(def inc-char-table
  (->> (range (short \a) (inc (short \z)))
       (map char)
       (partition 2 1)
       (mapcat identity)
       (apply sorted-map)))
        
(defn inc-password
  ([pass]
   (apply str (reverse (inc-password (vec (reverse pass)) 0))))

  ([pass idx]
   (let [c (nth pass idx)]
     (cond
       (not c) pass
       (not= c \z) (assoc pass idx (get inc-char-table c))
       :else (recur (assoc pass idx \a) (inc idx))))))

(def straights
  (->> (range (short \a) (inc (short \z)))
       (map char)
       (partition 3 1)
       (map (partial apply str))))

(defn has-straight
  [s]
  (some identity (map (partial clojure.string/includes? s) straights)))

(defn has-no-bad-char
  [s]
  (empty? (re-find #"(o|i|l)" s)))

(defn has-two-pairs
  [s]
  (some identity
        (for [i (range 0 (dec (count s)))
              j (range (+ i 2) (dec (count s)))]                 
          (and (= (nth s i) (nth s (inc i)))
               (= (nth s j) (nth s (inc j)))))))

(defn valid-password?
  [pass]
  (and (has-straight pass)
       (has-no-bad-char pass)
       (has-two-pairs pass)))

(defn next-password
  [pass]
  (->> (inc-password pass)
       (iterate inc-password)
       (drop-while (comp not valid-password?))
       (first)))

(defn part-1
  [input]
  (next-password input))

(defn part-2
  [input]
  (next-password (next-password input)))

(defn day-11
  [input]
  (println "Day 11")
  (println "Next password: " (part-1 input))
  (println "Next password: " (part-2 input)))
