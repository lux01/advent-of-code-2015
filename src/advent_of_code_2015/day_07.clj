(ns advent-of-code-2015.day-07
  (:gen-class))

(defn parseShort
  [s]
  (try (Integer/parseUnsignedInt s)
       (catch NumberFormatException e s)))

(defn gate-and
  [x y]
  (bit-and x y 0xffff))

(defn gate-or
  [x y]
  (bit-and (bit-or x y)
           0xffff))

(defn gate-lshift
  [x y]
  (bit-and (bit-shift-left x y)
           0xffff))

(defn gate-rshift
  [x y]
  (bit-and (unsigned-bit-shift-right x y)
           0xffff))

(defn gate-not
  [x]
  (bit-and (bit-not x)
           0xffff))

(defn connect-wire
  [instruction]
  (condp re-matches instruction
    #"^([\d]+) -> ([a-z]+)$"
    :>> #(list (nth % 2)
               (parseShort (nth % 1)))

    #"^([a-z]+) -> ([a-z]+)$"
    :>> #(list (nth % 2)
               (list identity (parseShort (nth % 1))))
    
    #"^([a-z]+|[\d]+) AND ([a-z]+|[\d]+) -> ([a-z]+)$"
    :>> #(list (nth % 3)
               (list gate-and
                     (parseShort (nth % 1))
                     (parseShort (nth % 2))))
    
    #"^([a-z]+|[\d]+) OR ([a-z]+|[\d]+) -> ([a-z]+)$"
    :>> #(list (nth % 3)
               (list gate-or
                     (parseShort (nth % 1))
                     (parseShort (nth % 2))))

    #"^([a-z]+|[\d]+) LSHIFT ([\d]+) -> ([a-z]+)$"
    :>> #(list (nth % 3)
               (list gate-lshift
                     (parseShort (nth % 1))
                     (parseShort (nth % 2))))

    #"^([a-z]+|[\d]+) RSHIFT ([\d]+) -> ([a-z]+)$"
    :>> #(list (nth % 3)
               (list gate-rshift
                     (parseShort (nth % 1))
                     (parseShort (nth % 2))))

    #"^NOT ([a-z]+|[\d]+) -> ([a-z]+)"
    :>> #(list (nth % 2)
               (list gate-not
                     (parseShort (nth % 1))))))

(defn connect-circuit
  [instructions]
  (apply hash-map (mapcat identity (map connect-wire (clojure.string/split-lines instructions)))))

(defn update-wire
  [circuit kv]  
  (let [[label value _] kv]
    [label
     (cond
       (number? value)
       value
       
       (= (count value) 2)
       (let [[op x _] value
             x-val (get circuit x x)]
         (if (number? x-val)
           (op x-val)
           value))

       (= (count value) 3)
       (let [[op x y _] value
             x-val (get circuit x x)
             y-val (get circuit y y)]
         (if (and (number? x-val)
                  (number? y-val))
           (op x-val y-val)
           value)))]))

(defn simplify-circuit
  ([circuit]
   (simplify-circuit circuit nil))
   
  ([circuit prev-circuit]
   (if (or (every? (comp number? second) circuit)
           (= circuit prev-circuit))
     circuit
     (recur (apply hash-map (mapcat identity (map (partial update-wire circuit) circuit)))
            circuit))))

(def test-circuit
  (connect-circuit "123 -> x
456 -> y
x AND y -> d
x OR y -> e
x LSHIFT 2 -> f
y RSHIFT 2 -> g
NOT x -> h
NOT y -> i"))

(defn part-1
  [input]
  (get (simplify-circuit (connect-circuit input)) "a"))

(defn part-2
  [input]
  (let [circuit (connect-circuit input)]
    (get (simplify-circuit (assoc circuit "b" (part-1 input))) "a")))

(defn day-07
  [input-file]
  (let [input (slurp input-file)]
    (println "Day 7")
    (println "Value of wire \"a\": " (part-1 input))
    (println "Value of wire \"a\" in new configuration: " (part-2 input))))
