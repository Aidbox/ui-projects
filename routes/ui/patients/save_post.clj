(box/set-header! "Content-Type" "text/vnd.turbo-stream.html; charset=utf-8")

(let [params (box/form-params)
      patient (cond-> {:resourceType "Patient"
                       :name [{:given [(get-in params [:given])]
                               :family (get-in params [:family])}]
                       :gender (get-in params [:gender])
                       :birthDate (get-in params [:birth-date])}
                (:id params)
                (assoc :id (:id params)))
      saved-patient (box/create patient)
      _ (m/set :saved-patient saved-patient)]

  (if (= (:resourceType saved-patient) "Patient")
    [:<>
     [:turbo-stream {:target "edit-patient"
                     :action "remove"}]

     [:turbo-stream (if-not (:id params)
                      {:target "patients"
                       :action "append"}
                      {:target (str "patient-" (:id patient))
                       :action "replace"})
      [:template
       [:div {:class "contents"
              :id (str "patient-" (:id saved-patient))}
        [:div
         {:class "px-4 py-4 text-sm font-medium whitespace-nowrap flex items-center border-b"}
         [:h2
          {:class "font-medium text-gray-800 dark:text-white"}
          (str (get-in saved-patient [:name 0 :given 0]) ", "
               (get-in saved-patient [:name 0 :family]))]]
        [:div
         {:class "px-12 py-4 text-sm font-medium whitespace-nowrap flex items-center border-b"}
         (when-let [birth-date (get-in saved-patient [:birthDate])]
           [:div
            {:class
             "inline px-3 py-1 text-sm font-normal rounded-full text-emerald-500 gap-x-2 bg-emerald-100/60 dark:bg-gray-800"}
            birth-date])]
        [:div
         {:class "px-4 py-4 text-sm whitespace-nowrap  flex items-center border-b"}
         [:div
          [:h4 {:class "text-gray-700 dark:text-gray-200"} (get-in saved-patient [:gender])]]]
        [:div
         {:class "px-4 py-4 text-sm whitespace-nowrap  flex items-center border-b"}
         [:form {:action "/ui/patients/edit"
                 :class "mb-0"}
          [:input {:type "hidden"
                   :name "patient-id"
                   :value (:id saved-patient)}]
          [:button
           {:class
            "px-3 py-2 text-gray-500 transition-colors duration-200 rounded-lg dark:text-gray-300 hover:bg-gray-100"}
           [:i.fas.fa-pencil]]]
         [:a
          {:class
           "px-3 py-2 text-gray-500 transition-colors duration-200 rounded-lg dark:text-gray-300 hover:bg-gray-100"
           :href (str "/ui/patients/" (str (:id saved-patient)))
           :data-turbo-method "DELETE"
           :data-turbo-confirm "Do you want to delete patient?"}
          [:i.fas.fa-trash]]]]]

      ]
     ]
    [:turbo-stream {:target "edit-patient-modal"
                    :action "update"}
     [:template [:div {:class "bg-white p-10" }][:code (pr-str saved-patient)]]]
    )

  )
