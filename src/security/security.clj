
(ns security.security
(:use [tawny.owl])
  (:require [tawny.owl :refer :all]
            [tawny.english]
            [tawny.reasoner :as r]))

(defontology SecurityVocab
   :iri "http://www.russet.org.uk/tawny/security/securityVocab"
   :comment "The vovabulary for Security domain")
(reasoner-factory :hermit)


(defclass Key
  :ontology SecurityVocab
  
  :comment "The tool use to encrypt & decrypt the message")

(defoproperty hasKey
 :ontology SecurityVocab)

(defoproperty hasKnowledgeOf
  :ontology SecurityVocab)


(as-disjoint
 (defclass AliceKey
   
   :comment "K.......")
 (defclass BobKey
   
   :comment "K......"))

(defclass One
  :super Key)

(defclass Alice
  :super (owl-some hasKey AliceKey)
         (owl-some hasKnowledgeOf AliceKey))
 

(defclass Bob
  :super (owl-some hasKey BobKey)
         (owl-some hasKnowledgeOf BobKey))
  

(defclass Eve
  :super (only hasKnowledgeOf(owl-not (owl-or AliceKey BobKey))))

(defclass Encryption
  :ontology SecurityVocab
  :label "security"
  :comment "The proces to change plainText to CipherText")


(as-inverse
(defoproperty isEncryptionOf 
:domain CipherText
:range PlainText )
(defoproperty isDecryptionOf 
:domain PlainText
:range CipherText))


(defoproperty hasEncrypted
  :ontology SecurityVocab)

(defoproperty CanEncrypt
 :ontology SecurityVocab)

(defclass Decryption
  :ontology SecurityVocab
  
  :comment "The proces to change CiphrtText to PlainText")

(defoproperty hasDecrypted 
  :ontology SecurityVocab)

(defoproperty CanDecrypt
 :ontology SecurityVocab)

(defclass CipherText
  :ontology SecurityVocab
  
  :comment "The message after encryption")


(defclass PlainText
  :ontology SecurityVocab
  
  :comment "The message before encryption"
  :super
  (owl-some isDecryptionOf CipherText))

(refine  CipherText
  :super
  (owl-some isEncryptionOf PlainText))

;;(defclass CanDecrypt/CanEncrypt :equivalent (owl-some hasKnoewledgeOf Key)



(save-ontology "SecurityVocab.omn" :omn)
(save-ontology "SecurityVocab.owl" :owl)

;;(tawny.reasoner/coherent?)
