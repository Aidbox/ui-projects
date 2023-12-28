(box/set-header! "Content-Type" "text/vnd.turbo-stream.html; charset=utf-8")

(when-let [chat (box/read {:id (:id (box/route-params)) :resourceType "Chat"})]
  #_(box/update (assoc chat :name (:name (box/form-params))))

  [:turbo-stream {:action "replace" :target "chat-name"}
   [:template
    [:turbo-frame {:id "chat-name"}
     [:form {:action (format "/ui/chat/%s/$rename" (:id (box/route-params))) :method "POST"}
      [:input {:name "name" :value (:name chat)}]
      [:button "Save"]]]]])
