(box/set-header! "Content-Type" "text/vnd.turbo-stream.html; charset=utf-8")

(let [params (box/url-params)
      patients (->> (box/sql ["select resource || jsonb_build_object('id', id) resource from Patient
where resource::text ilike ?" (str "%" (:query params) "%")])
                    (mapv :resource))
      _ (m/set :patients patients)
      ]

  [:turbo-stream {:target "patients"
                  :action "update"}
   (into [:template]
         (map (fn [p]
                   [:div {:class "contents"
                          :id (str "patient-" (:id p))}
                    [:div
                     {:class "px-4 py-4 text-sm font-medium whitespace-nowrap flex items-center  border-b"}
                     [:h2
                      {:class "font-medium text-gray-800 dark:text-white"}
                      (str (get-in p [:name 0 :given 0]) ", "
                           (get-in p [:name 0 :family]))]]
                    [:div
                     {:class "px-12 py-4 text-sm font-medium whitespace-nowrap flex items-center  border-b"}
                     (when-let [birth-date (get-in p [:birthDate])]
                       [:div
                        {:class
                         "inline px-3 py-1 text-sm font-normal rounded-full text-emerald-500 gap-x-2 bg-emerald-100/60 dark:bg-gray-800"}
                        birth-date])]
                    [:div
                     {:class "px-4 py-4 text-sm whitespace-nowrap  flex items-center  border-b"}
                     [:div
                      [:h4 {:class "text-gray-700 dark:text-gray-200"} (get-in p [:gender])]]]
                    [:div
                     {:class "px-4 py-4 text-sm whitespace-nowrap  flex items-center border-b"}
                     [:form {:action "/ui/patients/edit"
                             :class "mb-0"}
                      [:input {:type "hidden"
                               :name "patient-id"
                               :value (:id p)}]
                      [:button
                       {:class
                        "px-3 py-2 text-gray-500 transition-colors duration-200 rounded-lg dark:text-gray-300 hover:bg-gray-100"}
                       [:i.fas.fa-pencil]]]
                     [:a
                      {:class
                       "px-3 py-2 text-gray-500 transition-colors duration-200 rounded-lg dark:text-gray-300 hover:bg-gray-100"
                       :href (str "/ui/patients/" (str (:id p)))
                       :data-turbo-method "DELETE"
                       :data-turbo-confirm "Do you want to delete patient?"}
                      [:i.fas.fa-trash]]]]) patients))


   ]
  )
