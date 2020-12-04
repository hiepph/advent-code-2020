(def nums (filter #(< % 2020) (map #(Integer/parseInt %) (clojure.string/split (slurp "1.txt") #"\n"))))
(def pairs (for [x nums y nums] (list x y)))
(reduce * (first (filter #(= (reduce + %) 2020) pairs)))
