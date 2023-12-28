(box/set-header! "Content-Type" "text/vnd.turbo-stream.html; charset=utf-8")

(let [params (box/url-params)
      _ (m/set :params params)
      forms (->> (box/sql ["select qr.resource || jsonb_build_object('id', qr.id,
'lastUpdated', to_char(qr.ts, 'dd-MM-yyyy HH24:mm'),
'subject', qr.resource#>'{subject}' || p.resource,
                                       	 'questionnaire', jsonb_build_object('title', q.resource#>>'{title}',
                                                                             'id', q.id,
                                                                             'resourceType', 'Questionnaire')
                                        ) resource from questionnaireresponse qr
left join patient p on p.id = qr.resource#>>'{subject,id}'
left join questionnaire q on q.resource#>>'{url}' = qr.resource#>>'{questionnaire}'
where q.resource::text ilike ?" (str "%" (:query params) "%")])
                 (filter (fn [f] (some? (get-in f [:resource :questionnaire :id])))))
      forms (cond->> forms
              (not-empty (:status params))
              (filter (fn [f] (= (:status params) (get-in f [:resource :status])))))
      _ (m/set :forms forms)]

  [:<>
   (when (:status params)
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
                            (or (some-> status not-empty clojure.string/capitalize) "View all")]])]
       [:turbo-stream {:target "status-filters"
                       :action "update"}
        [:template
         (status-button "")
         (status-button "completed")
         (status-button "in-progress")]])
     )

   [:turbo-stream {:target "forms"
                   :action "update"
                   :class "contents"}
    (into
     [:template]
     (mapcat (fn [f]
               [[:div
                 {:class "bg-white border-b px-4 py-4 text-sm font-medium whitespace-nowrap"}
                 [:h2
                  {:class "font-medium text-gray-800 dark:text-white"}
                  (get-in f [:resource :questionnaire :title])]]
                [:div
                 {:class "bg-white border-b  px-4 py-4 text-sm font-medium whitespace-nowrap"}
                 [:div
                  {:class
                   "inline px-3 py-1 text-sm font-normal rounded-full text-emerald-500 gap-x-2 bg-emerald-100/60 dark:bg-gray-800"}
                  (get-in f [:resource :status])]]
                [:div
                 {:class "bg-white border-b  px-4 py-4 text-sm whitespace-nowrap"}
                 [:div
                  [:h4 {:class "text-gray-700 dark:text-gray-200"}
                   (get-in f [:resource :lastUpdated])]]]
                [:div
                 {:class "bg-white border-b px-4 py-4 text-sm whitespace-nowrap"}
                 [:div
                  [:h4 {:class "text-gray-700 dark:text-gray-200"}
                   (str (get-in f [:resource :subject :name 0 :given 0]) ", "
                        (get-in f [:resource :subject :name 0 :family]))
                   ]]]
                [:div
                 {:class "bg-white border-b  px-4 py-4 text-sm whitespace-nowrap cursor-pointer text-blue-500"}
                 [:a {:class "px-3 py-2 text-gray-500 transition-colors duration-200 rounded-lg dark:text-gray-300 hover:bg-gray-100"

                      :href (str "/ui/sdc#/questionnaire-response/" (get-in f [:resource :id]))}
                  "Open"
                  ]]])

             forms))]])
