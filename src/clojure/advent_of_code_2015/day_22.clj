(ns advent-of-code-2015.day-22
  (:import [advent_of_code_2015 Day22])
  (:gen-class))

(def initial-state
  {:player {:hp 50, :mana 500, :armour 0}
   :boss {:hp 71, :damage 10, :armour 0}
   :effects []
   :mana-spent 0})

(defn cast-spell
  "Casts a spell, deducting the mana cost from the player and applying any damage and effects. It is assumed that the player has enough mana to cast the spell."
  [state spell]
  (condp = spell
    :magic-missile
    (-> state
        (update-in [:player :mana] - 53)
        (update :mana-spent + 53)
        (update-in [:boss :hp] - 4))

    :drain
    (-> state
        (update-in [:player :mana] - 73)
        (update :mana-spent + 73)
        (update-in [:boss :hp] - 2)
        (update-in [:player :hp] + 2))

    :shield
    (-> state
        (update-in [:player :mana] - 113)
        (update :mana-spent + 113)
        (update :effects conj {:duration 6,
                               :source :shield
                               :effect identity,
                               :expiry #(update-in % [:player :armour] - 7)})
        (update-in [:player :armour] + 7))

    :poison
    (-> state
        (update-in [:player :mana] - 173)
        (update :mana-spent + 173)
        (update :effects conj {:duration 6,
                               :source :poison
                               :effect #(update-in % [:boss :hp] - 3)
                               :expiry identity}))

    :recharge
    (-> state
        (update-in [:player :mana] - 229)
        (update :mana-spent + 229)
        (update :effects conj {:duration 5,
                               :source :recharge
                               :effect #(update-in % [:player :mana] + 101)
                               :expiry identity}))))

(defn boss-attack
  "Performs the boss' attack against the player."
  [state]
  (let [damage (get-in state [:boss :damage])
        armour (get-in state [:player :armour])]
    (update-in state [:player :hp] - (max 1 (- damage armour)))))

(defn tick-effects
  "Ticks all currently active effects."
  [state]
  (reduce (fn [s {dur :duration, eff :effect, exp :expiry, :as e}]
            (if (> dur 0)
              (-> s
                  (eff)
                  (update :effects conj (update e :duration dec)))
              (update s :effects conj e)))
          (assoc state :effects [])
          (:effects state)))

(defn tick-expiry
  "Removes all expired effects."
  [state]
  (reduce (fn [s {dur :duration, eff :effect, exp :expiry, :as e}]
            (if (= dur 0)
              (-> s
                  (exp))
              (update s :effects conj e)))
          (assoc state :effects [])
          (:effects state)))


(defn ended?
  "Tests whether the combat has ended."
  [state]
  (or (>= 0 (get-in state [:player :hp]))
      (>= 0 (get-in state [:boss :hp]))))

(defn ended-or
  "Performs action on state if the not ended."
  [state action]
  (if (ended? state) state (action state)))

(defn player-attack
  "Ticks a single round of combat, performing the player's action, and then the boss' where applicable."
  [state spell]
  (-> state
      (cast-spell spell)
      (ended-or tick-effects)
      (ended-or tick-expiry)
      (ended-or boss-attack)
      (ended-or tick-effects)
      (ended-or tick-expiry)))

(defn player-attack-hard
  [state spell]
  (-> state
      (cast-spell spell)
      (ended-or tick-effects)
      (ended-or tick-expiry)
      (ended-or boss-attack)
      (ended-or tick-effects)
      (ended-or tick-expiry)
      (ended-or #(update-in % [:player :hp] dec))))

(defn spell-options
  "Determines what spells can be cast from the current state."
  [state]
  (let [current-mana (get-in state [:player :mana])
        active-effects (map :source (:effects state))]
    (filter identity
            [(if (>= current-mana 53)
               :magic-missile)
             (if (>= current-mana 73)
               :drain)
             (if (and (>= current-mana 113)
                      (not-any? (partial = :shield) active-effects))
               :shield)
             (if (and (>= current-mana 173)
                      (not-any? (partial = :poison) active-effects))
               :poison)
             (if (and (>= current-mana 229)
                      (not-any? (partial = :recharge) active-effects))
               :recharge)])))

(defn part-1
  []
  (let [end-test (reify java.util.function.Function
                   (apply [this node]
                     (and (ended? node)
                          (< 0 (get-in node [:player :hp])))))
        
        neighbours (reify java.util.function.Function
                     (apply [this node]
                       (map (partial player-attack node) (spell-options node))))]
    (:mana-spent
     (Day22/breadthFirstSearch initial-state end-test neighbours))))

(defn part-2
  []
  (let [end-test (reify java.util.function.Function
                   (apply [this node]
                     (and (ended? node)
                          (< 0 (get-in node [:player :hp])))))
        neighbours (reify java.util.function.Function
                     (apply [this node]
                       (map (partial player-attack-hard node) (spell-options node))))]
    (:mana-spent
     (Day22/breadthFirstSearch (update-in initial-state [:player :hp] dec)
                               end-test neighbours))))

(defn day-22
  []
  (println "Day 22")
  (println "Mana spent (Normal): " (part-1))
  (println "Mana spent (Hard):   " (part-2)))
