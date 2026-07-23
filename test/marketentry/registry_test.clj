(ns marketentry.registry-test
  (:require [clojure.test :refer [deftest is testing]]
            [marketentry.registry :as registry]))

(deftest engagement-fee-recompute
  (let [e {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12 :claimed-fee 860000.0}]
    (is (== 860000.0 (registry/compute-engagement-fee e)))
    (is (true? (registry/engagement-fee-matches-claim? e))))
  (let [bad {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12 :claimed-fee 999000.0}]
    (is (false? (registry/engagement-fee-matches-claim? bad)))))

(deftest camara-jurisdiction-mismatch-flagship
  (testing "a chamber that does not cover the claimed domicile is a mismatch"
    (is (true? (registry/camara-jurisdiction-mismatch?
                {:jurisdiction "HND" :registering-camara "CCIT" :business-domicile-department "Cortés"}))))
  (testing "a chamber that DOES cover the claimed domicile is NOT a mismatch"
    (is (false? (registry/camara-jurisdiction-mismatch?
                 {:jurisdiction "HND" :registering-camara "CCIT" :business-domicile-department "Francisco Morazán"})))
    (is (false? (registry/camara-jurisdiction-mismatch?
                 {:jurisdiction "HND" :registering-camara "CCIC" :business-domicile-department "Cortés"}))))
  (testing "an unverified/unlisted chamber is ALWAYS a mismatch -- never silently assumed valid"
    (is (true? (registry/camara-jurisdiction-mismatch?
                {:jurisdiction "HND" :registering-camara "CCSPS-UNVERIFIED" :business-domicile-department "Atlántida"})))))

(deftest register-draft-and-submit
  (let [d (registry/register-draft "eng-1" "HND" 0)
        s (registry/register-submit "eng-1" "HND" 0)]
    (is (= "HND-DFT-000000" (get d "draft_number")))
    (is (= "HND-SUB-000000" (get s "submit_number")))
    (is (nil? (get-in d ["certificate" "proof"])))
    (is (= "draft-unsigned" (get-in s ["certificate" "status"])))))

(deftest register-requires-ids
  (is (thrown? Exception (registry/register-draft "" "HND" 0)))
  (is (thrown? Exception (registry/register-submit "eng-1" "" 0))))
