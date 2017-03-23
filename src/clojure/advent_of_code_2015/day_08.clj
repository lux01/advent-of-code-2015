(ns advent-of-code-2015.day-08
  (:gen-class))

(defn parse-escaped-chars
  [s]
  (-> (subs s 1 (dec (count s)))
      (clojure.string/replace "\\\\" "\\")
      (clojure.string/replace "\\\"" "\"")
      (clojure.string/replace #"(\\x[0-9a-f][0-9a-f])" "X")))

(defn encode-string
  [s]
  (str \"
       (-> s (clojure.string/replace "\\" "\\\\") (clojure.string/replace "\"" "\\\""))
       \"))

(defn length-in-code
  [s]
  (count s))

(defn length-in-memory
  [s]
  (count (parse-escaped-chars s)))

(defn encoded-length
  [s]
  (count (encode-string s)))

(defn part-1
  [input]
  (reduce +
          (map #(- (length-in-code %)
                   (length-in-memory %))
               (clojure.string/split-lines input))))

(defn part-2
  [input]
  (reduce +
          (map #(- (encoded-length %)
                   (length-in-code %))
               (clojure.string/split-lines input))))

(defn day-08
  [input-file]
  (let [input (slurp input-file)]
    (println "Day 8")
    (println "Δchars: " (part-1 input))
    (println "Δchars_encoded: " (part-2 input))))
