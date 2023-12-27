

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
