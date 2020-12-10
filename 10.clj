(require '[clojure.string :as str])

(def nums
  (sort (map #(Integer/parseInt %) (str/split (slurp "10.txt") #"\n"))))

;; 1st
(loop [d [1 1 1]
       i 0]
  (if (= i (dec (count nums)))
    (* (get d 0) (get d 2))
    (let [num (nth nums i)
          next-num (nth nums (inc i))
          update-index (dec (- next-num num))]
      (recur (assoc d update-index (inc (get d update-index)))
             (inc i)))))

;; 2nd
(def nums
  (into [] nums))

(defn update-storage [i d]
  (loop [j 1
         d d]
    (if (> j 3)
      d
      (let [num (nth nums i)
            next-num (+ num j)]
        (if (some #{next-num} nums)
          (recur (inc j)
                 (assoc d next-num (+ (nth d num) (nth d next-num))))
          (recur (inc j) d))))))

(def storage
  (loop [i 0
         d (into []
                 (concat [0 1 1 1]
                         (into [] (repeat (- (inc (last nums)) 4) 0))))]
    (if (= i (count nums))
      d
      (recur (inc i)
             (update-storage i d)))))

(println (last storage))
