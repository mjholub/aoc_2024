(ns aoc-2024.core
  (:require [clojure.java.io :as io]))

(defn get-input-text [day]
  (slurp (io/resource (str "day_" day "/input.txt"))))

(defn increasing? [coll]
  (if-not (every? number? coll)
    false
    (= coll (seq (sort (distinct coll))))))

(defn decreasing? [coll]
  (if-not (every? number? coll)
    false
    (= coll (seq (reverse (sort (distinct coll)))))))