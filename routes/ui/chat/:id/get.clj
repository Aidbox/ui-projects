(if (nil? (auth/user-info))
  (box/redirect "/ui/auth/login")

  [:html
   [:meta {:charset "UTF-8"}]
   [:meta {:name "viewport", :content "width=device-width, initial-scale=1.0"}]
   [:title "Chat App"]

   #_[:script {:src "https://cdn.tailwindcss.com"}]

   [:script {:type "module" :src "https://unpkg.com/@hotwired/turbo@7.3.0/dist/turbo.es2017-esm.js"}]
   #_[:script {:src "/ui/$dev-mode"}]

   [:div "User: " (:id (auth/user-info))
      [:form {:method "POST" :action "/ui/auth/logout"
              :style "display: inline; margin-left: 8px"}
       [:input {:type "hidden" :name "_csrf" :value (auth/csrf)}]
       [:button "Sign out"]]
      [:hr]]

   [:div "id: " (:id (box/route-params))]

   [:h1 "Chat"]

   [:turbo-stream-source {:src (format "/ui/chat/%s/$subscribe" (:id (box/route-params)))}]

   [:turbo-frame {:id "chat-form"}
    [:form {:action (format "/ui/chat/%s/$send-message" (:id (box/route-params))) :method "post"}
     #_[:input {:name "chat-id" :type "hidden" :value (:id (box/route-params))}]
     [:input {:name "message"}]
     [:button "Send"]]]

   (let [messages (map :resource (box/sql ["select resource || jsonb_build_object('id', id) resource from chatmessage where resource#>>'{chat,id}' = ?" (:id (box/route-params))]))]
     [:turbo-frame {:id "chat"}
      (for [msg (reverse messages)]
        [:div [:span {:style "font-weight: bold;"} (get-in msg [:author :id]) ": "]
         (:message msg)])])



   [:div
    [:hr]
    [:div {:style "font-family: monospace"} (format "Aidbox version %s" (box/version))]]
   ])
