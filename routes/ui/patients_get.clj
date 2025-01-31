[:html
 [:head
  [:title "Aidbox | Patients"]
  [:meta {:http-equiv "X-UA-Compatible" :content    "IE=edge; IE=9; IE=8; IE=7"}]
  [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
  [:link {:type "text/css", :href "/static/assets/css/bootstrap.min.css" :rel "stylesheet"}]
  [:link {:type "text/css" :href "/static/assets/fa6/css/all.min.css" :rel "stylesheet"}]
  [:link {:type "text/css", :href "/static/assets/GothamPro/stylesheet.css" :rel "stylesheet"}]
  [:script {:src "https://cdn.tailwindcss.com"}]
  [:script {:src "https://momentjs.com/downloads/moment.js"}]
  [:link {:href "/static/assets/img/fav.svg" :rel "shortcut icon" :type "image/x-icon"}]

  [:script {:type "module" :src "https://unpkg.com/@hotwired/turbo@7.3.0/dist/turbo.es2017-esm.js"}]
  ]
 (let [params (box/url-params)
       encode-params (fn [qparams]
                          (->> qparams
                               (map (fn [[k v]] (str (name k) "=" v)))
                               (clojure.string/join "&")))]
   [:body {:class "bg-gray-200"}
    [:turbo-frame {:id "modal"}]
    [:section {:class "container px-4 mx-auto"}
     [:div
      {:class "sm:flex sm:items-center sm:justify-between mt-4"}
      [:div
       [:div
        {:class "flex items-center gap-x-3"}
        [:h2 {:class "text-lg font-medium text-gray-800"} "Patients"]
        #_[:span
           {:class
            "px-3 py-1 text-xs text-blue-600 bg-blue-100 rounded-full dark:bg-gray-800 dark:text-blue-400"}
           "240\n                        patients"]]]
      [:div
       {:class "flex items-center gap-x-3"}
       [:a
        {:class
         "flex items-center justify-center w-1/2 px-5 py-2 text-sm tracking-wide text-white transition-colors duration-200 bg-blue-500 rounded-lg shrink-0 sm:w-auto gap-x-2 hover:bg-gray-700 dark:hover:bg-gray-600 dark:bg-gray-600"
         :data-turbo-frame "modal"
         :href "/ui/patients/edit"
         :data-turbo-action "GET"
         }
        [:svg
         {:xmlns "http://www.w3.org/2000/svg",
          :fill "none",
          :viewBox "0 0 24 24",
          :stroke-width "1.5",
          :stroke "currentColor",
          :class "w-5 h-5"}
         [:path
          {:stroke-linecap "round",
           :stroke-linejoin "round",
           :d "M12 9v6m3-3H9m12 0a9 9 0 11-18 0 9 9 0 0118 0z"}]]
        [:span "Add Patient"]]]]
     [:div
      {:class "mt-6 md:flex md:items-center md:justify-between"}
      (let [inactive-class "px-5 py-2 text-xs font-medium text-gray-600 transition-colors duration-200 sm:text-sm dark:bg-gray-800 dark:text-gray-300"
            active-class (str inactive-class " bg-gray-100")
            params (box/url-params)

            status-button (fn [gender]

                            [:a
                             {:class (if (= (:gender params) (not-empty gender)) active-class inactive-class)
                              :data-turbo-action "advance"
                              :href (str "/ui/patients?" (cond-> params
                                                           (not-empty gender)
                                                           (assoc :gender gender)

                                                           (empty? gender)
                                                           (dissoc :gender)

                                                           :true
                                                           encode-params))}
                             (or (some-> gender not-empty clojure.string/capitalize) "View all")])]
        [:div
         {:class
          "inline-flex overflow-hidden bg-white border divide-x rounded-lg dark:bg-gray-900 rtl:flex-row-reverse dark:border-gray-700 dark:divide-gray-700"}
         [:turbo-frame {:id "gender-filter"
                        :class "contents"
                        :target "patients"}
          (status-button "")
          (status-button "male")
          (status-button "female")]])
      [:div
       {:class "relative flex items-center md:mt-0"}
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
       [:form {:data-turbo-action "advance"
               :target "patients"
               :class "mb-0"}
        (when (:gender params)
          [:input {:type "hidden"
                   :name "gender"
                   :value (:gender params)}])
        [:input
         {:type "text",
          :placeholder "Search",
          :name "query"
          :value (:query params)
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
         [:div {:class "min-w-full grid grid-cols-4 bg-white"}
          [:div
           {:scope "col",
            :class
            "py-3.5 px-4 text-sm font-normal text-left rtl:text-right text-gray-500 dark:text-gray-400 bg-gray-50 dark:bg-gray-800"}
           [:button
            {:class "flex items-center gap-x-3 focus:outline-none"}
            [:span "Full Name"]
            [:svg
             {:class "h-3",
              :viewBox "0 0 10 11",
              :fill "none",
              :xmlns "http://www.w3.org/2000/svg"}
             [:path
              {:d
               "M2.13347 0.0999756H2.98516L5.01902 4.79058H3.86226L3.45549 3.79907H1.63772L1.24366 4.79058H0.0996094L2.13347 0.0999756ZM2.54025 1.46012L1.96822 2.92196H3.11227L2.54025 1.46012Z",
               :fill "currentColor",
               :stroke "currentColor",
               :stroke-width "0.1"}]
             [:path
              {:d
               "M0.722656 9.60832L3.09974 6.78633H0.811638V5.87109H4.35819V6.78633L2.01925 9.60832H4.43446V10.5617H0.722656V9.60832Z",
               :fill "currentColor",
               :stroke "currentColor",
               :stroke-width "0.1"}]
             [:path
              {:d
               "M8.45558 7.25664V7.40664H8.60558H9.66065C9.72481 7.40664 9.74667 7.42274 9.75141 7.42691C9.75148 7.42808 9.75146 7.42993 9.75116 7.43262C9.75001 7.44265 9.74458 7.46304 9.72525 7.49314C9.72522 7.4932 9.72518 7.49326 9.72514 7.49332L7.86959 10.3529L7.86924 10.3534C7.83227 10.4109 7.79863 10.418 7.78568 10.418C7.77272 10.418 7.73908 10.4109 7.70211 10.3534L7.70177 10.3529L5.84621 7.49332C5.84617 7.49325 5.84612 7.49318 5.84608 7.49311C5.82677 7.46302 5.82135 7.44264 5.8202 7.43262C5.81989 7.42993 5.81987 7.42808 5.81994 7.42691C5.82469 7.42274 5.84655 7.40664 5.91071 7.40664H6.96578H7.11578V7.25664V0.633865C7.11578 0.42434 7.29014 0.249976 7.49967 0.249976H8.07169C8.28121 0.249976 8.45558 0.42434 8.45558 0.633865V7.25664Z",
               :fill "currentColor",
               :stroke "currentColor",
               :stroke-width "0.3"}]]]]
          [:div
           {:scope "col",
            :class
            "px-12 py-3.5 text-sm font-normal text-left rtl:text-right text-gray-500 dark:text-gray-400 bg-gray-50 dark:bg-gray-800"}
           "Date birth"]
          [:div
           {:scope "col",
            :class
            "px-4 py-3.5 text-sm font-normal text-left rtl:text-right text-gray-500 dark:text-gray-400 bg-gray-50 dark:bg-gray-800"}
           "Gender"]
          [:div
           {:scope "col", :class "relative py-3.5 px-4 bg-gray-50 dark:bg-gray-800"}
           [:span {:class "sr-only"} "Edit"]]
          [:turbo-frame {:id "patients"
                         :class "contents"
                         :src (str "/ui/patients/search?" (->> (box/url-params)
                                                               encode-params))}]]]]]]
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
    [:script {:src "/ui/$dev-mode"}]])

 ]
