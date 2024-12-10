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

(defn valid-sequence?
  "base sequence validator:
   - (dec|inc)reasing sequence
   - step between 1 and 3"
  [nums]
  (and (seq nums) ; ensure non-empty
       (every? valid-diff? (partition 2 1 nums))
       (inc-or-dec? nums)))

(def seqs
  (->>
   (str/split (get-input-text "two") #"\n")
   (mapv #(str/split % #"\s+"))
   (mapv #(mapv parse-long %))))

(defn count-valid-seqs [validator seqs]
  (count (filterv validator seqs)))

(defn solution-part-one [input]
  (count-valid-seqs valid-sequence? seqs))

(solution-part-one seqs)

;; part two

(defn problem-dampener
  "Curried function that creates a dampened validator"
  [validator]
  (fn [coll]
    (or (validator coll)
        (some validator (mapv #(concat (take % coll)
                                       (drop (inc %) coll))
                              (range (count coll)))))))

(defn solution-part-two [input]
  (count-valid-seqs (problem-dampener valid-sequence?) input))

(solution-part-two seqs)