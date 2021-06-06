(ns security.security-test
    (:use [clojure.test])
    (:require
     [security.security ]
     [tawny.owl :as o]
     [tawny.reasoner :as r]
     [tawny.fixture :as f]))

(use-fixtures :each (f/reasoner :hermit))

(deftest reasonable
  (is (r/consistent? security.security/SecurityVocab))
  (is (r/coherent? security.security/SecurityVocab)))
