(box/set-header! "Content-Type" "text/vnd.turbo-stream.html; charset=utf-8")

(when (nil? (box/read {:id (:id (box/route-params)) :resourceType "Chat"}))
  (throw (ex-info "Not found" {:id (:id (box/route-params)) :resourceType "Chat"})))

(box/create {:resourceType "ChatMessage"
             :chat {:id (:id (box/route-params)) :resourceType "Chat"}
             :author {:id (:id (auth/user-info)) :resourceType "User"}
             :message (:message (box/form-params))})

(turbo/send

 (format "Chat/%s" (:id (box/route-params)))

 [:turbo-stream {:action "prepend" :target "chat"}
  [:template
   [:div [:span {:style "font-weight: bold;"} (:id (auth/user-info)) ": "]
    (:message (box/form-params))]]])

[:<>
 [:turbo-stream {:action "replace" :target "chat-form"}
  [:template
   [:turbo-frame {:id "chat-form"}
    [:form {:action (format "/ui/chat/%s/$send-message" (:id (box/route-params))) :method "post"}
     [:input {:name "message"}]
     [:button "Send"]]]]]]
