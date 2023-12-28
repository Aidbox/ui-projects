(box/set-header! "Content-Type" "text/vnd.turbo-stream.html; charset=utf-8")

(when-let [orig-chat (box/read {:id (:id (box/route-params)) :resourceType "Chat"})]
  (let [chat (box/update (assoc orig-chat :name (:name (box/form-params))))]
    [:turbo-stream {:action "replace" :target "chat-name"}
     [:template
      [:turbo-frame {:id "chat-name"}
       [:span (or (:name chat) (str "id: " (:id chat)))]
       [:a {:href (format "/ui/chat/%s/$rename" (:id (box/route-params)))} "Rename"]]]]))
