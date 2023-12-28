(if (nil? (auth/user-info))
      (box/redirect "/ui/auth/login")

      (let [chats (map :resource (box/sql "select resource || jsonb_build_object('id', id) as resource from chat"))]

        [:html
         [:meta {:charset "UTF-8"}]
         [:meta {:name "viewport", :content "width=device-width, initial-scale=1.0"}]
         [:title "Chat App"]
         [:script {:type "module" :src "https://unpkg.com/@hotwired/turbo@7.3.0/dist/turbo.es2017-esm.js"}]
         [:script {:src "/ui/$dev-mode"}]

         [:div "User: " (:id (auth/user-info))

          [:form {:method "POST" :action "/ui/auth/logout"
                  :style "display: inline; margin-left: 8px"}
           [:input {:type "hidden" :name "_csrf" :value (auth/csrf)}]
           [:button "Sign out"]]
          [:hr]]

         [:h1 "List of chats"]

         [:div {:style "margin-bottom: 8px"}
          (for [chat chats]
            [:div
             [:a {:href (str "/ui/chat/" (:id chat))}
              (or (:name chat) (:id chat))]
             (when (= (:id (auth/user-info)) (get-in chat [:participants 0 :id]))
               [:span {:style "color: gray; margin-left: 4px"} "author"])])]

         [:div
          "Total: " (count chats)

          [:form {:method "POST" :action "/ui/chat/$new"
                  :style "display: inline; margin-left: 8px"}
           [:input {:type "hidden" :name "_csrf" :value (auth/csrf)}]
           [:button "Create new chat"]]]


         [:div
          [:hr]

          #_
          (count (box/sql "select * from chat"))

          [:div {:style "font-family: monospace"} (format "Aidbox version %s" (box/version))]]
         ]

        #_[:div
         [:div "Chats of " (:id (auth/user-info))]
         (for [chat chats]
           [:div (str chat)])]

        #_[:html
         [:meta {:charset "UTF-8"}]
         [:meta
          {:name "viewport", :content "width=device-width, initial-scale=1.0"}]
         [:title "Chat"]
         [:script {:src "https://cdn.tailwindcss.com"}]
         [:section
          {:class "p-4"}
          [:div
           {:class "relative flex items-center space-x-4"}
           [:div
            {:class "relative"}
            [:span
             {:class "absolute text-green-500 right-0 bottom-0"}
             [:svg
              {:width "20", :height "20"}
              [:circle {:cx "8", :cy "8", :r "8", :fill "currentColor"}]]]
            [:img
             {:src
              "https://img.freepik.com/free-psd/3d-illustration-bald-person-with-glasses_23-2149436184.jpg?w=2000&t=st=1703687163~exp=1703687763~hmac=5f8ba9896ce6414e3239571fa62bd702c7491504f29000cf6544821817eca59e",
              :alt "",
              :class "w-10 sm:w-16 h-10 sm:h-16 rounded-full"}]]
           [:div
            {:class "flex flex-col leading-tight"}
            [:div
             {:class "text-2xl mt-1 flex items-center"}
             [:span {:class "text-gray-700 mr-3"} (get-in (auth/user-info) [:data :fullName])]]
            [:span {:class "text-lg text-gray-600"} (get-in (auth/user-info) [:data :roleName])]]]
          [:div
           {:class "flex flex-col w-screen overflow-y-auto"}
           [:div
            {:class "border-b-2 py-4 px-2"}
            #_[:input
             {:type "text",
              :placeholder "search chatting",
              :class "py-2 px-2 border-2 border-gray-200 rounded-md w-full"}]]

           (for [chat chats]
             [:a {:href (str "/ui/chat/" (:id chat))}
              [:div
               {:class
                "flex flex-row gap-x-4 py-4 px-2 justify-center items-center border-b-2"}
               [:img
                {:src "https://img.freepik.com/free-vector/doctor-character-background_1270-84.jpg?w=2000&t=st=1703687025~exp=1703687625~hmac=5a40b340cfd14b400ae9c4f6f5bbecdfb938433746080070beb172eec7606c43",
                 :class "object-cover h-12 w-12 rounded-full",
                 :alt ""}]
               [:div
                {:class "w-full"}
                [:div {:class "text-lg font-semibold"} "Chat with " "Dr. Smith"]
                [:span {:class "text-gray-500"} "Headache"]]]])]]]))


    #_[:html
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
