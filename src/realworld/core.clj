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

(defn create-server [config]
  (http/create-server
   {::http/routes routes
    ::http/type   :jetty
    ::http/join?  false
    ::http/port   (-> config :server :port)}))

(defn start-server [config]
  (http/start (create-server config)))

(defn -main
  "I don't do a whole lot ... yet."
  []
  (let [config (config/read-config)]
    (println "Starting RealWorld with config:" config)
    (start-server config)))

(comment
  (-main))
