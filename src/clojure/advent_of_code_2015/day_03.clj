(ns advent-of-code-2015.day-03
  (:gen-class))

(def init-grid
  {:houses {{:x 0, :y 0} 1}
   :posn {:x 0, :y 0}})

(defn- drop-present
  [state]
  (update-in state [:houses (:posn state)] (fnil inc 0)))

(defn- move-grid
  [state direction]
  (if (not-any? (partial = direction) [\^ \v \< \>]) state
      (-> state
          (update :posn
                  (condp = direction
                    \^ #(update % :y inc)
                    \v #(update % :y dec)
                    \< #(update % :x dec)
                    \> #(update % :x inc)))
          (drop-present))))


(defn- partition-input
  ([input]
   (partition-input input [] [] false))

  ([input left right even]
   (if (empty? input) (list left right)
       (let [x (first input)
             xs (rest input)
             left' (if (not even) (conj left x) left)
             right' (if even (conj right x) right)]
         (recur xs left' right' (not even))))))

(defn- part-1
  [input]
  (count (:houses
          (reduce move-grid init-grid input))))

(defn- part-2
  [input]
  (let [[real robot _] (partition-input input)]
    (count (:houses
            (reduce move-grid
                    (assoc (reduce move-grid init-grid real)
                           :posn {:x 0, :y 0})
                    robot)))))

(defn day-03
  [input-file]
  (let [input (slurp input-file)]
    (println "Day 3")
    (println "Number of houses visited: " (part-1 input))
    (println "Number of houses visited (with robo Santa): " (part-2 input))))
