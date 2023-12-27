(box/set-header! "Content-Type" "text/vnd.turbo-stream.html; charset=utf-8")

[:turbo-stream {:action "append" :target "chat"}
 [:template
  [:div "new message"]]]
