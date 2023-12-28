# Aidbox UIs

## Development


1. Get Aidbox Liecense
2. Deploy Aidbox locally `docker compose up -d`
3. Start development `npm run dev`


``` sh
npm run dev
```

# Deploy

1. Get Aidbox 



# todo

- [ ] don't sync `.test.js` files in routes directory


```clojure
[:html
   #_[:script {:type "module"}
      "import hotwiredTurbo from 'https://cdn.skypack.dev/@hotwired/turbo';"]

   [:script {:type "module" :src "https://unpkg.com/@hotwired/turbo@7.3.0/dist/turbo.es2017-esm.js"}]

   [:script {:src "/ui/$dev-mode"}]


   [:form {:method "POST" :action "/ui/auth/logout"}
    [:input {:type "hidden" :name "_csrf" :value (auth/csrf)}]
    [:button "Sign out"]]

   [:div "chat"]

   [:turbo-frame {:id "chat"}
    [:div "message"]]

   [:turbo-frame {:id "chat-form"}
    [:form {:action "/ui/chat/$send-message" :method "post"}
     [:button "Generate new message"]]]

   ]
```
