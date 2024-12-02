(ns aoc-2024.day-two
  (:require [aoc-2024.core :refer
             [decreasing? increasing? get-input-text]]
            [clojure.string :as str]))

(defn inc-or-dec? [levels]
  (or (increasing? levels)
      (decreasing? levels)))

(defn valid-diff?
  "Checks if difference is 1,2 or 3"
  [[a b]]
  (let [diff (abs (- b a))]
    (<= 1 diff 3)))

(defn valid-sequence? [nums]
  (and (seq nums) ; ensure non-empty
       (every? valid-diff? (partition 2 1 nums))
       (inc-or-dec? nums)))

(def seqs
  (->>
   (str/split (get-input-text "two") #"\n")
   (mapv #(str/split % #"\s+"))
   (mapv #(mapv parse-long %))))

(defn solution-part-one [input]
  (count
   (filterv true?
            (mapv #(valid-sequence? %) input))))

(solution-part-one seqs)