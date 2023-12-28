(box/set-header! "Content-Type" "text/vnd.turbo-stream.html; charset=utf-8")

(let [params (box/route-params)
      result (box/delete {:id (:id params) :resourceType "Patient"})]
  (when (= (:resourceType result) "Patient")
    [:turbo-stream {:target (str "patient-" (:id result))
                :action "remove"}]
    )
  )
