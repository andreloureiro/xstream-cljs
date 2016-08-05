(ns xstream.core
  (:refer-clojure :exclude [drop empty filter flatten last map merge take])
  (:require [cljs.spec :as s]
   [xstream.extern]))

(enable-console-print!)

(def ^:const xs (.. js/window -xstream -default))
(def ^:const Stream (.. xs -Stream))

(defn stream? [s]
  (= (.. s -constructor -name) "Stream"))

(s/def ::next fn?)
(s/def ::error fn?)
(s/def ::complete fn?)
(s/def ::listener (s/keys :req-un [::next ::error ::complete]))

(defn make-listener [listener]
  (if (s/valid? ::listener listener)
    (clj->js listener)
    (do
      (println "make-listener: listener doesn't conform")
      (s/explain ::listener listener))))

(defn create [producer]
  (.create xs (clj->js producer)))

(defn create-with-memory [producer]
  (.createWithMemory xs (clj->js producer)))

(defn never []
  (.never xs))

(defn empty []
  (.empty xs))

(defn throw [error]
  (.throw xs error))

(defn of [& values]
  (apply (.-of xs) values))

(defn from-coll [coll]
  (.fromArray xs coll))

(defn from-promise [promise]
  (.fromPromise xs promise))

(defn periodic [ms]
  (.periodic xs ms))

(defn merge [& streams]
  (apply (.-merge xs) streams))

(defn combine [& streams]
  (apply (.-combine xs) streams))

(defn add-listener [l s]
  (do (.addListener s l)))

(defn remove-listener [l s]
  (do (.removeListener s l)))

(defn map [f s]
  (.map s f))

(defn map-to [v s]
  (.mapTo s v))

(defn filter [f s]
  (.filter s f))

(defn take [n s]
  (.take s n))

(defn drop [n s]
  (.drop s n))

(defn last [s]
  (.last s))

(defn start-with [v s]
  (.startWith s v))

(defn end-when [os s]
  (.endWhen s os))

(defn fold [f in s]
  (.fold s f in))

(defn replace-error [f s]
  (.replaceError s f))

(defn flatten [s]
  (.flatten s))

(defn compose [o s]
  (.compose s o))

(defn remember [s]
  (.remember s))

(defn debug [f s]
  (.debug s f))

(defn imitate [t s]
  (.imitate s t))

(defn shamefully-send-next [s v]
  (.shamefullySendNext s v))

(defn shamefully-send-error [s v]
  (.shamefullySendError s v))

(defn shamefully-send-complete [s v]
  (.shamefullySendComplete s v))

(comment
  (let [s1 (->> (periodic 1000)
                (map inc))
        s2 (->> (periodic 3000)
                (map-to "aaa"))
        m (merge s1 s2)
        l (make-listener {:next #(println %)
                          :error #(println %)
                          :complete #(println "done")})]
    (add-listener l m)
    (.setTimeout js/window #(do
                              (println "chega")
                              (remove-listener l m)) 5000)))
