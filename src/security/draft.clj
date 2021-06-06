(ns ...
(:use [tawny.owl])
  (:require [tawny.owl :refer :all]
            [tawny.english]
            [tawny.reasoner]))

 
(defontology securityVocab
  :iri "http://www.russet.org.uk/tawny/security/securityVocab"
  :noname true
  :comment "The vovabulary for Symmetric encryption"
  )

(defontology Alice
  :iri "http://www.russet.org.uk/tawny/security/Alice"
  :noname true
  :comment ""
  )

(defontology Bob
  :iri "http://www.russet.org.uk/tawny/security/Bob"
  :noname true
  :comment ""
  )  

(defontology Eve
  :iri "http://www.russet.org.uk/tawny/security/Eve"
  :noname true
  :comment ""
  )


(defclass PlainText
  :ontology securityVocab)

(defclass CipherText
  :ontology securityVocab
  :super
  (some hasUnencryptedForm PlainText)
  (some hasEncryptionKey Key)
)

- todo

(defclass AliceCipherText
  :comment "Cipher text intended for Alice to read"
  :super (some hasEncryptionKey AlicePublicKey))

(defclass AliceCanDecrypt
  :super CanDecrypt
  :equivalent 
  (some hasCipherText AliceCipherText)
  (some hasKnowledgeOf AlicePrivateKey)
)



(defindividual Alice
  :fact (is hasKnowledgeOf AliceKey)
)




(defclass CanDecrypt
  :equivalent
  (some hasCipherText CipherText)
  (some hasKnowledgeOf Key))



(defclass AlicePublicKey

  :ontology securityVocab)

(defclass AlicePrivateKey
  :ontology securityVocab)

(defclass AlicesKey)




(defclass BobPublicKey
  :ontology securityVocab)

(defclass BobPrivateKey
  :ontology securityVocab)

(defclass Key
  :ontology securityVocab)

(defclass message
  :ontology securityVocab)




(mrefine
  [Alice Bob]
  plainText :super message)


(mrefine
  [Alice Bob]
  cipherText :super message)


(refine Alice
      AlicePublicKey
      :super Key
      :comment "")

(refine Alice
      AlicePrivateKey
      :super Key
      :comment "")

(refine Bob
      BobPublicKey
      :super Key
      :comment "")

(refine Bob
      BobPrivateKey
      :super Key
      :comment "")

(refine Eve 
      cipherText :super message)

