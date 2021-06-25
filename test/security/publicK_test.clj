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
(is (not (r/isubclass? p/PlainText p/AliceCipherText)))
(is (not (r/isubclass? p/PlainText p/BobCipherText)))

(is (r/isubclass? p/CipherText p/BobCipherText))
(is (r/isuperclass? p/BobCipherText p/CipherText))

(is (not (r/isubclass? p/BobCipherText p/CipherText)))
(is (not (r/isuperclass? p/CipherText p/BobCipherText))))


(deftest PlainText

(is (r/isubclass? p/PlainText p/AlicePlainText))
(is (r/isuperclass? p/AlicePlainText p/PlainText))

(is (not (r/isubclass? p/AlicePlainText p/PlainText)))
(is (not (r/isuperclass? p/PlainText p/AlicePlainText)))



(is (r/isubclass? p/PlainText p/BobPlainText))
(is (r/isuperclass? p/BobPlainText p/PlainText))

(is (not (r/isubclass? p/BobPlainText p/PlainText)))
(is (not (r/isuperclass? p/PlainText p/BobPlainText))))


(deftest PublicKey
(is (r/isubclass? p/PublicKey p/AlicePublicKey))
(is (r/isubclass? p/PublicKey p/BobPublicKey)))


(deftest PrivateKey
(is (r/isuperclass? p/AlicePrivateKey p/PrivateKey))
(is (r/isuperclass? p/BobPrivateKey p/PrivateKey))

(is (not (r/isuperclass? p/AlicePrivateKey p/PublicKey)))



)




