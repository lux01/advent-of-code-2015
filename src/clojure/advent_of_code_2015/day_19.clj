(ns advent-of-code-2015.day-19
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

(defn levenshtein [str1 str2]
  "a Clojure levenshtein implementation using transient data structure"
  (let [n (count str1) m (count str2)]
    (cond 
     (= 0 n) m
     (= 0 m) n
     :else
     (let [prev-col (transient (vec (range (inc m)))) col (transient [])] ; initialization for the first column.
       (dotimes [i n]
         (assoc! col 0 (inc i)) ; update col[0]
         (dotimes [j m]
           (assoc! col (inc j)  ; update col[1..m] 
                   (min (inc (nth col j))
                        (inc (nth prev-col (inc j)))
                        (+ (get prev-col j) (if (= (nth str1 i) (nth str2 j)) 0 1)))))
         (dotimes [i (count prev-col)] 
           (assoc! prev-col i (get col i)))) ; 
       (last (persistent! col)))))) ; last element of last column

(defn part-2
  [{mol :molecule, reps :reps}]
  (let [dist (constantly 1)
        heur #(Math/abs (- (count %1) (count %2)))
        neighbours #(possible-molecules % reps)]
    (a* "e" mol heur dist neighbours)))

(defn day-19
  [input-file]
  (let [input (slurp input-file)
        input' (parse-input input)]
    (println "Day 19")
    (println "Number of molecules: " (part-1 input'))
    (println "Path: " (part-2 input'))))
