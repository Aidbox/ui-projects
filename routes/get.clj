[:html
 [:h1 "UI operations"]

 (for [op (map :resource (box/sql "select * from operation where id like 'aidbox-%'"))]
   [:div
    (str (:request op))])

 ]
