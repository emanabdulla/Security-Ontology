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
(defoproperty encrypts)
(defoproperty decrypts)


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
          (owl-some decrypts BobCipherText)))


(refine AliceCipherText 
   :super  (some-only isEncryptedBy AlicePublicKey)
           (some-only isDecryptedBy AlicePrivateKey))

(refine BobCipherText
    :super (owl-some isEncryptedBy BobPublicKey)
           (owl-some isDecryptedBy BobPrivateKey))

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
        (owl-some sec/hasKnowledgeOf AlicePublicKey)
        (owl-some sec/hasKnowledgeOf AlicePrivateKey)
        (owl-some sec/hasKnowledgeOf BobPublicKey))

(refine sec/Bob
        (owl-some sec/hasKnowledgeOf BobPrivateKey)
        (owl-some sec/hasKnowledgeOf BobPublicKey)
        (owl-some sec/hasKnowledgeOf AlicePublicKey))

(save-ontology "PublicK.omn" :omn)
(save-ontology "PublicK.owl" :owl)
