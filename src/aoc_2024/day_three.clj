(ns aoc-2024.day-three
  (:require [aoc-2024.core :refer [get-input-text]]
            [clojure.string :as str]))

(defn mul-parser [token]
  (->
   (str/replace token #"mul\(" "(* ")
   (str/replace #"," " ")
   (read-string)
   (eval)))

(def part-one
  (->> (get-input-text "three")
       (re-seq #"mul\(\d+\,\d+\)")
       (map mul-parser)
       (reduce +)))

;; part 2
(comment "create a simple state machine for both tokens")
(defn parse-tokens [input]
  (->> input
       (re-seq #"(?:do\(\)|don't\(\)|mul\(\d+,\d+\))")
       (map (fn [token]
              (cond
                (= token "do()" {:type :enable})
                (= token "don't()" {:type :disable})
                :else {:type :multiply
                       :value (mul-parser token)})))))

(defn process-tokens [tokens]
  (loop [remaining tokens
         enabled? true
         sum 0]
    (if (empty? remaining)
      sum
      (let [token (first remaining)]
        (recur (rest remaining)
               (case (:type token)
                 :enable true
                 :disable false
                 enabled?)
               (if (and enabled? (= (:type token) :multiply))
                 (+ sum (:value token))
                 sum))))))

(def part-two 
  (->> (get-input-text "three")
       (parse-tokens)
       (map process-tokens)
       )
  )