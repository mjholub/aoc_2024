(ns aoc-2024.day-four
  (:require [clojure.string :as str]))

(comment
  "we got 140 lines, each 140 chars long
   maybe zipmap each char to the column number?
   ")

(def lines
  (str/split-lines (get-input-text "four")))

(def vertical (apply mapv str lines))

(defn counter [pattern s] (->> (map #(re-seq pattern %) s)
                               (flatten)
                               (remove empty?)
                               (count)))

(def directions
  (for [x [-1 0 1]
        y [-1 0 1] :when (not= 0 x y)]
    [x y]))

(defn in-bounds?
  "Checks if coordinates are within grid boundaries"
  [grid [^Long row ^Long col]]
  (and (< -1 row (count grid))
       (< -1 col (count (first grid)))))

(defn char-at
  "Retrieves character at coordinates, returns nil if out of bounds"
  [grid [^Long row ^Long col]]
  (when (in-bounds? grid [row col]) (get-in grid [row col])))

(defn follow-path
  "Follows path from coordinate `s` in direction `d`, returns all encountered chars"
  [grid s d]
  (let [positions (take 4 (iterate (fn [[row col]]
                                     [(+ row (first d))
                                      (+ col (second d))]) s))]
    (->> positions
         (mapv (partial char-at grid))
         (take-while some?)
         (apply str))))

(defn search-from-position
  [grid [row col] ^String word]
  (when (= (first word) (char-at grid [row col]))
    (->> directions
         (some (fn [direction]
                 (when (= word (follow-path grid [row col] direction))
                   [row col]))))))

(defn search-word
  [grid word]
  (let [rows (range (count grid))
        cols (range (count (first grid)))
        positions (for [row rows col cols] [row col])
        chunk-size 4
        position-chunks (partition-all chunk-size positions)
        search-results (pmap (fn [chunk]
                               (keep #(search-from-position grid % word) chunk)) position-chunks)]
    (vec (mapcat identity search-results))))

;; we might skip parametrizing the word to find since we only got 2 options
(defn search [grid ^Long row ^Long col]
  (let [grid-len (count grid)
        n (count (first grid))]))

(defn diagonals [grid]
  (let [height (count grid)
        width  (count (first grid))
        coords (for [y (range height)
                     x (range width)]
                 [y x])
        fwd-diag (fn [[y x]]
                   (apply str
                          (for [i (range (min (- height y) (- width x)))]
                            (get-in grid [(+ y i) (+ x i)]))))
        backward-diag (fn [[y x]]
                        (apply str
                               (for [i (range (min (- height y) (+ 1 x)))]
                                 (get-in grid [(+ y i) (- x i)]))))]
    {:forward (remove (or empty? #(> 4 %)) (mapv fwd-diag coords))
     :backward (remove (or empty? #(> 4 %)) (mapv backward-diag coords))}))