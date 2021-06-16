(ns publickey.publickey
(:use [tawny.owl])
  (:require [tawny.owl :refer :all]
            [tawny.english]
            [tawny.reasoner]
           [security.security :as sec]))


(defontology PublicKeyCryptography
   :iri "http://www.russet.org.uk/tawny/publickey/PublicKeyCryptography"
   :comment "An Ontology for Public Key Cryptography")

(defoproperty hasKnownBy)

(defclass AlicePublicKey
  :ontology PublicKeyCryptography
  :comment "is used by everyone to encrypt message to Alice")
 
(defclass privateKey)



(defindividual AlicePrivateKey
  :ontology PublicKeyCryptography
  :comment "is used to decrypt message by Alice only" 
  :fact (is hasKnownBy sec/Alice))

(defclass BobPublicKey
  :ontology PublicKeyCryptography
  :comment "is used by everyone to encrypt message to Bob")

(defclass BobPrivateKey
  :ontology PublicKeyCryptography
  :comment "is used to decrypt message by Bob only"
  :super
  (owl-only hasKnownBy Bob))

(defclass CipherText
  :equivalent (owl-some hasEncryptionKey PublicKey))

;; Useful test
;; (r/isuperclass? CipherText AliceCipherText)

(defclass AliceCipherText
  :comment "Cipher text intended for Alice to read"
  :super (owl-some hasEncryptionKey AlicePublicKey))

(defclass BobCipherText
  :comment "Cipher text intended for Bob to read"
  :super (owl-some hasEncryptionKey BobPublicKey))

(defclass AlicePlainText
:comment "Plain text for Alice to encrypt "
)


(defclass AliceCanDecrypt
  :super CanDecrypt
  :equivalent 
  (owl-some hasCipherText AliceCipherText)
  (owl-some hasKnowledgeOf AlicePrivateKey))

(defclass BobCanDecrypt
  :super CanDecrypt
  :equivalent 
  (owl-some hasCipherText BobCipherText)
  (owl-some hasKnowledgeOf BobPrivateKey))

(defclass AliceCanEncrypt
  :super CanEncrypt
  :equivalent 
  (owl-some hasPlainText AlicePlainTwxt)
  (owl-some hasKnowledgeOf BobPublicKey)

)


(defindividual Alice
  :fact (is hasKnowledgeOf AlicePublicKey)
        (is hasKnowledgeOf AlicePrivateKey)
        (is hasKnowledgeOf BobPublicKey))
 

(defindividual Bob
  :fact (is hasKnowledgeOf BobPrivateKey)
        (is hasKnowledgeOf BobPublicKey)
        (is hasKnowledgeOf AlicePublicKey))
