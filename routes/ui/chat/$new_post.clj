(box/create
 {:resourceType "Chat"
  :name "New chat"
  :participants [{:id (:id (auth/user-info))
                  :resourceType "User"}]})

(box/redirect "/ui/chat")
