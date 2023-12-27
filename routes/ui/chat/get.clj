[:html
 #_[:script {:type "module"}
  "import hotwiredTurbo from 'https://cdn.skypack.dev/@hotwired/turbo';"]

 [:script {:type "module" :src "https://unpkg.com/@hotwired/turbo@7.3.0/dist/turbo.es2017-esm.js"}]

 [:script {:src "/ui/$dev-mode"}]


 [:div "chat"]

 [:turbo-frame {:id "chat"}
  [:div "message"]

  ]

 [:turbo-frame {:id "chat-form"}
  [:form {:action "/ui/chat/$send-message" :method "post"}
   [:button "Generate new message"]]]



 ]
