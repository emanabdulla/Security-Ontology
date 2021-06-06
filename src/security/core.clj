(ns security.core
  [:use [tawny.owl]]
  [:require [security.security]])


(defn -main [& args]
  (save-ontology security.security/security "security.omn"))
