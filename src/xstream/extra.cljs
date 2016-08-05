(ns xstream.extra
  (:refer-clojure :exclude [concat])
  (:require [xstream.core :refer [xs]]))

(defn concat [& s]
  (apply (.-concat xs) s))

(defn debounce [n]
  (.debounce xs n))

(defn delay [n]
  (.delay xs n))

(defn drop-repeats
  ([] (drop-repeats))
  ([f] (.dropRepeats xs f)))

(defn drop-until [s]
  (.dropUntil xs s))

(defn flatten-concurrently []
  (.flattenConcurrently xs))

(defn flatten-sequentially []
  (.flattenSequentially xs))

(defn from-diagram [d o]
  (.fromDiagram xs d o))

(defn from-event
  ([el ev] (.fromEvent xs el ev))
  ([el ev o] (.fromEvent xs el ev o)))

(defn pairwise []
  (.pairwise xs))

(defn split [s]
  (.split xs s))

(defn tween [o]
  (.tween xs o))
