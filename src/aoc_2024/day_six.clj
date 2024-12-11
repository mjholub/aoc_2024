(ns aoc-2024.day-six
  (:require [aoc-2024.core :refer [get-input-text indexed-map indices]]
            [clojure.string :as str]))

(defn parse-grid [input]
  (let [lines (str/split-lines input)
        height (count lines)
        width (count (first lines))
        grid (into {}
                   (for [y (range height)
                         x (range width)
                         :let [char (get-in lines [y x])]
                         :when (#{\# \^} char)]
                     [[y x] char]))
        start-pos (first (filter #(= (second %) \^) grid))]
    {:grid (into {} (filter #(= (second %) \#) grid))
     :start (first start-pos)
     :height height
     :width width}))

(def directions
  {:up    [-1 0]
   :right [0 1]
   :down  [1 0]
   :left  [0 -1]})

(def turn-right
  {:up :right
   :right :down
   :down :left
   :left :up})

(defn in-bounds? [{:keys [height width]} [y x]]
  (and (>= y 0) (< y height)
       (>= x 0) (< x width)))

(defn next-position [p dir]
  (mapv + p (directions dir)))

(defn walk-path [{:keys [grid start] :as state}]
  (loop [p start
         dir :up
         visited #{start}]
    (let [next-pos (next-position p dir)]
      (cond
        (not (in-bounds? state next-pos))
        visited

        (grid next-pos)
        (recur p (turn-right dir) visited)

        :else
        (recur next-pos dir (conj visited next-pos))))))

(defn solve-part-1 []
  (-> (get-input-text "six")
      parse-grid
      walk-path
      count))