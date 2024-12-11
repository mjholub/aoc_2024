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

(defn indices [pred coll]
  (keep-indexed #(when (pred %2) %1) coll))

(defn indexed-map
  "Creates a map where `f` is applied to elements of `coll`.
   The results are used as values,
   while the indices of the current iteration are used as keys
   Example 1: Double each number
   (indexed-map #(* 2 %) [1 2 3 4])
   => {0 2, 1 4, 2 6, 3 8}

  Example 2: Convert strings to uppercase
  (comment (indexed-map clojure.string/upper-case [\" a \" \" b \" \" c \"])
  => {0 'A', 1 'B', 2 'C'})"
  [f coll]
  (into {} (map-indexed (fn [i x] [i (f x)]) coll)))