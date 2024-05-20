(ns realworld.components.example-component
  (:require [com.stuartsierra.component :as component]))

(defrecord ExampleComponent [config]
  component/Lifecycle

  (start [component]
    (println "Starting ExampleComponent with config:" config)
    (assoc component :state ::started))

  (stop [component]
    (println "Stopping ExampleComponent")
    (assoc component :state nil)))

(defn new-example-component [config]
  (map->ExampleComponent {:config config}))
