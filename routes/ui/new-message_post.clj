

(box/http {:method "POST" :uri "/AuditEvent" :resource {:message (:message (request/form-data))}})


[:turbo-stream
 [:div ""]]
