
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


(defoproperty hasDecrypted 
  :ontology SecurityVocab)

(defoproperty hasEncrypted
  :ontology SecurityVocab)

(defoproperty CanEncrypt
 :ontology SecurityVocab)

(defoproperty CanDecrypt
 :ontology SecurityVocab)

(defclass CipherText)


(defclass PlainText
  :ontology SecurityVocab
  :label "security"
  :comment "The message before encryption"
  :super
  (owl-some hasDecrypted CipherText)
  )

(refine  CipherText
  :ontology SecurityVocab
  :label "security"
  :comment "The message after encryption"
  :super
  (owl-some hasEncrypted PlainText))

(defclass Encryption
  :ontology SecurityVocab
  :label "security"
  :comment "The proces to change plainText to CipherText")



(defclass Decryption
  :ontology SecurityVocab
  :label "security"
  :comment "The proces to change CiphrtText to PlainText"
)

(defoproperty hasKnowledgeOf
  :ontology SecurityVocab
)


(defoproperty CanDecrypt
 :ontology SecurityVocab)





;;(defclass CanDecrypt/CanEncrypt :equivalent (owl-some hasKnoewledgeOf Key)

(defindividual AlicesKey
  :type Key
)
(defindividual BobKey
   :type Key
)
(defindividual Alice
  :fact (is hasKnowledgeOf AlicesKey))
 

(defindividual Bob
  :fact (is hasKnowledgeOf BobKey))

(defindividual Eve
  :fact (owl-not hasKnowledgeOf AlicesKey)
        (owl-not hasKnowledgeOf BobKey)        
)

;;(save-ontology "o.omn" :omn) 
;;(tawny.reasoner/coherent?)
