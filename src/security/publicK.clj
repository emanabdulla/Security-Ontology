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
(defoproperty hasKnowledgeOf)

(defclass AlicePublicKey
  :ontology PublicKeyCryptography
  :comment "is used by everyone to encrypt message to Alice")
 
(defclass privateKey)
(defclass PublicKey)

(defoproperty hasEncryptionKey)

(defindividual AlicePrivateKey
  :ontology PublicKeyCryptography
  :comment "is used to decrypt message by Alice only" 
  :fact (is hasKnownBy sec/Alice))

(defclass BobPublicKey
  :ontology PublicKeyCryptography
  :comment "is used by everyone to encrypt message to Bob")

(defindividual BobPrivateKey
  :ontology PublicKeyCryptography
  :comment "is used to decrypt message by Bob only"
  :fact(is hasKnownBy sec/Bob))

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

(defoproperty hasCipherText)

(defindividual AliceCanDecrypt
  
:fact (is hasKnowledgeOf AlicePrivateKey)


;;:equivalent 
 ;; (owl-some hasCipherText AliceCipherText)
 ;; (owl-some hasKnowledgeOf AlicePrivateKey)
  )

(defindividual BobCanDecrypt

:fact (is hasKnowledgeOf BobPrivateKey)
 
 ;; :equivalent 
 ;; (owl-some hasCipherText BobCipherText)
 ;; (owl-some hasKnowledgeOf BobPrivateKey)
  )

(defoproperty hasPlainText)

(defclass AlicePlainText)
(defclass AliceCanEncrypt
  
  :equivalent 
  (owl-some hasPlainText AlicePlainText)
  (owl-some hasKnowledgeOf BobPublicKey)

)



(refine sec/Alice
  :fact (is hasKnowledgeOf AlicePublicKey)
        (is hasKnowledgeOf AlicePrivateKey)
        (is hasKnowledgeOf BobPublicKey))
 
(defindividual Bob
  :fact (is hasKnowledgeOf BobPrivateKey)
        (is hasKnowledgeOf BobPublicKey)
        (is hasKnowledgeOf AlicePublicKey))
