[SCI API](https://github.com/babashka/sci/blob/master/API.md) is fully availiable

Additional functions defined in aidbox described below:

# Table of Contents:

- [`box`](#box)
  - [`sql`](#box/sql) - Run SQL query
  - [`rpc`](#box/rpc) - Run RPC request
  - [`http`](#box/http) - Run HTTP request
  - [`create`](#box/create) - Create resource
  - [`read`](#box/read) - Read resource
  - [`update`](#box/update) - Update resource
  - [`delete`](#box/delete) - Delete resource
  - [`redirect`](#box/redirect) - Delete resource
  - [`form-params`](#box/form-params) - Get form parameters
  - [`url-params`](#box/url-params) - Get query url parameters
  - [`route-params`](#box/form-params) - Get route parameters
  - [`request`](#box/request) - Get ring request map
  - [`version`](#box/version) - Returns aidbox version
  - [`set-header`](#box/set-header) - Set header to the response
- [`auth`](#auth)
  - [`user-info`](#auth/user-info) - Returns user info
  - [`session`](#auth/session) - Returns session
  - [`authenticate`](#auth/authenticate) - Performs user authentication
  - [`csrf`](#auth/csrf) - Generate CSRF token
  - [`csrf`](#auth/csrf-valid?) - Validate CSRF token in current context
  - [`logout`](#auth/logout) - Delete user's session and resets cookies
- [`m`](#m)
  - [`set`](#m/set) - Store data in current context for debug purposes
  - [`get`](#m/get) - Get stored debug data
- [`util`](#util)
  - [`md5`](#util/md5) - Generates MD5 hash

# <a name="box">`box`</a>

## <a name="box/sql">`box/sql`</a>
```clojure
(box/sql "select * from patient")
```
## <a name="box/rpc">`box/rpc`</a>
```clojure
(box/rpc {:method "aidbox.echo"
          :params {}})
```
Run RPC request

## <a name="box/http">`box/http`</a>
```clojure
(box/http opts)

(box/http {:request-method "GET"
           :uri "/$config"
	   :body ...})
```
Run HTTP request

## <a name="box/create">`box/create`</a>
```clojure
(box/create res)

(box/create {:name [{:family "Doe"}]
	     :resourceType "Patient"});
```

Create resource. Returns created resource

## <a name="box/read">`box/read`</a>
```clojure
(box/read ref)

(box/read {:id "pt-1" :resourceType "Patient"})
```
Read resource.
`ref` map must contain `id` and `resourceType`

In case aidbox can't find given resource it'll return:
```clojure
{:id "not-found" :resourceType "OperationOutcome"}
```

## <a name="box/update">`box/update`</a>
```clojure
(box/update res)

(box/update {:id "pt-1" :gender "male" :resourceType "Patient" })
```

Update resource. Returns updated resource.
`res` map must contain `id` and `resourceType`

## <a name="box/delete">`box/delete`</a>
```clojure
(box/delete res)

(box/delete {:id "pt-1" :resourceType "Patient" })
```

Delete resource. Returns deleted resource.
`res` map must contain `id` and `resourceType`

## <a name="box/redirect">`box/redirect`</a>

```clojure
(box/redirect locaiton & [params])

(box/redirect "/ui/patients")

(box/redirect "/ui/patients" {:query "hello"})
```

Redirect to given location. Takes an optional query params map.

## <a name="box/form-params">`box/form-params`</a>

```clojure
(box/form-params)
```

Returns an map with form parameters sent to route.


## <a name="box/url-params">`box/url-params`</a>
```clojure
(box/url-params)
```
Returns a map with query parameters.

For route `/ui/patients?query=John` will return:
```clojure
{:query "John"}
```

## <a name="box/route-params">`box/route-params`</a>
```clojure
(box/route-params)
```
Returns map with route params

For route defined as `/ui/patients/:id_get.clj` and actual URL `/ui/patients/pt-1` will return: -
```clojure
{:id "pt-1"}
```
## <a name="box/request">`box/request`</a>
```clojure
(box/request)
```

Returns request map according to [ring specification](https://github.com/ring-clojure/ring/blob/master/SPEC.md#14-request-maps)
## <a name="box/version">`box/version`</a>
```clojure
(box/version) ;; => edge2112:335c22e8b
```

Returns aidbox version

## <a name="box/set-header">`box/set-header`</a>
```clojure
(box/set-header! name value)

(box/set-header! "content-type" "application/json")
```

Set header to the response

# <a name="auth">`auth`</a>

## <a name="user-info">`auth/user-info`</a>
```clojure
(auth/user-info) ;; => {:id 'user' :resourceType "User"}
```

Returns User resource for authenticated user.

## <a name="auth/session">`auth/session`</a>
```clojure
(auth/session)

;; {:id "1a9bbfbf-5260-4f57-bcd5-3088bcf2341c",
;;  :meta
;;  {:versionId 11, :lastUpdated "2023-12-12T16:39:18.635084+04:00"},
;;  :type "login",
;;  :access_token "NTM4NWQxMWEtYjQ2ZC00MjJjLWI2YjgtNTgyMjU5MDllMTUx"}

```
Returns Session resource

## <a name="auth/authenticate">`auth/authenticate`</a>
```clojure
(auth/authenticate creds)

(auth/authenticate {:username "user" :password "password"})
```

Performs user authentication and set cookie.
`creds` map must contain `username` and `password` keys.

## <a name="auth/csrf">`auth/csrf`</a>

```clojure
(auth/csrf)
```

Generates CSRF token

## <a name="auth/csrf-valid?">`auth/csrf-valid?`</a>

```clojure
(auth/csrf-valid?)
```

Validate CSRF token in current context
## <a name="auth/logout">`auth/logout`</a>

```clojure
(auth/logout)
```

Delete user's session and resets cookies

# <a name="m">`m`</a>
## <a name="m/set">`m/set`</a>
```clojure
(m/set :name "John Doe")
(m/set :patient {:id "pt-1" :resourceType "Patient"})
```

Store data in current context for debug purposes.
To view this data, send request with header `X-Aidbox-Dev: model`

## <a name="m/get">`m/get`</a>

```clojure
(m/get :name) ;; => "John Doe"
(m/get :user) ;; => {:id "pt-1" :resourceType "Patient"}
```

Get stored debug data

# <a name="util">`util`</a>

## <a name="util/md5">`md5`</a>
```clojure
(util/md5 "string") ;; => "b45cffe084dd3d20d928bee85e7b0f21"
```

Generates MD5 hash
