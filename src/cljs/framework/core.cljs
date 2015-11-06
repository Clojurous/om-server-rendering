(ns framework.core
  (:require
    [cljs.reader :as edn]
    [goog.dom :as gdom]

    [om.next :as om :refer-macros [defui]]
    [om.dom :as dom :include-macros true]
    [sablono.core :as html :refer-macros [html]]

    [shodan.console :as console :include-macros true]
    ))

; Print using Nashorn's `print` function if `console` is undefined.
(if (exists? js/console)
  (enable-console-print!)
  (set-print-fn! js/print))

(defui AppView
       Object
       (componentWillMount [this]
          (console/log "app-view mounting into DOM")
       )
       (render [this]

               (let [props (-> this om/props (select-keys [:uri :msg]))]

                 (console/log "props: " props)
                 (html
                  [:div
                   [:h3 "Hello server rendering!"]
                   [:div (str "url: " (:uri props))]
                   [:div (str "msg: " (:msg props))]
                  ]))
               ))

(def app-view (om/factory AppView))

(declare app-container
         app-state)

(defn render
  "To be called from init.
  Renders the app to the DOM.
  Can safely be called repeatedly to rerender the app."
  []

  ;(set! reconciler
  ;      (om/reconciler {:state app-state
  ;                      :parser parser}))

  ;(om/add-root! reconciler
  ;              AppView
  ;              (gdom/getElement app-container)
  ;              )

  (js/ReactDOM.render (app-view @app-state) (gdom/getElement app-container))
)

;(defn -main []
;  (set! app-state {})
;  (set! app-container "app")
;  (render))


(defn ^:export render-to-string
  "Takes an app state as EDN and returns the HTML for that state.
  It can be invoked from JS as `framework.core.render_to_string(edn)`."
  [state-edn]
  (->> state-edn
       edn/read-string
       app-view
       dom/render-to-str))

(defn ^:export init
  "Initializes the app.
  Should only be called once on page load.
  It can be invoked from JS as `framework.core.init(appElementId, stateElementId)`."
  [app-id state-id]
  (->> state-id
       gdom/getElement
       .-textContent
       edn/read-string
       atom
       (set! app-state))
  (->> app-id
       gdom/getElement
       (set! app-container))
  (render))
