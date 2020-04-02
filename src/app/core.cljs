(ns app.core
  "This namespace contains your application and is the entrypoint for 'yarn start'."
  (:require [reagent.dom :as rdom]
            [reagent.core :as r]
            [app.hello :refer [hello foo]]
            [reitit.frontend :as rf]
            ["@material-ui/core/styles" :refer [ThemeProvider createMuiTheme]]
            ["@material-ui/core/colors" :as mui-colors]
            [reitit.coercion.spec :as rss]
            [reitit.frontend.easy :as rfe]))

(def routes
  [["/"
    {:name ::frontpage
     :view hello}]
   ["/foo"
    {:name ::testpage
     :view foo}]])

(defonce match (r/atom nil))

(defn custom-theme
  []
  (createMuiTheme
   (clj->js
    {:palette
     {:type "light"
      :primary (.-red mui-colors)
      :secondary (.-amber mui-colors)}})))

(defn current-page []
  [:> ThemeProvider
   {:theme (custom-theme)}
   [:div 
    [:ul
     [:li [:a {:href (rfe/href ::frontpage)} "Frontpage"]]
     [:li [:a {:href (rfe/href ::testpage)} "Page 2"]]]
    (when @match
      (let [view (:view (:data @match))]
        [view @match]))]])

(defn ^:dev/after-load render
  "Render the toplevel component for this app."
  []
  (rfe/start!
   (rf/router routes {:data {:coercion rss/coercion}})
   (fn [m]
     (reset! match m))
   {:use-fragment false})
  (rdom/render [current-page] (.getElementById js/document "app")))

(defn ^:export main
  "Run application startup logic."
  []
  (render))
