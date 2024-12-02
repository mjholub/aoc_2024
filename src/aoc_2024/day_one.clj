(ns aoc-2024.day-one 
  (:require
   [aoc-2024.core :refer [get-input-text]]
   [clojure.string :as str]))

(defn clean-pairs [input]
  (->> input 
       (mapcat #(str/split (str/trim %) #"\s+"))
       (mapv #(parse-long %))))

(defn make-list [v] (sort (vec (take-nth 2 v))))

(defn answer []
  (let [pairs (str/split (get-input-text "one") #"\n")
        cleaned (clean-pairs pairs)
        left-list (make-list (rest cleaned)) ;; odd indices
        right-list (make-list cleaned)]
  {:part-one
   (reduce + (mapv #(abs (- %1 %2)) left-list right-list))
   :part-two nil
   }))
