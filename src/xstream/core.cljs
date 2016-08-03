(ns xstream.core
  (:require [cljs.spec :as c]
   [xstream.extern]))

(enable-console-print!)

(def ^:const xs (.. js/window -xstream -default))
(def ^:const Stream (.. xs -Stream))

(defn create [producer]
  (.create xs (clj->js producer)))

(comment
  (create {:start #(fn []) :stop #(fn [])}))

(println "This text is printed from src/xstream/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
