[:html
 [:head

  [:title "Aidbox | Patient Responses"]
  [:meta {:http-equiv "X-UA-Compatible" :content    "IE=edge; IE=9; IE=8; IE=7"}]
  [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
  [:link {:type "text/css", :href "/static/assets/css/bootstrap.min.css" :rel "stylesheet"}]
  [:link {:type "text/css", :href "/static/assets/GothamPro/stylesheet.css" :rel "stylesheet"}]
  [:script {:src "https://cdn.tailwindcss.com"}]
  [:script {:type "module" :src "https://unpkg.com/@hotwired/turbo@7.3.0/dist/turbo.es2017-esm.js"}]
  [:link {:href "/static/assets/img/fav.svg" :rel "shortcut icon" :type "image/x-icon"}]]



 [:body.bg-gray-200.dark:bg-gray-200
  [:section
 {:class "container px-4 mx-auto"}
 [:div
  {:class "sm:flex sm:items-center sm:justify-between"}
  [:div
   [:div
    {:class "flex items-center gap-x-3"}
    [:h2 {:class "text-lg font-medium text-gray-800"} "Responses"]
    #_[:span
     {:class
      "px-3 py-1 text-xs text-blue-600 bg-blue-100 rounded-full dark:bg-gray-800 dark:text-blue-400"}
     "240\n                        patients"]]]]
   [:div {:class "mt-6 md:flex md:items-center md:justify-between"}
    (let [inactive-class "px-5 py-2 text-xs font-medium text-gray-600 transition-colors duration-200 sm:text-sm dark:bg-gray-800 dark:text-gray-300"
          active-class (str inactive-class " bg-gray-100")
          params (box/url-params)
          status-button (fn [status]
                          [:form {:action "/ui/forms/responses/search"
                                  :class "contents"}
                           [:input {:type "hidden"
                                    :name "status"
                                    :value status}]
                           [:button
                            {:class (if (= (:status params) status) active-class inactive-class)}
                            (or (some-> status clojure.string/capitalize) "View all")]])]
      [:div
       {:class
        "inline-flex overflow-hidden bg-white border divide-x rounded-lg dark:bg-gray-900 rtl:flex-row-reverse dark:border-gray-700 dark:divide-gray-700"}
       [:turbo-frame {:id "status-filters"}
        (status-button nil)
        (status-button "completed")
        (status-button "in-progress")]])

    [:turbo-frame {:id "search"}
     [:form {:action "/ui/forms/responses/search"
             :class "relative flex items-center md:mt-0 mb-0"}
      [:span
       {:class "absolute"}
       [:svg
        {:xmlns "http://www.w3.org/2000/svg",
         :fill "none",
         :viewBox "0 0 24 24",
         :stroke-width "1.5",
         :stroke "currentColor",
         :class "w-5 h-5 mx-3 text-gray-400 dark:text-gray-600"}
        [:path
         {:stroke-linecap "round",
          :stroke-linejoin "round",
          :d
          "M21 21l-5.197-5.197m0 0A7.5 7.5 0 105.196 5.196a7.5 7.5 0 0010.607 10.607z"}]]]
      [:input
       {:type "text",
        :placeholder "Search",
        :name "query"
        :class
        "block w-full py-1.5 pr-5 text-gray-700 bg-white border border-gray-200 rounded-lg md:w-80 placeholder-gray-400/70 pl-11 rtl:pr-11 rtl:pl-5 dark:bg-gray-900 dark:text-gray-300 dark:border-gray-600 focus:border-blue-400 dark:focus:border-blue-300 focus:ring-blue-300 focus:outline-none focus:ring focus:ring-opacity-40"}]]]]
 [:div
  {:class "flex flex-col mt-6"}
  [:div
   {:class "-mx-4 -my-2 overflow-x-auto sm:-mx-6 lg:-mx-8"}
   [:div
    {:class
     "inline-block min-w-full py-2 align-middle md:px-6 lg:px-8"}
    [:div
     {:class
      "overflow-hidden border border-gray-200 dark:border-gray-700 md:rounded-lg"}
     [:div {:class "grid grid-cols-5"}

      [:div
       {:scope "col",
        :class
        "bg-gray-50 dark:bg-gray-800 py-3.5 px-4 text-sm font-normal text-left rtl:text-right text-gray-500 dark:text-gray-400"}
       [:span "Title"]]
      [:div
       {:scope "col",
        :class
        "bg-gray-50 dark:bg-gray-800 py-3.5 px-4 text-sm font-normal text-left rtl:text-right text-gray-500 dark:text-gray-400"}
       [:span "Status"]]
      [:div
       {:scope "col",
        :class
        "bg-gray-50 dark:bg-gray-800 px-4 py-3.5 text-sm font-normal text-left rtl:text-right text-gray-500 dark:text-gray-400"}
       "Date"]

      [:div
       {:scope "col",
        :class
        "bg-gray-50 dark:bg-gray-800 px-4 py-3.5 text-sm font-normal text-left rtl:text-right text-gray-500 dark:text-gray-400"}
       "Patient"]
      [:div
       {:scope "col", :class "bg-gray-50 dark:bg-gray-800 relative py-3.5 px-4"}
       [:span {:class "sr-only"} "View"]]
      [:turbo-frame {:class "contents"
                     :id "forms"
                     :target "_top"
                     :src "/ui/forms/responses/search"}]]]]]]
   #_[:div
  {:class "mt-6 sm:flex sm:items-center sm:justify-between"}
  [:div
   {:class "text-sm text-gray-500 dark:text-gray-400"}
   "Page"
   [:span
    {:class "font-medium text-gray-700 dark:text-gray-600"}
    "1 of 10"]]
  [:div
   {:class "flex items-center mt-4 gap-x-4 sm:mt-0"}
   [:a
    {:href "#",
     :class
     "flex items-center justify-center w-1/2 px-5 py-2 text-sm text-gray-700 capitalize transition-colors duration-200 bg-white border rounded-md sm:w-auto gap-x-2 hover:bg-gray-100 dark:bg-gray-900 dark:text-gray-200 dark:border-gray-700 dark:hover:bg-gray-800"}
    [:svg
     {:xmlns "http://www.w3.org/2000/svg",
      :fill "none",
      :viewBox "0 0 24 24",
      :stroke-width "1.5",
      :stroke "currentColor",
      :class "w-5 h-5 rtl:-scale-x-100"}
     [:path
      {:stroke-linecap "round",
       :stroke-linejoin "round",
       :d "M6.75 15.75L3 12m0 0l3.75-3.75M3 12h18"}]]
    [:span "previous"]]
   [:a
    {:href "#",
     :class
     "flex items-center justify-center w-1/2 px-5 py-2 text-sm text-gray-700 capitalize transition-colors duration-200 bg-white border rounded-md sm:w-auto gap-x-2 hover:bg-gray-100 dark:bg-gray-900 dark:text-gray-200 dark:border-gray-700 dark:hover:bg-gray-800"}
    [:span "Next"]
    [:svg
     {:xmlns "http://www.w3.org/2000/svg",
      :fill "none",
      :viewBox "0 0 24 24",
      :stroke-width "1.5",
      :stroke "currentColor",
      :class "w-5 h-5 rtl:-scale-x-100"}
     [:path
      {:stroke-linecap "round",
       :stroke-linejoin "round",
       :d "M17.25 8.25L21 12m0 0l-3.75 3.75M21 12H3"}]]]]]]

  [:script {:src "/ui/$dev-mode"}]

  ]

 ]
