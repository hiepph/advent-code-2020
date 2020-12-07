(require '[clojure.string :as str])

(def lines (str/split (slurp "7.txt") #"\n"))
(def bags
  (into {} (map
            (fn [line]
              (let [comps
                    (filter
                     #(not (contains? (set ["contain" "bag" "bags,"
                                            "bags" "bag," "bags." "bag."]) %))
                     (str/split line #" "))]
                [(str/join " " (take 2 comps))
                 (into {} (map (fn [col]
                                 (let [[quan name1 name2] col]
                                   [(str/join " " [name1 name2])
                                    (Integer/parseInt quan)]))
                               (partition 3 (drop 2 comps))))]))
            lines)))

(defn contain-shiny-gold? [k]
  (let [bag (get bags k)]
    (or
     (contains? bag "shiny gold")
     (some true? (map contain-shiny-gold? (keys bag))))))

;; 1st
(count (filter contain-shiny-gold? (keys bags)))

;; 2nd
(defn count-bags [k]
  (let [bag (get bags k)]
    (if (empty? bag)
      0
      (reduce +
              (reduce + (vals bag))
              (map (fn [[child-k v]]
                     (* v (count-bags child-k))) bag)))))
