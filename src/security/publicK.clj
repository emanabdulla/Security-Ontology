(ns security.publicK
(:use [tawny.owl])
  (:require [tawny.owl :refer :all]
            [tawny.english]
            [tawny.reasoner]
           [security.security :as sec]))


(defontology PublicKeyCryptography
   :iri "http://www.russet.org.uk/tawny/publickey/PublicKeyCryptography"
   :comment "An Ontology for Public Key Cryptography")

(defclass PublicKey
   :ontology PublicKeyCryptography)

(defoproperty hasKnownBy
   :ontology PublicKeyCryptography)

(defoproperty hasKnowledgeOf
   :ontology PublicKeyCryptography)


(defindividual AlicePublicKey
  :type PublicKey
  :ontology PublicKeyCryptography
  :comment "is used by everyone to encrypt message to Alice")
 
(defclass PrivateKey
   :ontology PublicKeyCryptography)


(defoproperty hasEncryptionKey
   :ontology PublicKeyCryptography)

(defindividual AlicePrivateKey
  :type PrivateKey
  :ontology PublicKeyCryptography
  :comment "is used to decrypt message by Alice only" 
  :fact (is hasKnownBy sec/Alice))

(defindividual BobPublicKey
  :type PublicKey
  :ontology PublicKeyCryptography
  :comment "is used by everyone to encrypt message to Bob")

(defindividual BobPrivateKey
  :type PrivateKey
  :ontology PublicKeyCryptography
  :comment "is used to decrypt message by Bob only"
  :fact(is hasKnownBy sec/Bob))

(defclass CipherText
  :equivalent (owl-some hasEncryptionKey PublicKey))

;; Useful test
;; (r/isuperclass? CipherText AliceCipherText)

(defindividual AliceCipherText
  :type CipherText
  :comment "Cipher text intended for Alice to read"
  :fact (is hasEncryptionKey AlicePublicKey))

(defindividual  BobCipherText
  :type CipherText
  :comment "Cipher text intended for Bob to read"
  :fact(is hasEncryptionKey BobPublicKey))

(defclass AlicePlainText
  :comment "Plain text for Alice to encrypt ")

(defoproperty hasCipherText
  :ontology PublicKeyCryptography)

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

(defoproperty hasPlainText
  :ontology PublicKeyCryptography)

(defclass AlicePlainText)

(defindividual AliceCanEncrypt
  :fact (is hasKnowledgeOf BobPublicKey)

 ;; :equivalent 
 ;; (owl-some hasPlainText AlicePlainText)
 ;; (owl-some hasKnowledgeOf BobPublicKey)

)



(refine sec/Alice
  :fact (is hasKnowledgeOf AlicePublicKey)
        (is hasKnowledgeOf AlicePrivateKey)
        (is hasKnowledgeOf BobPublicKey))
 
(defindividual Bob
  :fact (is hasKnowledgeOf BobPrivateKey)
        (is hasKnowledgeOf BobPublicKey)
        (is hasKnowledgeOf AlicePublicKey))
