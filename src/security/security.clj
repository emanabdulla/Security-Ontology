
(ns security.security
(:use [tawny.owl])
  (:require [tawny.owl :refer :all]
            [tawny.english]
            [tawny.reasoner]))

(defontology SecurityVocab
   :iri "http://www.russet.org.uk/tawny/security/securityVocab"
   :comment "The vovabulary for Security domain")

(defclass Key
  :ontology SecurityVocab
  :label "security"
  :comment "The tool use to encrypt & decrypt the message")

(defoproperty hasKey
 :ontology SecurityVocab)

(defoproperty hasKnowledgeOf
  :ontology SecurityVocab)


(as-disjoint
 (defclass AliceKey
   :label "Key"
   :comment "K.......")
 (defclass BobKey
   :label "Key"
   :comment "K......"))


(defclass Alice
  :super (owl-some hasKey AliceKey)
         (owl-some hasKnowledgeOf AliceKey))
 

(defclass Bob
  :super (owl-some hasKey BobKey)
         (owl-some hasKnowledgeOf BobKey))
  

(defclass Eve
   (owl-not hasKnowledgeOf AliceKey))

(defclass Encryption
  :ontology SecurityVocab
  :label "security"
  :comment "The proces to change plainText to CipherText")

(defoproperty hasEncrypted
  :ontology SecurityVocab)

(defoproperty CanEncrypt
 :ontology SecurityVocab)

(defclass Decryption
  :ontology SecurityVocab
  :label "security"
  :comment "The proces to change CiphrtText to PlainText")

(defoproperty hasDecrypted 
  :ontology SecurityVocab)

(defoproperty CanDecrypt
 :ontology SecurityVocab)

(defclass CipherText
  :ontology SecurityVocab
  :label "security"
  :comment "The message after encryption")


(defclass PlainText
  :ontology SecurityVocab
  :label "security"
  :comment "The message before encryption"
  :super
  (owl-some hasDecrypted CipherText))

(refine  CipherText
  :super
  (owl-some hasEncrypted PlainText))

;;(defclass CanDecrypt/CanEncrypt :equivalent (owl-some hasKnoewledgeOf Key)



(save-ontology "o.omn" :omn)

;;(tawny.reasoner/coherent?)
