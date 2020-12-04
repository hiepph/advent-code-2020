(def worlds (clojure.string/split (slurp "3.txt") #"\n"))
(def step (count (first worlds)))

(defn travel [ws [right down]]
  (loop [w 1
         i right
         res 0]
    (if (>= w (count ws))
      res
      (if (= (get (get ws w) i) \#)
        (recur (+ w down) (mod (+ i right) step) (inc res))
        (recur (+ w down) (mod (+ i right) step) res)))))

(reduce * (map (partial travel worlds) '((1 1) (3 1) (5 1) (7 1) (1 2))))
