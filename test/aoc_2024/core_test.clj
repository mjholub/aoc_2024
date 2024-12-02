(ns aoc-2024.core-test
  (:require [clojure.test :refer :all]
            [aoc-2024.core :refer :all]))

(deftest increasing-test
  (testing "recognizes increasing collections properly"
    (is (true? (increasing? '[1 2 3 4])))
         (is (false? (increasing? '[1 6 3 0 7])))
         (is (false? (increasing? '[0 0 4 2])))
         (is (false? (increasing? '[1 3 3 5])))))

(deftest decreasing-test
  (testing "recognizes strictly decreasing colls properly"
    (is (true? (decreasing? '[9 7 5 3])))
         (is (true? (decreasing? '[11 9.2 7.3 11/2])))
         (is (false? (decreasing? '[5 8 3 12])))
         (is (false? (decreasing? '[9 7 7 0])))))

(run-all-tests)