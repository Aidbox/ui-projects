(if (some? (auth/user-info))
  (box/redirect "/ui/chats")

  [:html
   [:title "Aidbox | Userinfo"]
   [:meta {:http-equiv "X-UA-Compatible" :content    "IE=edge; IE=9; IE=8; IE=7"}]
   [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
   [:link {:type "text/css", :href "/static/assets/css/bootstrap.min.css" :rel "stylesheet"}]
   [:link {:type "text/css", :href "/static/assets/GothamPro/stylesheet.css" :rel "stylesheet"}]
   [:script {:src "https://cdn.tailwindcss.com"}]

   [:style
    (garden.core/css
     [:html
      [:body {:font-family
              "'Gotham Pro',-apple-system,BlinkMacSystemFont,Segoe UI,PingFang SC,Hiragino Sans GB,Microsoft YaHei,Helvetica Neue,Helvetica,Arial,sans-serif,Apple Color Emoji,Segoe UI Emoji,Segoe UI Symbol;"}]])]

   [:link {:href "/static/assets/img/fav.svg" :rel "shortcut icon" :type "image/x-icon"}]

   [:script {:src "/ui/$dev-mode"}]

   [:body.flex.mt-8 {:style "
background-color: #0093E9;
background-image: linear-gradient(160deg, #0093E9 0%, #80D0C7 100%);
"}

    [:div.m-auto.flex.flex-column

     [:div.py-8.shadow.rounded
      {:style "backdrop-filter: blur(8px); background: rgba(255, 255, 255, 0.6)"}
      [:div
       [:img.px-16 {:src "/static/assets/img/aidbox-logo.svg" :style "max-width: 100%"}]

       ;; [:div.text-center.text-2xl "Login"]

       [:br]

       [:form.px-8
        {:method "POST" :action "/ui/chat/login"}
        (when (:error (box/url-params))
          [:div.auth-error [:div.alert.alert-danger (:error (box/url-params))]])
        [:input {:type "hidden" :name "_csrf" :value (auth/csrf)}]
        [:div.form-group
         [:label {:for "username"} "Username or Email"]
         [:input.form-control
          {:name "username" :autofocus "on" :required "true"}]]

        [:div
         [:label "Password"]
         [:input.form-control {:type "password" :name "password"
                               :autocomplete "current-password"
                               :required "true"}]]
        [:br]
        [:button.py-2.px-4.text-white.rounded.w-full
          {:style "background-color: #0093E9"}
          "Sign in"]
        ]

       [:div.px-8

        [:form.m-auto {:method "POST" :action "/ui/auth/logout"}
         #_[:input {:type "hidden" :name "_csrf" :value (box/csrf)}]
         ]]]]

     [:div.text-gray-200.mx-auto.pt-2 (format "Aidbox version %s" (box/version))]]]])
