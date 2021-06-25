
(ns security.security
(:use [tawny.owl])
  (:require [tawny.owl :refer :all]
            [tawny.english]
            [tawny.reasoner :as r]))

(defontology SecurityVocab
   :iri "http://www.russet.org.uk/tawny/security/securityVocab"
   :comment "The vovabulary for Security domain")

(r/reasoner-factory :hermit)


(defclass Key
  :ontology SecurityVocab
  :comment "The tool use to encrypt & decrypt the message")

(defoproperty hasKey
 :ontology SecurityVocab)

(defoproperty hasKnowledgeOf
  :ontology SecurityVocab)

(defclass SecretKey
  :super Key
  :comment "is used to encrypt and decrypt a message")

(defclass Alice
  :super (owl-some hasKey SecretKey)
         (owl-some hasKnowledgeOf SecretKey))
 

(defclass Bob
  :super (owl-some hasKey SecretKey)
         (owl-some hasKnowledgeOf SecretKey))
  

(defclass Eve
  :super (only hasKnowledgeOf(owl-not (owl-or SecretKey Key))))


(defclass Encryption
  :ontology SecurityVocab
  :label "security"
  :comment "The proces to change plainText to CipherText")

(defoproperty hasEncryptionKey
  :ontology SecurityVocab)
(defoproperty hasDecryptionKey
  :ontology SecurityVocab)

(defclass CipherText
  :super (owl-some hasDecryptionKey SecretKey)
  :ontology SecurityVocab
  :comment "The message after encryption")


(defclass PlainText
  :super (owl-some hasEncryptionKey SecretKey)
  :ontology SecurityVocab
  :comment "The message before encryption")

(as-inverse
 (defoproperty isEncryptionOf 
  :domain CipherText
  :range PlainText )

 (defoproperty isDecryptionOf 
  :domain PlainText
  :range CipherText))





(defclass Decryption
  :ontology SecurityVocab
  
  :comment "The proces to change CiphrtText to PlainText")

(defoproperty hasDecrypted 
  :ontology SecurityVocab)

(defoproperty CanDecrypt
 :ontology SecurityVocab)



(refine CipherText
  :super
  (owl-some isEncryptionOf PlainText))

(refine PlainText
  :super
 (owl-some isDecryptionOf CipherText))


(save-ontology "SecurityVocab.omn" :omn)
(save-ontology "SecurityVocab.owl" :owl)


