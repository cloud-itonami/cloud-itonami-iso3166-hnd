(ns statute.facts-test
  (:require [clojure.string :as str]
            [clojure.test :refer [deftest is]]
            [statute.facts :as facts]))

(deftest hnd-has-spec-basis
  (let [sb (facts/spec-basis "HND")]
    (is (= 2 (count sb)))
    (is (every? #(str/starts-with? (:statute/url %) "https://") sb))
    (is (every? :statute/law-number sb))))

(deftest unknown-jurisdiction-has-no-spec-basis
  (is (nil? (facts/spec-basis "ATL")))
  (is (nil? (facts/spec-basis "ZZZ"))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["HND" "JPN" "ATL"])]
    (is (= 3 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["ATL" "JPN"] (:missing-jurisdictions c)))))

(deftest by-topic-filters
  (is (= ["hnd.ley-transparencia-acceso-informacion-publica-decreto-170-2006"]
         (mapv :statute/id (facts/by-topic "HND" :transparency))))
  (is (empty? (facts/by-topic "HND" :labor)))
  (is (empty? (facts/by-topic "ATL" :transparency))))
