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
  :comment "A cryptographic key used by a public-key (asymmetric) cryptographic algorithm 
            It can be obtained and used by anyone to encrypt messages intended for a particular recipient"
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
  :range sec/Person)

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
  :comment "A cryptographic key used by a public-key (asymmetric) cryptographic
            algorithm this key must be kept secret by the owner")


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

(defoproperty isEncryptedBy
  :domain CipherText
  :range PublicKey)

(defoproperty isDecryptedBy
  :domain CipherText
  :range PrivateKey)

(refine AliceCipherText 
   :super  (some-only isEncryptedBy AlicePublicKey)
           (some-only isDecryptedBy AlicePrivateKey))

(refine BobCipherText
    :super (some-only isEncryptedBy BobPublicKey)
           (some-only isDecryptedBy BobPrivateKey))


(defclass PlainText
:comment "The message before encryption(Unencrypted data)")


(as-subclasses
 PlainText
 :disjoint 
 (defclass AlicePlainText
   :comment "a message will be sent by Alice")
 (defclass BobPlainText
   :comment "a message will be sent by Bob"))

(defclass CanDecrypt
 :super sec/Role
 :equivalent (and CipherText(owl-some sec/hasKnowledgeOf PrivateKey)))

(defclass CanEncrypt
:super sec/Role
:equivalent (and PlainText (owl-some sec/hasKnowledgeOf PublicKey)))


(refine sec/Alice
     :super (owl-some sec/hasKnowledgeOf AlicePublicKey)
            (owl-some sec/hasKnowledgeOf AlicePrivateKey)
            (owl-some sec/hasKnowledgeOf BobPublicKey))

(refine sec/Bob
     :super (owl-some sec/hasKnowledgeOf BobPrivateKey)
            (owl-some sec/hasKnowledgeOf BobPublicKey)
            (owl-some sec/hasKnowledgeOf AlicePublicKey))

(defclass DigitalSignature 
 :super sec/Proces
 :comment "is a process that guarantees that the contents of a message have not been altered in transit")

(defclass HashValue
:comment "is a numeric value of a fixed length that uniquely identifies data, is calculated by a computer & used with digital signatures.")

(as-subclasses
 HashValue
:disjoint
(defclass AliceHashValue
:comment " is a numeric value calculatedt by Alice's computer")
(defclass BobHashValue
:comment " is a numeric value calculatedt by Bob's computer "))

(as-subclasses
 DigitalSignature
:disjoint
(defclass AliceSignature
:comment " is used to sign the message sent by Alice  ")
(defclass BobSignature
:comment " is used to sign the message sent by Bob ")
)

(defoproperty isSignedBy)

(defclass AliceSignedMessage
 :comment "A message signed by Alice"
 :super (owl-some isSignedBy AliceSignature))

(defclass BobSignedMessage
 :comment "A message signed by Bob"
 :super (owl-some isSignedBy BobSignature))


(save-ontology "PublicK.omn" :omn)
(save-ontology "PublicK.owl" :owl)
