(ns realworld.core
  (:require [realworld.config :as config]
            [io.pedestal.http.route :as route]
            [io.pedestal.http :as http]
            [com.stuartsierra.component :as component]
            [realworld.components.example-component :as example-component]))

(defn respond-hello [request]
  {:status 200
   :body   "Hello, World!"})

(def routes
  (route/expand-routes
   #{["/greet" :get respond-hello :route-name :greet]}))

(defn create-server [config]
  (http/create-server
   {::http/routes routes
    ::http/type   :jetty
    ::http/join?  false
    ::http/port   (-> config :server :port)}))

(defn realworld-system [config]
  (component/system-map
   :example-component (example-component/new-example-component config)))

(defn start-server [config]
  (http/start (create-server config)))

(defn -main []
  (let [system (-> (config/read-config)
                   (realworld-system)
                   (component/start-system))]
    (println "Starting RealWorld with config:")
    ;; Clean up the system when the JVM shuts down
    (.addShutdownHook (Runtime/getRuntime)
                      (new Thread. #(component/stop-system system)))))

(comment
  (-main))
