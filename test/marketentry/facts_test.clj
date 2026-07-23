(ns marketentry.facts-test
  (:require [clojure.test :refer [deftest is testing]]
            [marketentry.facts :as facts]))

(deftest hnd-has-spec-basis
  (let [sb (facts/spec-basis "HND")]
    (is (some? sb))
    (is (string? (:provenance sb)))
    (is (seq (:required-evidence sb)))
    (is (= 5 (count (:required-evidence sb))) "exactly 5 required-evidence items, not padded")
    (is (some? (facts/rep-spec-basis "HND")))
    (is (some? (facts/corporate-number-spec-basis "HND")))
    (is (some? (facts/camara-jurisdiction-spec-basis "HND")))))

(deftest unknown-jurisdiction-has-no-spec-basis
  (is (nil? (facts/spec-basis "ATL")))
  (is (nil? (facts/spec-basis "ZZZ"))))

(deftest required-evidence-satisfied
  (let [sb (facts/spec-basis "HND")
        all (:required-evidence sb)]
    (is (true? (facts/required-evidence-satisfied? "HND" all)))
    (is (not (facts/required-evidence-satisfied? "HND" (take 1 all))))
    (is (nil? (facts/required-evidence-satisfied? "ATL" all)))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["HND" "USA" "ATL"])]
    (is (= 3 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["ATL" "USA"] (:missing-jurisdictions c)))))

(deftest no-fabricated-live-honducompras-fetch
  (testing "honducompras.gob.hn itself had an expired TLS cert this session -- must never be cited as an independently-browsed live source"
    (let [sb (facts/spec-basis "HND")]
      (is (re-find #"EXPIRED TLS CERTIFICATE" (:provenance sb))
          "the gap must be disclosed in the provenance note, not concealed")
      (is (re-find #"oncae\.gob\.hn" (:provenance sb))))))

(deftest camara-jurisdiction-is-decentralized-by-basis
  (testing "the flagship basis cites Código de Comercio Arts. 384-386/420 -- a department-scoped registry, not a national one"
    (let [cj (facts/camara-jurisdiction-spec-basis "HND")]
      (is (re-find #"384" (:camara-jurisdiction-legal-basis cj)))
      (is (re-find #"385" (:camara-jurisdiction-legal-basis cj)))
      (is (re-find #"420" (:camara-jurisdiction-legal-basis cj)))
      (is (= #{"Francisco Morazán"} (get-in cj [:camara-jurisdiction-departments "CCIT"])))
      (is (= #{"Cortés"} (get-in cj [:camara-jurisdiction-departments "CCIC"]))))))

(deftest camara-covers-department-is-honestly-scoped
  (testing "only the two independently-confirmed chambers are ever considered valid -- an unlisted camara is NEVER assumed to cover any department"
    (is (true? (facts/camara-covers-department? "HND" "CCIT" "Francisco Morazán")))
    (is (true? (facts/camara-covers-department? "HND" "CCIC" "Cortés")))
    (is (false? (facts/camara-covers-department? "HND" "CCIT" "Cortés")))
    (is (false? (facts/camara-covers-department? "HND" "CCIC" "Francisco Morazán")))
    (is (false? (facts/camara-covers-department? "HND" "CCSPS-UNVERIFIED" "Atlántida"))
        "an unverified chamber id must never be silently treated as valid")
    (is (false? (facts/camara-covers-department? "ATL" "CCIT" "Francisco Morazán"))
        "no spec-basis for the jurisdiction at all -> never covered")))

(deftest rep-spec-basis-reuses-codigo-de-comercio-citation
  (testing "the foreign-company representative requirement cites the SAME Decreto 73-50 as statute.facts, never a second different citation"
    (let [rep (facts/rep-spec-basis "HND")]
      (is (re-find #"308" (:rep-legal-basis rep)))
      (is (re-find #"wipo\.int.*hn009es\.pdf" (:rep-provenance rep))))))
