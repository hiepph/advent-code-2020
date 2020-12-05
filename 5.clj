(def passes (clojure.string/split (slurp "5.txt") #"\n"))

;; 1
(defn value [p l h sep]
  (loop [cur p
         low l
         high h]
    (if (empty? cur)
      low
      (let [c (first cur)]
        (if (= c sep)
          (recur
           (subs cur 1)
           low
           (- high 1 (quot (- high low) 2)))
          (recur
           (subs cur 1)
           (+ low 1 (quot (- high low) 2))
           high))))))

(def seat-ids (map (fn [p]
                      (let [row (value (subs p 0 7) 0 127 \F)
                            column (value (subs p 7) 0 7 \L)
                            ]
                        (+ (* row 8) column)))
                    passes))

(apply max seat-ids)

;; 2
(def max-id (apply max seat-ids))
(def min-id (apply min seat-ids))

(clojure.set/difference (set (range min-id max-id)) seat-ids)
