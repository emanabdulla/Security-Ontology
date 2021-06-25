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

(defoproperty isEncryptedBy
  :ontology PublicKeyCryptography)
(defoproperty isDecryptedBy
  :ontology PublicKeyCryptography)
(defoproperty encrypts
  :ontology PublicKeyCryptography)
(defoproperty decrypts
 :ontology PublicKeyCryptography)



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
   :super    (some-only  encrypts AliceCipherText))
 
 (defclass  BobPublicKey
   :super    (some-only encrypts BobCipherText)
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
             (some-only  decrypts AliceCipherText))

 (defclass BobPrivateKey
   :ontology PublicKeyCryptography
   :comment "is used to decrypt message by Bob only"
   :super (some-only hasKnownBy sec/Bob)
          (some-only decrypts BobCipherText)))


(refine AliceCipherText 
   :super  (some-only isEncryptedBy AlicePublicKey)
           (some-only isDecryptedBy AlicePrivateKey))

(refine BobCipherText
    :super (some-only isEncryptedBy BobPublicKey)
           (some-only isDecryptedBy BobPrivateKey))

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
     :super (owl-some sec/hasKnowledgeOf AlicePublicKey)
            (owl-some sec/hasKnowledgeOf AlicePrivateKey)
            (owl-some sec/hasKnowledgeOf BobPublicKey))

(refine sec/Bob
     :super (owl-some sec/hasKnowledgeOf BobPrivateKey)
            (owl-some sec/hasKnowledgeOf BobPublicKey)
            (owl-some sec/hasKnowledgeOf AlicePublicKey))

(save-ontology "PublicK.omn" :omn)
(save-ontology "PublicK.owl" :owl)
