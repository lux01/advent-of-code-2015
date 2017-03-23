(ns advent-of-code-2015.day-16
  (:gen-class))

(def aunts-txt
  "Sue 1: children: 1, cars: 8, vizslas: 7
Sue 2: akitas: 10, perfumes: 10, children: 5
Sue 3: cars: 5, pomeranians: 4, vizslas: 1
Sue 4: goldfish: 5, children: 8, perfumes: 3
Sue 5: vizslas: 2, akitas: 7, perfumes: 6
Sue 6: vizslas: 0, akitas: 1, perfumes: 2
Sue 7: perfumes: 8, cars: 4, goldfish: 10")

(defn parse-aunt
  [line]
  (let [aunt-num (Integer. (second (re-find #"Sue ([\d]+):" line)))]
    (for [[k v] (map rest (re-seq #"([\w]+): ([\d]+),?" line))]
      (list [aunt-num (keyword k)] (Integer. v)))))

(defn parse-all-aunts
  [lines]
  (->> lines
       (clojure.string/split-lines)
       (map parse-aunt)
       (mapcat identity)
       (reduce #(assoc-in %1 (first %2) (second %2)) {})))

(def model-aunt
  {:children 3, :cats 7, :samoyeds 2,
   :pomeranians 3, :akitas 0, :vizslas 0,
   :goldfish 5, :trees 3, :cars 2, :perfumes 1})

(defn no-wrong-compounds
  [aunt]
  (every? identity
          [(if (contains? aunt :children)
             (= (:children model-aunt) (:children aunt))
             true)
           (if (contains? aunt :cats)
             (= (:cats model-aunt) (:cats aunt))
             true)
           (if (contains? aunt :samoyeds)
             (= (:samoyeds model-aunt) (:samoyeds aunt))
             true)
           (if (contains? aunt :pomeranians)
             (= (:pomeranians model-aunt) (:pomeranians aunt))
             true)
           (if (contains? aunt :akitas)
             (= (:akitas model-aunt) (:akitas aunt))
             true)
           (if (contains? aunt :vizslas)
             (= (:vizslas model-aunt) (:vizslas aunt))
             true)
           (if (contains? aunt :goldfish)
             (= (:goldfish model-aunt) (:goldfish aunt))
             true)
           (if (contains? aunt :trees)
             (= (:trees model-aunt) (:trees aunt))
             true)
           (if (contains? aunt :cars)
             (= (:cars model-aunt) (:cars aunt))
             true)
           (if (contains? aunt :perfumes)
             (= (:perfumes model-aunt) (:perfumes aunt))
             true)]))

(defn no-wrong-compound-bounds
  [aunt]
  (every? identity
          [(if (contains? aunt :children)
             (= (:children model-aunt) (:children aunt))
             true)
           (if (contains? aunt :cats)
             (< (:cats model-aunt) (:cats aunt))
             true)
           (if (contains? aunt :samoyeds)
             (= (:samoyeds model-aunt) (:samoyeds aunt))
             true)
           (if (contains? aunt :pomeranians)
             (> (:pomeranians model-aunt) (:pomeranians aunt))
             true)
           (if (contains? aunt :akitas)
             (= (:akitas model-aunt) (:akitas aunt))
             true)
           (if (contains? aunt :vizslas)
             (= (:vizslas model-aunt) (:vizslas aunt))
             true)
           (if (contains? aunt :goldfish)
             (> (:goldfish model-aunt) (:goldfish aunt))
             true)
           (if (contains? aunt :trees)
             (< (:trees model-aunt) (:trees aunt))
             true)
           (if (contains? aunt :cars)
             (= (:cars model-aunt) (:cars aunt))
             true)
           (if (contains? aunt :perfumes)
             (= (:perfumes model-aunt) (:perfumes aunt))
             true)]))

(defn part-1
  [input]
  (->> input
       (parse-all-aunts)
       (filter (comp no-wrong-compounds second))
       (ffirst)))

(defn part-2
  [input]
  (->> input
       (parse-all-aunts)
       (filter (comp no-wrong-compound-bounds second))
       (ffirst)))

(defn day-16
  [input-file]
  (let [input (slurp input-file)]
    (println "Day 16")
    (println "Right aunt: " (part-1 input))
    (println "Bounded aunt: " (part-2 input))))
