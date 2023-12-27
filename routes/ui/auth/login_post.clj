;; NOTE: should this logic be moved back to Aidbox with bunch of configurable redirects?

(cond
  (some? (auth/user-info))
  (box/redirect "/ui/auth/userinfo")

  (not (auth/csrf-valid?))
  (box/redirect "/ui/auth/login" {:error "csrf-protection"})

  (auth/authenticate {:username (:username (box/form-params))
                      :password (:password (box/form-params))})
  (box/redirect "/ui/auth/userinfo")

  :else
  (box/redirect "/ui/auth/login" {:error "invalid-credentials"}))
