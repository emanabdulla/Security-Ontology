(ns security.security-test
    (:use [clojure.test])
    (:require
     [security.security :as s]
     [tawny.owl :as o]
     [tawny.reasoner :as r]
     [tawny.fixture :as f]))

(defn ontology-reasoner-fixture [tests]
  (r/reasoner-factory :hermit)
  (o/ontology-to-namespace s/SecurityVocab)
  (binding [r/*reasoner-progress-monitor*
            (atom
            r/reasoner-progress-monitor-silent)]
    (tests)))

(use-fixtures :once ontology-reasoner-fixture)

(use-fixtures :each (f/reasoner :hermit))

(deftest reasonable
  (is (r/consistent? s/SecurityVocab))
  (is (r/coherent? s/SecurityVocab)))

(deftest Key
(is (r/isubclass? s/Key s/SecretKey))
(is (not (r/isuperclass? s/Key s/SecretKey))))

