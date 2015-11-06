(defproject om-next-rendering "0.1.0-SNAPSHOT"
  :description "A clojure(script) framework"
  :url "http://example.com/FIXME"

  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo
            :comments "Same as Clojure"}

  :dependencies [;; clojure
                 [org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.122"]
                 ;[org.clojure/core.async "0.1.267.0-0d7780-alpha"]

                 ;; server
                 [http-kit "2.1.18"]
                 [compojure "1.1.8"]
                 [ring "1.3.0"]
                 [ring/ring-devel "1.1.8"]
                 [hiccup "1.0.5"]
                 [cider/cider-nrepl "0.7.0-SNAPSHOT"]

                 ;; om
                 [cljsjs/react "0.14.0-1"]
                 [cljsjs/react-dom "0.14.0-1"]
                 [org.omcljs/om "1.0.0-alpha12" :exclusions [cljsjs/react cljsjs/react-dom]]
                 [sablono "0.3.6"]
                 [shodan "0.4.2"]
                 ]

  :plugins [[lein-cljsbuild "1.0.3"]]
  :hooks [leiningen.cljsbuild]

  :cljsbuild {:builds [{:source-paths ["src/cljs"]
                  :compiler {
                             ;:preamble ["react/react.min.js"]
                             :output-to "resources/public/framework.js"
                             :output-dir "resources/public/out"
                             :source-map "resources/public/framework.js.map"
                             :optimizations :simple}}]}

  :source-paths ["src/clj"]
  :resource-paths ["resources"]
  :main framework.core)
