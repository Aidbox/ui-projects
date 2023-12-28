

function waitForUpdate() {

  const version = localStorage.getItem('aidbox-operation-version')

  if ((version === null) || (version === "undefined")) {
    console.log("here")
    return fetch('/Operation/$changes')
      .then(resp => resp.json())
      .then(data => {
        localStorage.setItem('aidbox-operation-version', data.version)
        waitForUpdate()
      })
  }

  return fetch(`/Operation/$changes?version=${version}`)
    .then(resp => {
      if (resp.status === 304) {
        console.log("timeout")
        setTimeout(waitForUpdate, 1000)
        return null;
      } else {
        return resp.json();
      }
    })
    .then(data => {
      if (data !== null) {
        localStorage.setItem('aidbox-operation-version', data.version)
        window.location.reload()
      }
    })
}
waitForUpdate();

function tag(name, ...children) {
    const result = document.createElement(name);
    for (const child of children) {
        if (typeof(child) === 'string') {
            result.appendChild(document.createTextNode(child));
        } else {
            result.appendChild(child);
        }
    }

    result.att$ = function(name, value) {
        this.setAttribute(name, value);
        return this;
    };

    result.onclick$ = function(callback) {
        this.onclick = callback;
        return this;
    };

    return result;
}

async function getDebugModel() {
    var resp = await fetch(window.location.href, {
	headers: {
	    "x-aidbox-dev": "model"
	}
    });
    var json = await resp.json();

    return json;
}

async function showDebugOverlay() {


    var root = tag('div')
	.att$("style", `position: absolute;
bottom: 10; right: 10; top: 10; left: 10;
background-color: rgba(255,255,255,0.95);`)
	.att$("id", "debug-overlay");

    var header = tag('div',
		     tag('h1', "Debug Overlay"),
		     tag('button', 'CLOSE').onclick$(() => root.remove()),
		    )
	.att$("style", `
display: flex; width: 100%;
justify-content: space-between;
background-color: rgba(124,0,124,0.3);
padding: 10px 10px;`);
    root.appendChild(header);

    var modelPreview = tag("pre").att$("id", "model");
    root.appendChild(modelPreview);

    document.body.insertAdjacentElement("beforeend", root);

    var model = await getDebugModel();
    console.log("MODEL: ", model);
    modelPreview.innerHTML = JSON.stringify(model, null, 2);
}

function debugButton() {
    var button = document.createElement("button");

    button.onclick = () => showDebugOverlay();
    button.innerHTML = "DEBUG"
    button.style.cssText = `
position: fixed;
bottom: 0; right: 0;
background-color: rgba(124,0,124,0.3);
padding: 5px 10px`;

    return button;
}

document.body.insertAdjacentElement("beforeend", debugButton());
