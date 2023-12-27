(if (nil? (auth/user-info))
  (box/redirect "/ui/chat/login")

  [:html
   [:meta {:charset "UTF-8"}]
   [:meta
    {:name "viewport", :content "width=device-width, initial-scale=1.0"}]
   [:title "Chat"]
   [:script {:src "https://cdn.tailwindcss.com"}]

   [:script {:type "module" :src "https://unpkg.com/@hotwired/turbo@7.3.0/dist/turbo.es2017-esm.js"}]

   [:div
    {:class "flex-1 p:2 sm:p-6 justify-between flex flex-col h-screen"}
    [:div
     {:class
      "flex sm:items-center justify-between py-3 border-b-2 border-gray-200"}
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
         "https://img.freepik.com/free-vector/doctor-character-background_1270-84.jpg?w=2000&t=st=1703687025~exp=1703687625~hmac=5a40b340cfd14b400ae9c4f6f5bbecdfb938433746080070beb172eec7606c43",
         :alt "",
         :class "w-10 sm:w-16 h-10 sm:h-16 rounded-full"}]]
      [:div
       {:class "flex flex-col leading-tight"}
       [:div
        {:class "text-2xl mt-1 flex items-center"}
        [:span {:class "text-gray-700 mr-3"} "Dr. Smith"]]
       [:span {:class "text-lg text-gray-600"} "Therapist"]]]
     [:div
      {:class "flex items-center space-x-2"}
      [:button
       {:type "button",
        :class
        "inline-flex items-center justify-center rounded-lg border h-10 w-10 transition duration-500 ease-in-out text-gray-500 hover:bg-gray-300 focus:outline-none"}
       [:svg
        {:xmlns "http://www.w3.org/2000/svg",
         :fill "none",
         :viewBox "0 0 24 24",
         :stroke "currentColor",
         :class "h-6 w-6"}
        [:path
         {:stroke-linecap "round",
          :stroke-linejoin "round",
          :stroke-width "2",
          :d "M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"}]]]
      [:button
       {:type "button",
        :class
        "inline-flex items-center justify-center rounded-lg border h-10 w-10 transition duration-500 ease-in-out text-gray-500 hover:bg-gray-300 focus:outline-none"}
       [:svg
        {:xmlns "http://www.w3.org/2000/svg",
         :fill "none",
         :viewBox "0 0 24 24",
         :stroke "currentColor",
         :class "h-6 w-6"}
        [:path
         {:stroke-linecap "round",
          :stroke-linejoin "round",
          :stroke-width "2",
          :d
          "M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"}]]]
      [:button
       {:type "button",
        :class
        "inline-flex items-center justify-center rounded-lg border h-10 w-10 transition duration-500 ease-in-out text-gray-500 hover:bg-gray-300 focus:outline-none"}
       [:svg
        {:xmlns "http://www.w3.org/2000/svg",
         :fill "none",
         :viewBox "0 0 24 24",
         :stroke "currentColor",
         :class "h-6 w-6"}
        [:path
         {:stroke-linecap "round",
          :stroke-linejoin "round",
          :stroke-width "2",
          :d
          "M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9"}]]]]]
    [:div
     {:id "messages",
      :class
      "flex flex-col space-y-4 p-3 overflow-y-auto scrollbar-thumb-blue scrollbar-thumb-rounded scrollbar-track-blue-lighter scrollbar-w-2 scrolling-touch"}
     [:div
      {:class "chat-message"}
      [:div
       {:class "flex items-end"}
       [:div
        {:class
         "flex flex-col space-y-2 text-xs max-w-xs mx-2 order-2 items-start"}
        [:div
         [:span
          {:class
           "px-4 py-2 rounded-lg inline-block rounded-bl-none bg-gray-300 text-gray-600"}
          "Can\n                                be verified on any platform using docker"]]]
       [:img
        {:src
         "https://img.freepik.com/free-vector/doctor-character-background_1270-84.jpg?w=2000&t=st=1703687025~exp=1703687625~hmac=5a40b340cfd14b400ae9c4f6f5bbecdfb938433746080070beb172eec7606c43",
         :alt "My profile",
         :class "w-6 h-6 rounded-full order-1"}]]]
     [:div
      {:class "chat-message"}
      [:div
       {:class "flex items-end justify-end"}
       [:div
        {:class
         "flex flex-col space-y-2 text-xs max-w-xs mx-2 order-1 items-end"}
        [:div
         [:span
          {:class
           "px-4 py-2 rounded-lg inline-block rounded-br-none bg-gray-700 text-white"}
          "Your\n                                error message says permission denied, npm global installs must be given root\n                                privileges."]]]
       [:img
        {:src
         "https://img.freepik.com/free-psd/3d-illustration-bald-person-with-glasses_23-2149436184.jpg?w=2000&t=st=1703687163~exp=1703687763~hmac=5f8ba9896ce6414e3239571fa62bd702c7491504f29000cf6544821817eca59e",
         :alt "My profile",
         :class "w-6 h-6 rounded-full order-2"}]]]
     [:div
      {:class "chat-message"}
      [:div
       {:class "flex items-end"}
       [:div
        {:class
         "flex flex-col space-y-2 text-xs max-w-xs mx-2 order-2 items-start"}
        [:div
         [:span
          {:class
           "px-4 py-2 rounded-lg inline-block bg-gray-300 text-gray-600"}
          "Command was run\n                                with root privileges. I&#39;m sure about that."]]
        [:div
         [:span
          {:class
           "px-4 py-2 rounded-lg inline-block bg-gray-300 text-gray-600"}
          "I&#39;ve update the\n                                description so it&#39;s more obviously now"]]
        [:div
         [:span
          {:class
           "px-4 py-2 rounded-lg inline-block bg-gray-300 text-gray-600"}
          "FYI\n                                https://askubuntu.com/a/700266/510172"]]
        [:div
         [:span
          {:class
           "px-4 py-2 rounded-lg inline-block rounded-bl-none bg-gray-300 text-gray-600"}
          "Check the line above (it ends with a # so, I&#39;m running it as root )"
          [:pre "# npm install -g @vue/devtools"]]]]
       [:img
        {:src
         "https://img.freepik.com/free-vector/doctor-character-background_1270-84.jpg?w=2000&t=st=1703687025~exp=1703687625~hmac=5a40b340cfd14b400ae9c4f6f5bbecdfb938433746080070beb172eec7606c43",
         :alt "My profile",
         :class "w-6 h-6 rounded-full order-1"}]]]
     [:div
      {:class "chat-message"}
      [:div
       {:class "flex items-end justify-end"}
       [:div
        {:class
         "flex flex-col space-y-2 text-xs max-w-xs mx-2 order-1 items-end"}
        [:div
         [:span
          {:class
           "px-4 py-2 rounded-lg inline-block rounded-br-none bg-gray-700 text-white"}
          "Any\n                                updates on this issue? I&#39;m getting the same error when trying to install devtools.\n                                Thanks"]]]
       [:img
        {:src
         "https://img.freepik.com/free-psd/3d-illustration-bald-person-with-glasses_23-2149436184.jpg?w=2000&t=st=1703687163~exp=1703687763~hmac=5f8ba9896ce6414e3239571fa62bd702c7491504f29000cf6544821817eca59e",
         :alt "My profile",
         :class "w-6 h-6 rounded-full order-2"}]]]
     [:div
      {:class "chat-message"}
      [:div
       {:class "flex items-end"}
       [:div
        {:class
         "flex flex-col space-y-2 text-xs max-w-xs mx-2 order-2 items-start"}
        [:div
         [:span
          {:class
           "px-4 py-2 rounded-lg inline-block rounded-bl-none bg-gray-300 text-gray-600"}
          "Thanks\n                                for your message David. I thought I&#39;m alone with this issue. Please, ? the issue to\n                                support it :)"]]]
       [:img
        {:src
         "https://img.freepik.com/free-vector/doctor-character-background_1270-84.jpg?w=2000&t=st=1703687025~exp=1703687625~hmac=5a40b340cfd14b400ae9c4f6f5bbecdfb938433746080070beb172eec7606c43",
         :alt "My profile",
         :class "w-6 h-6 rounded-full order-1"}]]]
     [:div
      {:class "chat-message"}
      [:div
       {:class "flex items-end justify-end"}
       [:div
        {:class
         "flex flex-col space-y-2 text-xs max-w-xs mx-2 order-1 items-end"}
        [:div
         [:span
          {:class
           "px-4 py-2 rounded-lg inline-blockbg-gray-700 text-white"}
          "Are you using\n                                sudo?"]]
        [:div
         [:span
          {:class
           "px-4 py-2 rounded-lg inline-block rounded-br-none bg-gray-700 text-white"}
          "Run\n                                this command sudo chown -R `whoami` /Users/{{your_user_profile}}/.npm-global/ then\n                                install the package globally without using sudo"]]]
       [:img
        {:src
         "https://img.freepik.com/free-psd/3d-illustration-bald-person-with-glasses_23-2149436184.jpg?w=2000&t=st=1703687163~exp=1703687763~hmac=5f8ba9896ce6414e3239571fa62bd702c7491504f29000cf6544821817eca59e",
         :alt "My profile",
         :class "w-6 h-6 rounded-full order-2"}]]]
     [:div
      {:class "chat-message"}
      [:div
       {:class "flex items-end"}
       [:div
        {:class
         "flex flex-col space-y-2 text-xs max-w-xs mx-2 order-2 items-start"}
        [:div
         [:span
          {:class
           "px-4 py-2 rounded-lg inline-block bg-gray-300 text-gray-600"}
          "It seems like you\n                                are from Mac OS world. There is no /Users/ folder on linux ?"]]
        [:div
         [:span
          {:class
           "px-4 py-2 rounded-lg inline-block rounded-bl-none bg-gray-300 text-gray-600"}
          "I\n                                have no issue with any other packages installed with root permission globally."]]]
       [:img
        {:src
         "https://img.freepik.com/free-vector/doctor-character-background_1270-84.jpg?w=2000&t=st=1703687025~exp=1703687625~hmac=5a40b340cfd14b400ae9c4f6f5bbecdfb938433746080070beb172eec7606c43",
         :alt "My profile",
         :class "w-6 h-6 rounded-full order-1"}]]]
     [:div
      {:class "chat-message"}
      [:div
       {:class "flex items-end justify-end"}
       [:div
        {:class
         "flex flex-col space-y-2 text-xs max-w-xs mx-2 order-1 items-end"}
        [:div
         [:span
          {:class
           "px-4 py-2 rounded-lg inline-block rounded-br-none bg-gray-700 text-white"}
          "yes, I\n                                have a mac. I never had issues with root permission as well, but this helped me to solve\n                                the problem"]]]
       [:img
        {:src
         "https://img.freepik.com/free-psd/3d-illustration-bald-person-with-glasses_23-2149436184.jpg?w=2000&t=st=1703687163~exp=1703687763~hmac=5f8ba9896ce6414e3239571fa62bd702c7491504f29000cf6544821817eca59e",
         :alt "My profile",
         :class "w-6 h-6 rounded-full order-2"}]]]
     [:div
      {:class "chat-message"}
      [:div
       {:class "flex items-end"}
       [:div
        {:class
         "flex flex-col space-y-2 text-xs max-w-xs mx-2 order-2 items-start"}
        [:div
         [:span
          {:class
           "px-4 py-2 rounded-lg inline-block bg-gray-300 text-gray-600"}
          "I get the same\n                                error on Arch Linux (also with sudo)"]]
        [:div
         [:span
          {:class
           "px-4 py-2 rounded-lg inline-block bg-gray-300 text-gray-600"}
          "I also have this\n                                issue, Here is what I was doing until now: #1076"]]
        [:div
         [:span
          {:class
           "px-4 py-2 rounded-lg inline-block rounded-bl-none bg-gray-300 text-gray-600"}
          "even\n                                i am facing"]]]
       [:img
        {:src
         "https://img.freepik.com/free-vector/doctor-character-background_1270-84.jpg?w=2000&t=st=1703687025~exp=1703687625~hmac=5a40b340cfd14b400ae9c4f6f5bbecdfb938433746080070beb172eec7606c43",
         :alt "My profile",
         :class "w-6 h-6 rounded-full order-1"}]]]]
    [:div
     {:class "border-t-2 border-gray-200 px-4 pt-4 mb-2 sm:mb-0"}
     [:div
      {:class "relative flex"}
      [:input
       {:type "text",
        :placeholder "Write your message!",
        :class
        "w-full focus:outline-none focus:placeholder-gray-400 text-gray-600 placeholder-gray-600 pl-6 bg-gray-200 rounded-md py-3"}]
      [:div
       {:class "absolute right-0 items-center inset-y-0 hidden sm:flex"}
       [:button
        {:type "button",
         :class
         "inline-flex items-center justify-center rounded-full h-10 w-10 transition duration-500 ease-in-out text-gray-500 hover:bg-gray-300 focus:outline-none"}
        [:svg
         {:xmlns "http://www.w3.org/2000/svg",
          :fill "none",
          :viewBox "0 0 24 24",
          :stroke "currentColor",
          :class "h-6 w-6 text-gray-600"}
         [:path
          {:stroke-linecap "round",
           :stroke-linejoin "round",
           :stroke-width "2",
           :d
           "M15.172 7l-6.586 6.586a2 2 0 102.828 2.828l6.414-6.586a4 4 0 00-5.656-5.656l-6.415 6.585a6 6 0 108.486 8.486L20.5 13"}]]]
       [:button
        {:type "button",
         :class
         "inline-flex items-center justify-center rounded-full h-10 w-10 transition duration-500 ease-in-out text-gray-500 hover:bg-gray-300 focus:outline-none"}
        [:svg
         {:xmlns "http://www.w3.org/2000/svg",
          :fill "none",
          :viewBox "0 0 24 24",
          :stroke "currentColor",
          :class "h-6 w-6 text-gray-600"}
         [:path
          {:stroke-linecap "round",
           :stroke-linejoin "round",
           :stroke-width "2",
           :d
           "M3 9a2 2 0 012-2h.93a2 2 0 001.664-.89l.812-1.22A2 2 0 0110.07 4h3.86a2 2 0 011.664.89l.812 1.22A2 2 0 0018.07 7H19a2 2 0 012 2v9a2 2 0 01-2 2H5a2 2 0 01-2-2V9z"}]
         [:path
          {:stroke-linecap "round",
           :stroke-linejoin "round",
           :stroke-width "2",
           :d "M15 13a3 3 0 11-6 0 3 3 0 016 0z"}]]]
       [:button
        {:type "button",
         :class
         "inline-flex items-center justify-center rounded-full h-10 w-10 transition duration-500 ease-in-out text-gray-500 hover:bg-gray-300 focus:outline-none"}
        [:svg
         {:xmlns "http://www.w3.org/2000/svg",
          :fill "none",
          :viewBox "0 0 24 24",
          :stroke "currentColor",
          :class "h-6 w-6 text-gray-600"}
         [:path
          {:stroke-linecap "round",
           :stroke-linejoin "round",
           :stroke-width "2",
           :d
           "M14.828 14.828a4 4 0 01-5.656 0M9 10h.01M15 10h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"}]]]
       [:button
        {:type "button",
         :class
         "inline-flex items-center justify-center rounded-lg px-4 py-3 transition duration-500 ease-in-out text-white  bg-gray-700 hover:bg-gray-600 focus:outline-none"}
        [:span {:class "font-bold"} "Send"]
        [:svg
         {:xmlns "http://www.w3.org/2000/svg",
          :viewBox "0 0 20 20",
          :fill "currentColor",
          :class "h-6 w-6 ml-2 transform rotate-90"}
         [:path
          {:d
           "M10.894 2.553a1 1 0 00-1.788 0l-7 14a1 1 0 001.169 1.409l5-1.429A1 1 0 009 15.571V11a1 1 0 112 0v4.571a1 1 0 00.725.962l5 1.428a1 1 0 001.17-1.408l-7-14z"}]]]]]]]
   [:style
    ".scrollbar-w-2::-webkit-scrollbar {\n            width: 0.25rem;\n            height: 0.25rem;\n        }\n\n        .scrollbar-track-blue-lighter::-webkit-scrollbar-track {\n            --bg-opacity: 1;\n            background-color: #f7fafc;\n            background-color: rgba(247, 250, 252, var(--bg-opacity));\n        }\n\n        .scrollbar-thumb-blue::-webkit-scrollbar-thumb {\n            --bg-opacity: 1;\n            background-color: #edf2f7;\n            background-color: rgba(237, 242, 247, var(--bg-opacity));\n        }\n\n        .scrollbar-thumb-rounded::-webkit-scrollbar-thumb {\n            border-radius: 0.25rem;\n        }"]
   [:script
    "const el = document.getElementById('messages')\n        el.scrollTop = el.scrollHeight"]])

#_[:html
   #_[:script {:type "module"}
      "import hotwiredTurbo from 'https://cdn.skypack.dev/@hotwired/turbo';"]

   [:script {:type "module" :src "https://unpkg.com/@hotwired/turbo@7.3.0/dist/turbo.es2017-esm.js"}]

   [:script {:src "/ui/$dev-mode"}]


   [:form {:method "POST" :action "/ui/chat/logout"}
    [:input {:type "hidden" :name "_csrf" :value (auth/csrf)}]
    [:button "Sign out"]]

   [:div "chat"]

   [:turbo-frame {:id "chat"}
    [:div "message"]]

   [:turbo-frame {:id "chat-form"}
    [:form {:action "/ui/chat/$send-message" :method "post"}
     [:button "Generate new message"]]]

   ]
