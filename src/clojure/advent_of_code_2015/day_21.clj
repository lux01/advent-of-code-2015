(ns advent-of-code-2015.day-21
  (:require [clojure.math.combinatorics :as combi])
  (:gen-class))

(defn transpose
  "Transposes a list of maps into a map of lists"
  [maps]
  (apply merge-with conj (zipmap (apply clojure.set/union (map keys maps))
                                 (repeat []))
         maps)
  )

(def armory
  {:weapons [{:armour 0 :damage 4 :cost 8 :name "Dagger"}
             {:armour 0 :damage 5 :cost 10 :name "Shortsword"}
             {:armour 0 :damage 6 :cost 25 :name "Warhammer"}
             {:armour 0 :damage 7 :cost 40 :name "Longsword"}
             {:armour 0 :damage 8 :cost 74 :name "Greataxe"}]
   :armour [{:armour 0 :damage 0 :cost 0 :name "Jockstrap"}
            {:armour 1 :damage 0 :cost 13 :name "Leather"}
            {:armour 2 :damage 0 :cost 31 :name "Chainmail"}
            {:armour 3 :damage 0 :cost 53 :name "Splintmail"}
            {:armour 4 :damage 0 :cost 75 :name "Bandedmail"}
            {:armour 5 :damage 0 :cost 102 :name "Platemail"}]
   :rings (combi/combinations [{:armour 0 :damage 0 :cost 0 :name "Bare left hand"}
                               {:armour 0 :damage 0 :cost 0 :name "Bare right hand"}
                               {:armour 0 :damage 1 :cost 25 :name "Damage +1"}
                               {:armour 0 :damage 2 :cost 50 :name "Damage +2"}
                               {:armour 0 :damage 3 :cost 100 :name "Damage +3"}
                               {:armour 1 :damage 0 :cost 20 :name "Defense +1"}
                               {:armour 2 :damage 0 :cost 40 :name "Defense +2"}
                               {:armour 3 :damage 0 :cost 80 :name "Defense +3"}]
                              2)
   })

(def boss
  {:hp 104 :damage 8 :armour 1})

(defn hurt
  [damage target]
  (update target :hp #(min 1 (- damage (:armour target)))))

(defn fight!
  [player boss]
  (let [p-damage (double (max 1 (- (:damage player)
                                   (:armour boss))))
        b-damage (double (max 1 (- (:damage boss)
                                   (:armour player))))
        p-n (Math/ceil (/ (:hp player) b-damage))
        b-n (Math/ceil (/ (:hp boss) p-damage))]
    (>= p-n b-n)))


(defn part-1
  []
  (apply min-key :cost
         (for [weapon (:weapons armory)
               armour (:armour armory)
               rings (:rings armory)
               :let [equipment (transpose (apply vector weapon armour rings))
                     stats (into {:hp 100} (map #(vector (first %) (reduce + (second %)))
                                                (dissoc equipment :name :cost)))
                     cost (reduce + (:cost equipment))
                     items (:name equipment)]
               :when (fight! stats boss)]
           (merge stats {:cost cost, :items items}))))

(defn part-2
  []
  (apply max-key :cost
         (for [weapon (:weapons armory)
               armour (:armour armory)
               rings (:rings armory)
               :let [equipment (transpose (apply vector weapon armour rings))
                     stats (into {:hp 100} (map #(vector (first %) (reduce + (second %)))
                                                (dissoc equipment :name :cost)))
                     cost (reduce + (:cost equipment))
                     items (:name equipment)]
               :when (not (fight! stats boss))]
           (merge stats {:cost cost, :items items}))))

(defn day-21
  []
  (println "Day 21")
  (println "Minimal winning equipment: " (part-1))
  (println "Maximal losing equipment:  " (part-2)))
