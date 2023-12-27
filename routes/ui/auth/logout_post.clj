(cond
  (nil? (auth/user-info))
  (box/redirect "/ui/auth/login")

  (not (auth/csrf-valid?))
  (box/redirect "/ui/error" {:error "csrf-protection"})

  (auth/logout)
  (box/redirect "/ui/auth/login")

  :else
  (box/redirect "/ui/auth/login" {:error "invalid-credentials"}))
