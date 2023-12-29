(box/set-header! "Content-Type" "text/vnd.turbo-stream.html; charset=utf-8")

(let [params (box/url-params)
      patient (when (:patient-id params)
                (box/read {:id (:patient-id params) :resourceType "Patient"}))
      _ (m/set :patient patient)
      ]
  [:turbo-stream {:target "modal"
                  :action "update"}
   [:template
    [:section {:id "edit-patient"
               :class "fixed top-0 bottom-0 right-0 left-0 z-10"}
     [:script
"function removeModal(e) {
if(e.target.id == 'edit-patient-root') {
e.target.parentElement.remove();
}
}"
      ]
     [:div
      {:class "min-h-screen p-6 bg-gray-100 flex items-center justify-center bg-opacity-80"
       :id "edit-patient-root"
       :onclick "removeModal(event)"
       }
      [:div
       {:class "container max-w-screen-lg mx-auto"
        :id "edit-patient-modal"}
       [:div
        [:div
         {:class "bg-white rounded shadow-lg p-4 px-4 md:p-8 mb-6"}
         [:div
          {:class "grid gap-4 gap-y-2 text-sm grid-cols-1 lg:grid-cols-3"}
          [:div
           {:class "text-gray-600"}
           [:p {:class "font-medium text-lg"} "Personal Details"]
           [:p "Please fill out all the fields."]]
          [:form {:action "/ui/patients/save" :method "POST"
                  :class "lg:col-span-2"}
           (when (:id patient)
             [:input {:type "hidden"
                      :name "id"
                      :value (:id patient)}]
             )
           [:div
            {:class
             "grid gap-4 gap-y-2 text-sm grid-cols-1 md:grid-cols-5"}
            [:div
             {:class "md:col-span-5"}
             [:label {:for "full_name"} "First Name"]
             [:input
              {:type "text",
               :name "given",
               :id "full_name",
               :required true
               :class "h-10 border mt-1 rounded px-4 w-full bg-gray-50",
               :value (get-in patient [:name 0 :given 0])}]]
            [:div
             {:class "md:col-span-5"}
             [:label {:for "full_name"} "Last Name"]
             [:input
              {:type "text",
               :name "family",
               :id "full_name",
               :required true
               :class "h-10 border mt-1 rounded px-4 w-full bg-gray-50",
               :value (get-in patient [:name 0 :family])}]]
            [:div
             {:class "md:col-span-3"}
             [:label {:for "birth-date"} "Date of Birth"]
             [:input
              {:type "date",
               :name "birth-date",
               :required true
               :id "birth-date",
               :class "h-10 border mt-1 rounded px-4 w-full bg-gray-50",
               :value (get-in patient [:birthDate])
               :placeholder ""}]]
            [:div
             {:class "md:col-span-2"}
             [:label {:for "gender"} "Gender"]
             [:select {:name "gender"
                       :required true
                       :class "h-10 border mt-1 rounded px-4 w-full bg-gray-50"}
              [:option {:value "male" :selected (= (:gender patient) "male")} "Male"]
              [:option {:value "female" :selected (= (:gender patient) "female")} "Female"]]]

            [:div
             {:class "md:col-span-5 text-right"}
             [:div
              {:class "inline-flex items-end"}
              [:button
               {:class
                "bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"}
               "Submit"]]]]]]]]]]]]

   ])

#_[:turbo-stream {:target "modal"
                  :action "update"}
   [:template
    [:span "hello"]]
   ]
