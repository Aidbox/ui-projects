[:html
 [:h1 "Aidbox UI projects"]


 [:ul
  [:li [:a {:href "/ui/auth/userifno"} "Auth UI"]]
  [:li [:a {:href "/ui/patients"} "Patient CRUD"]]
  [:li [:a {:href "/ui/chat"} "Chat App"]]
  [:li [:a {:href "/ui/forms/responses"} "Aidbox Forms"]]]


 (for [op (map :resource (box/sql "select * from operation where id like 'aidbox-%'"))]
   [:div
    (str (:request op))])

 ]

