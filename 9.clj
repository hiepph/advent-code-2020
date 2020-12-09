(require '[clojure.string :as str])

(def nums
  (map #(Long/parseLong %) (str/split (slurp "9.txt") #"\n")))

(defn sum-to? [s l]
  (some #(= s %)
        (for [x l
              y l]
          (+ x y))))

;; 1st
(def invalid-num
  (loop [i 25]
    (let [num (nth nums i)]
      (if (not (sum-to? num
                        (take 25 (drop (- i 25) nums))))
        num
        (recur (inc i))))))



;; 2nd
(defn contagious-sum [i target]
  (loop [j (inc i)
         sum (nth nums i)
         res [(nth nums i)]]
    (cond
      (= sum target) res
      (> sum target) []
      :else (recur (inc j)
                   (+ sum (nth nums j))
                   (conj res (nth nums j))))))

(def series
  (loop [i 0]
    (let [s (contagious-sum i invalid-num)]
      (if (not (empty? s))
        s
        (recur (inc i))))))

(+ (apply min series) (apply max series))
