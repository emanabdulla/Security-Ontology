(ns security.publicK
  (:use [tawny.owl])
  (:require [tawny.owl :refer :all]
            [tawny.english]
            [tawny.reasoner :as r]
            [security.security :as sec]))


(defontology PublicKeyCryptography
   :iri "http://www.russet.org.uk/tawny/publickey/PublicKeyCryptography"
   :comment "An Ontology for Public Key Cryptography")

(r/reasoner-factory :hermit)

(defclass PublicKey
   :ontology PublicKeyCryptography)

(defoproperty isEncryptedBy)
(defoproperty isDecryptedBy)
(defoproperty isEncrypts)
(defoproperty isDecrypts)


(defclass CipherText)
 (as-subclasses
   CipherText
   :disjoint

(defclass AliceCipherText
:comment "Cipher text intended for Alice to read")
(defclass BobCipherText
 :comment "Cipher text intended for Bob to read"))

(defoproperty hasKnownBy
   :ontology PublicKeyCryptography)

(as-subclasses
  PublicKey
  :disjoint
(defclass AlicePublicKey
  
  :ontology PublicKeyCryptography
  :comment "is used by everyone to encrypt message to Alice"
  :super (owl-some isEncrypts AliceCipherText))
  
(defclass  BobPublicKey
  :super (owl-some isEncrypts BobCipherText)
  :ontology PublicKeyCryptography
  :comment "is used by everyone to encrypt message to Bob"))




(defclass PrivateKey
   :ontology PublicKeyCryptography)


(as-subclasses
  PrivateKey
  :disjoint
(defclass AlicePrivateKey
  :comment "is used to decrypt message by Alice only" 
  :super    (some-only  hasKnownBy sec/Alice)
            (owl-some isDecrypts AliceCipherText))
(defclass BobPrivateKey
  :ontology PublicKeyCryptography
  :comment "is used to decrypt message by Bob only"
  :super (some-only hasKnownBy sec/Bob)
         (owl-some isDecrypts BobCipherText)))


(refine AliceCipherText 
   :super (owl-some isEncryptedBy AlicePublicKey)
          (owl-some isDecryptedBy AlicePrivateKey))

(refine BobCipherText
  :super (owl-some isEncryptedBy BobPublicKey)
         (owl-some isDecryptedBy BobPrivateKey))




(defoproperty hasKnowledgeOf
   :ontology PublicKeyCryptography)


(defoproperty hasEncryptionKey
   :ontology PublicKeyCryptography)


;; Useful test
;; (r/isuperclass? CipherText AliceCipherText)


(defclass PlainText)

(as-subclasses
  PlainText
  :disjoint 
(defclass AlicePlainText
  :comment "a message will be sent by Alice")
(defclass BobPlainText
 :comment "a message will be sent by Bob"))


(refine sec/Alice
        (owl-some hasKnowledgeOf AlicePublicKey)
        (owl-some hasKnowledgeOf AlicePrivateKey)
        (owl-some hasKnowledgeOf BobPublicKey))
 
(refine sec/Bob
        (owl-some hasKnowledgeOf BobPrivateKey)
        (owl-some hasKnowledgeOf BobPublicKey)
        (owl-some hasKnowledgeOf AlicePublicKey))
