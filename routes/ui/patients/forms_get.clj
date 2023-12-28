[:html
 [:title "Aidbox | Patients | Forms"]
 [:script {:src "https://cdn.tailwindcss.com"}]
 [:link {:href "/static/assets/img/fav.svg" :rel "shortcut icon" :type "image/x-icon"}]
 [:body
  [:script {:src "/ui/$dev-mode"}]
  (let [forms (box/sql "select * from questionnaire")
        _ (m/set :forms forms)]
    [:div
     (for [f forms]
       [:div
        [:span (get-in f [:resource :title])]])]
    )
  ]
 ]
