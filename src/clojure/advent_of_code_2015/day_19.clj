(ns advent-of-code-2015.day-19
  (:import [advent_of_code_2015 Day19])
  (:gen-class))

(defn find-indices
  ([s sub]
   (find-indices s sub 0 []))

  ([s sub prev out]
   (let [next (clojure.string/index-of s sub prev)]
     (if-not next out
             (recur s sub (inc next) (conj out next))))))

(defn replace-all
  [s [in out]]
  (for [idx (find-indices s in)]
    (let [before (subs s 0 idx)
          after (subs s (+ (count in) idx))]
      (str before out after))))

(defn parse-replacement
  [line]
  (rest (re-find #"([A-Z][a-z]*|e) => ([\w]+)" line)))

(defn parse-input
  [input]
  (let [lines (clojure.string/split-lines input)]
    {:reps (filter not-empty (map parse-replacement lines))
     :molecule (last lines)}))

(defn possible-molecules
  [molecule replacements]
  (->> replacements       
       (mapcat (partial replace-all molecule))
       (into #{})))

(defn part-1
  [{molecule :molecule, replacements :reps}]
  (count (possible-molecules molecule replacements)))

(defn- reconstruct-path
  ([came-from current]
   (reconstruct-path came-from current []))

  ([came-from current path]
   (if (not (contains? came-from current))
     (reverse path)
     (recur came-from (get came-from current) (conj path current)))))

(defn a*
  [start end heur dist neighbours]
  (with-local-vars [closed #{}
                    open #{start}
                    came-from {}
                    g {start 0.}
                    f {start (heur start end)}
                    reached-goal false]    
    (while (and (not-empty @open) (not @reached-goal))
      (let [current (apply min-key #(get @f % (Double/POSITIVE_INFINITY)) @open)]
        (if (= current end) (var-set reached-goal true)
            (do (var-set open (disj @open current))
                (var-set closed (conj @closed current))
                (doall                                  
                 (for [n (neighbours current)
                       :when (not (contains? @closed n))
                       :let [d (dist n current)
                             g-curr (get @g current (Double/POSITIVE_INFINITY))
                             g-tent (+ g-curr d)
                             g-n (get @g n (Double/POSITIVE_INFINITY))]
                       :when (or (not (contains? @open n))
                                 (< g-tent g-n))]
                   (do                      
                       (if (not (contains? @open n)) (var-set open (conj @open n)))
                       (var-set came-from
                                (assoc @came-from n current))
                       (var-set g
                                (assoc @g n g-tent))
                       (var-set f
                                (assoc @f n (+ g-tent (heur n end)))))))
                ))))
    (if @reached-goal (count (reconstruct-path @came-from end)) false)))

(defn part-2
  [{mol :molecule, reps :reps}]
  (let [reps' (map reverse reps)
        dist (reify java.util.function.BiFunction
               (apply [this _ _] 1.0))
        heur (reify java.util.function.BiFunction
               (apply [this a b] (double (Math/abs (- (count a) (count b))))))
        neighbours (reify java.util.function.Function
                     (apply [this n] (possible-molecules n reps')))]
    (Day19/aStarSearch mol "e" neighbours heur dist)))

(defn day-19
  [input-file]
  (let [input (slurp input-file)
        input' (parse-input input)]
    (println "Day 19")
    (println "Number of molecules: " (part-1 input'))
    (println "Path: " (part-2 input'))))
