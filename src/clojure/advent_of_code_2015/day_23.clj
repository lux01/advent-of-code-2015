(ns advent-of-code-2015.day-23
  (:gen-class))

(defn new-machine
  [instructions]
  {:pc 0 :a 0 :b 0
   :instrs (clojure.string/split-lines instructions)})

(defn run-instruction
  [state instr]
  (condp #(clojure.string/starts-with? %2 %1) instr
    "hlf" (let [reg (keyword (subs instr 4))]
            (-> state
                (update reg #(/ % 2))
                (update :pc inc)))
    "tpl" (let [reg (keyword (subs instr 4))]
            (-> state
                (update reg (partial * 3))
                (update :pc inc)))
    "inc" (let [reg (keyword (subs instr 4))]
            (-> state
                (update reg inc)
                (update :pc inc)))
    "jmp" (let [offset (Integer. (subs instr 4))]
            (update state :pc (partial + offset)))
    "jie" (let [reg (keyword (subs instr 4 5))
                offset (Integer. (subs instr 7))]
            (update state :pc (if (even? (get state reg)) (partial + offset) inc)))
    "jio" (let [reg (keyword (subs instr 4 5))
                offset (Integer. (subs instr 7))]
            (update state :pc (if (= 1 (get state reg)) (partial + offset) inc)))))

(defn run-machine
  [instructions & {:keys [a b]
                   :or {a 0 b 0}}]
  (loop [state (-> (new-machine instructions)
                   (assoc :a a :b b))]
    (if (>= (:pc state)
            (count (:instrs state)))
      state
      (recur (run-instruction state (get (:instrs state)
                                         (:pc state)))))))

(defn part-1
  [input]
  (:b (run-machine input)))

(defn part-2
  [input]
  (:b (run-machine input :a 1)))


(defn day-23
  [input-file]
  (let [input (slurp input-file)]
    (println "Day 23")
    (println "Value of b register: " (part-1 input))
    (println "Value of b register: " (part-2 input))))
