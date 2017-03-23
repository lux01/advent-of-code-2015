(ns advent-of-code-2015.day-14
  (:gen-class))

(defn reindeer-distance  
  [speed fly-time rest-time t]
  (let [phase-time (+ fly-time rest-time)
        phase-dist (* speed fly-time)
        n-phases (quot t phase-time)
        delta-t (mod t phase-time)]
    (+ (* n-phases phase-dist)
       (if (<= delta-t fly-time)
         (* delta-t speed)
         phase-dist))))

(defn parse-reindeer
  [line]
  (let [[[speed _] [fly-time _] [rest-time _] _] (re-seq #"([\d]+)" line)]
    {:speed (Long. speed)
     :fly (Long. fly-time)
     :rest (Long. rest-time)
     :mode :fly
     :time (Long. fly-time)
     :dist 0
     :points 0}))

(defn tick
  [r]
  (-> r
      (update :dist (if (= (:mode r) :fly) (partial + (:speed r)) identity))
      (update :time dec)
      (#(if (= 0 (:time %))
          (assoc %
                 :mode (condp = (:mode %) :fly :rest :rest :fly)
                 :time (condp = (:mode %) :fly (:rest %) :rest (:fly %)))
          %))))

(defn race
  ([t-end rs]
   (race 0 t-end rs))

  ([t t-end rs]
   (if (= t t-end) rs
       (recur (inc t) t-end
              (let [rs' (map tick rs)
                    max-dist (:dist (apply max-key :dist rs'))]
                (map #(update % :points (if (= max-dist (:dist %))
                                          inc identity))
                     rs'))))))

(defn part-1
  [input t]
  (->> input
       (clojure.string/split-lines)
       (map parse-reindeer)
       (map #(reindeer-distance (:speed %) (:fly %) (:rest %) t))
       (apply max)))

(defn part-2
  [input]
  (->> input
       (clojure.string/split-lines)
       (map parse-reindeer)
       (race 2503)
       (apply max-key :points)
       (:points)))

(defn day-14
  [input-file]
  (let [input (slurp input-file)]
    (println "Day 14")
    (println "Winning distance at t=2503: " (part-1 input 2503) "km")
    (println "Winning points: " (part-2 input))))
