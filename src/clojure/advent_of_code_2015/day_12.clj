(ns advent-of-code-2015.day-12
  (:require [clojure.data.json :as json])
  (:gen-class))


(defn json-obj->num
  [x & {:keys [map-filter]
        :or {map-filter (constantly true)}}]
  (cond (map? x) (if (map-filter x)
                   (reduce + (map (comp #(json-obj->num %
                                                        :map-filter map-filter)
                                        second)
                                  (into [] x)))
                   0)        
        (vector? x) (reduce + (map #(json-obj->num % :map-filter map-filter) x))
        (string? x) 0
        (number? x) x
        (nil? x) 0
        ))


(defn part-1
  [input]
  (->> input
       (json/read-str)
       (json-obj->num)))

(defn part-2
  [input]
  (-> input
       (json/read-str)
       (json-obj->num :map-filter #(not-any? (comp (partial = "red") second) %))))

(defn day-12
  [input-file]
  (let [input (slurp input-file)]
    (println "Day 12")
    (println "Sum of numbers: " (part-1 input))
    (println "Sum of non-red things: " (part-2 input))))
