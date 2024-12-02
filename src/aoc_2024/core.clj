(ns aoc-2024.core
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn get-input-text [day]
  (slurp (io/resource (str "day_" day "/input.txt"))))
