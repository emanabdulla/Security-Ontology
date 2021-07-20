
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

(defclass Person
  :comment "Represents the set of people involved in encryption and decryption process")


(defoproperty hasKey
  :domain Person
  :range Key)

(defoproperty hasKnowledgeOf
  :domain Person
  :range Key)

(defclass SecretKey
  :super Key
  :comment "is used to encrypt and decrypt a message")


(as-subclasses 
 Person
 :disjoint

 (defclass Alice
   :super (owl-some hasKey SecretKey)
   (owl-some hasKnowledgeOf SecretKey))

  (defclass Bob
  :super (owl-some hasKey SecretKey)
         (owl-some hasKnowledgeOf SecretKey))
  (defclass Eve
  :super (only hasKnowledgeOf(owl-not (owl-or SecretKey Key)))))

(defclass Encryption
  :ontology SecurityVocab
  :label "security"
  :comment "The proces to change plainText to CipherText")

(defoproperty hasEncryptionKey
  :range Key)

(defoproperty hasDecryptionKey
  :range Key)

(defclass CipherText
  :super (owl-some hasDecryptionKey SecretKey)
  :ontology SecurityVocab
  :comment "The message after encryption(encrypted data)")

(defclass PlainText
  :super (owl-some hasEncryptionKey SecretKey)
  :ontology SecurityVocab
  :comment "The message before encryption(Unencrypted data)")

(as-inverse
 (defoproperty isEncryptionOf 
  :domain CipherText
  :range PlainText )

 (defoproperty isDecryptionOf 
  :domain PlainText
  :range CipherText))


(defclass Decryption
  :ontology SecurityVocab
  :comment "The process of changing ciphertext into plaintext")


(refine CipherText
  :super
  (owl-some isEncryptionOf PlainText))

(refine PlainText
  :super
 (owl-some isDecryptionOf CipherText))

(save-ontology "SecurityVocab.omn" :omn)
(save-ontology "SecurityVocab.owl" :owl)


