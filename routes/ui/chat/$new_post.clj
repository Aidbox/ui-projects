(box/create
 {:resourceType "Chat"
  :participants [{:id (:id (auth/user-info))
                  :resourceType "User"}]})

(box/redirect "/ui/chat")
