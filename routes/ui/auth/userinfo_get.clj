;; :redirect-on-unauthenticated "/ui/auth/login"

(cond
  (nil? (auth/user-info))
  (box/redirect "/ui/auth/login")

  :else
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

   (let [user (auth/user-info)]
     [:body.flex.mt-8 {:style "
background-color: #0093E9;
background-image: linear-gradient(160deg, #0093E9 0%, #80D0C7 100%);
"}

      [:div.m-auto.flex.flex-column

       [:div.py-8.shadow.rounded
        {:style "backdrop-filter: blur(8px); background: rgba(255, 255, 255, 0.6)"}
        [:div
         [:img.px-16.mb-4.m-auto {:src "/static/assets/img/aidbox-logo.svg" :style "max-width: 100%"}]

         [:div.flex.justify-between.w-full.mb-4
          (for [link [{:label "userinfo" :url "/ui/auth/userinfo"}
                      {:label "sessions" :url "/ui/auth/sessions"}
                      {:label "apps" :url "/ui/auth/apps"}]]
            [:a.border-b.flex-1.text-center.hover:no-underline.py-2.text-black
             {:href (:url link)
              :class (if (= (:uri (box/request)) (:url link))
                       (str/join "," ["hover:text-black"])
                       (str/join "," ["hover:text-gray-500"]))
              :style (if (= (:uri (box/request)) (:url link))
                       "border-color: black"
                       "border-color: #80D0C7")}
             (:label link)])]

         [:div.px-8

          [:div.flex.items-center.gap-2.mb-4.justify-center
           [:img.rounded-full {:src (format "https://gravatar.com/avatar/%s?d=identicon" (util/md5 (or (:email user) (:id user))))}]
           [:h1.text-xl.font-bold (:id user)]]

          (when (:email user)
            [:div "email: " (:email user)])

          [:br]

          [:form.m-auto {:method "POST" :action "/ui/auth/logout"}
           [:input {:type "hidden" :name "_csrf" :value (auth/csrf)}]
           [:button.py-2.px-4.text-white.rounded.w-full
            {:style "background-color: #0093E9"}
            "Sign out"]]]]]

       [:div.text-gray-200.mx-auto.pt-2
        (format "Aidbox version %s" (box/version))]]
      ])])
