(ns security.publicK-test
    (:use [clojure.test])
    (:require
     [security.publicK :as p]
    
     [tawny.owl :as o]
     [tawny.reasoner :as r]
     [tawny.fixture :as f]))

(defn ontology-reasoner-fixture [tests]
  (r/reasoner-factory :hermit)
  (o/ontology-to-namespace p/PublicKeyCryptography)
  (binding [r/*reasoner-progress-monitor*
            (atom
            r/reasoner-progress-monitor-silent)]
    (tests)))

(use-fixtures :once ontology-reasoner-fixture)




(use-fixtures :each (f/reasoner :hermit))



(deftest reasonable
  (is (r/consistent? p/PublicKeyCryptography))
  (is (r/coherent? p/PublicKeyCryptography)))


(deftest cipherText

(is (r/isubclass? p/CipherText p/AliceCipherText))
(is (r/isuperclass? p/AliceCipherText p/CipherText))

(is (not (r/isubclass? p/AliceCipherText p/CipherText)))
(is (not (r/isuperclass? p/CipherText p/AliceCipherText)))
)





