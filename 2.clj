;;
(def passwords (clojure.string/split (slurp "2.txt") #"\n"))
(count (filter (fn [p]
                 (let [[x y pass] (clojure.string/split p #" ")
                       [low high] (map #(Integer/parseInt %) (clojure.string/split x #"-"))
                       c (first y)
                       n (count (filter #(= c %) pass))]
                   (and (<= low n) (<= n high)))) passwords))

;;
(count (filter (fn [p]
                 (let [[x y pass] (clojure.string/split p #" ")
                       [low high] (map #(Integer/parseInt %) (clojure.string/split x #"-"))
                       c (first y)
                       n (count (filter #(= c %) pass))
                       trueLow (= (get pass (dec low)) c)
                       trueHigh (= (get pass (dec high)) c)]
                   (not= trueLow trueHigh))) passwords))
