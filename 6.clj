(require '[clojure.string :as str])

(def groups (str/split (slurp "6.txt") #"\n\n"))

;; 1st
(reduce + (map #(count (remove #{\newline} (set %))) groups))

;; 2nd
(reduce + (map (fn [group]
                 (count
                  (reduce
                   clojure.set/intersection
                   (map set (str/split group #"\n")))))
          groups))
