(ns advent-of-code-2015.day-02
  (:gen-class))

(defn parse-gift
  [gift-str]
  (->> (clojure.string/split gift-str #"x")
       (map #(Integer. %))))

(defn gift-area
  [[w l h]]
  (+ (* 2 w l)
     (* 2 w h)
     (* 2 l h)))

(defn slack
  [dims]
  (reduce * (take 2 (sort dims))))

(defn ribbon-length
  [dims]
  (+ (* 2 (reduce + (take 2 (sort dims))))
     (reduce * dims)))

(defn part-1
  [input]
  (->> (clojure.string/split-lines input)
       (map parse-gift)
       (map #(+ (gift-area %) (slack %)))
       (reduce +)))

(defn part-2
  [input]
  (->> (clojure.string/split-lines input)
       (map parse-gift)
       (map ribbon-length)
       (reduce +)))
