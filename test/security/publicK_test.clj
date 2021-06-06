(ns security.publicK-test
    (:use [clojure.test])
    (:require
     [security.publicK ]
     [tawny.owl :as o]
     [tawny.reasoner :as r]
     [tawny.fixture :as f]))

(use-fixtures :each (f/reasoner :hermit))

(deftest reasonable
  (is (r/consistent? security.publicK/PublickEeyCryptography))
  (is (r/coherent? security.publicK/PublicKeyCryptography)))
