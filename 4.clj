(def passports
  (map (fn [p]
         (into {}
               (map #(clojure.string/split % #":")
                    (clojure.string/split p #" |\n"))))
         (clojure.string/split (slurp "4.txt") #"\n\n")))


;; 1st
(count (filter (fn [p]
                 (or
                  (= (count p) 8)
                  (and (= (count p) 7) (nil? (get p "cid")))))
               passports))

;; 2nd
(count (filter (fn [p]
                 (and
                  (= (count (dissoc p "cid")) 7)
                  (let [byr (Integer/parseInt (get p "byr"))
                        iyr (Integer/parseInt (get p "iyr"))
                        eyr (Integer/parseInt (get p "eyr"))
                        ;; hgt-num (Integer/parseInt
                        ;;          (apply str (drop-last 2 (get p "hgt"))))
                        ;; hgt-unit (apply str (take-last 2 (get p "hgt")))
                        hgt (get p "hgt")
                        hcl (get p "hcl")
                        ecl (get p "ecl")
                        pid (get p "pid")]
                    (and
                     (<= 1920 byr 2002)
                     (<= 2010 iyr 2020)
                     (<= 2020 eyr 2030)
                     (let [hgt-unit (apply str (take-last 2 hgt))
                           hgt-num-str (apply str (drop-last 2 hgt))
                           hgt-num (if (empty? hgt-num-str)
                                 0
                                 (Integer/parseInt hgt-num-str))]
                       (or
                        (and (= hgt-unit "in") (<= 59 hgt-num 76))
                        (and (= hgt-unit "cm") (<= 150 hgt-num 193))))
                     (and
                      (= (count hcl) 7)
                      (= (first hcl) \#)
                      (if (re-find #"^[a-f0-9]+$" (subs hcl 1)) true false))
                     (contains? (set ["amb" "blu" "brn" "gry" "grn" "hzl" "oth"]) ecl)
                     (= (count pid) 9)
                     ))))
               passports))
