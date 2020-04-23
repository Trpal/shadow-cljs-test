(ns app.hello
  (:require [reagent.core :as r]
            ["@material-ui/core/Button" :default Button]))

(defn click-counter [click-count]
  [:div
   "The atom " [:code "click-count"] " has value: "
   @click-count ". "
   [:> Button {:variant :contained
               :color :primary
               :on-click #(swap! click-count inc)}
    "click me"]])

(def click-count (r/atom 0))

(defn foo []
  [:<>
   [:h1 "foobar"]])

(defn hello []
  [:<>
   [:p "Hello, this is running!"]
   [:p "Here's an example of using a component with state:"]
   [click-counter click-count]])