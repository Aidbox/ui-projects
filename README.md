# Aidbox UIs

## Development


1. Get Aidbox Liecense
2. Deploy Aidbox locally `docker compose up -d`
3. Start development `npm run dev`


``` sh
npm run dev
```

# Development

Aidbox uses sci language (minified Clojure). [sci API](https://github.com/babashka/sci/blob/master/API.md) is fully available.

In order to get access to Aidbox functions see [API.md](API.md).

### 1. Define a new route

Create file inside `routes` directory. Path name matches route name, filename suffix matches **method** name
```
- routes
  - ui
    - patients_get.clj
```

We just create new route `GET /ui/patients`. Put the following content to this file:

```clojure
(let [patients (box/sql "select * from patients")]
  [:div
   [:h1 "List of patients"]
   [:ul
    (for [p patients]
      [:li (:id p)]
      )]])
```

Aidbox UI projects uses [Hiccup syntax](https://github.com/weavejester/hiccup/blob/master/doc/syntax.md) to describe pages.

Now, our route will return simple patients list.

```html
GET /ui/patients

<div>
    <h1>List of patients</h1>
    <ul>
	<li>patient-1</li>
	...
	<li>patient-100</li>
    </ul>
</div>

```

### 2. Debug route

Sometimes we need to see, what `patients` holds. We can use `m/set` function and additional header
to look in inside `patients`.

Change your route source like following.

```clojure
(let [patients (box/sql "select * from patients")]
  (m/set :patients patients) ;; store under :patients key
  ...html content...)
```

Now, sending the same request but with additional header `X-Aidbox-Dev: model` will show all info stored
by `m/set`:

```http
GET /ui/patients
X-Aidbox-Dev: model

{"patients": [
	{"id": "patient-1"},
	,,,
	{"id": "patient-100"}
]}

```
