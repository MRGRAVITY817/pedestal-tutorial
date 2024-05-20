(ns realworld.core
  (:require [realworld.config :as config]
            [io.pedestal.http.route :as route]
            [io.pedestal.http :as http]))

(defn respond-hello [request]
  {:status 200
   :body   "Hello, World!"})

(def routes
  (route/expand-routes
   #{["/greet" :get respond-hello :route-name :greet]}))

(defn create-server []
  (http/create-server
   {::http/routes routes
    ::http/type   :jetty
    ::http/port   8080}))

(defn start-server []
  (http/start (create-server)))

(start-server)

(defn -main
  "I don't do a whole lot ... yet."
  []
  (let [config (config/read-config)]
    (println "Starting RealWorld with config:" config)
    (start-server)))
