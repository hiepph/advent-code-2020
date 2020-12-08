(require '[clojure.string :as str])

(def lines
  (map (fn [line]
         (apply hash-map
                (let [[op num] (str/split line #" ")]
                  [:op op :num (Integer/parseInt num)])))
       (str/split (slurp "8.txt") #"\n")))


;; 1st
(loop [i 0
       acc 0
       history #{}]
  (if (contains? history i)
    acc
    (let [line (nth lines i)
          op (:op line)
          num (:num line)]
      (cond (= op "nop") (recur (inc i)
                                acc
                                (conj history i))
            (= op "acc") (recur (inc i)
                                (+ acc num)
                                (conj history i))
            (= op "jmp") (recur (+ i num)
                                acc
                                (conj history i))))))

;; 2nd
(defn run [codes]
  (loop [i 0
         acc 0
         history #{}]
    (if (or
         (>= i (count codes))
         (contains? history i))
      {:acc acc
       :last i}
    (let [line (nth codes i)
          op (:op line)
          num (:num line)]
      (cond (= op "nop") (recur (inc i)
                                acc
                                (conj history i))
            (= op "acc") (recur (inc i)
                                (+ acc num)
                                (conj history i))
            (= op "jmp") (recur (+ i num)
                                acc
                                (conj history i)))))))

(defn assoc-seq [s i v]
  (map-indexed (fn [j x] (if (= i j) v x)) s))


(def all-possible-codes
  (map (fn [[i lines]]
         (let [line (nth lines i)]
           (cond
             (= (:op line) "nop") (assoc-seq lines i {:op "jmp" :num (:num line)})
             (= (:op line) "jmp") (assoc-seq lines i {:op "nop" :num (:num line)})
             :else lines)))
       (map vector (range (count lines)) (repeat (count lines) (doall lines)))))

((comp :acc first)
 (filter #(= (:last %) (count lines)) (map run all-possible-codes)))
