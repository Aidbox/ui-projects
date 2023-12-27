const fs = require('fs');
const path = require('path');
const axios = require('axios');
const yaml = require('js-yaml')

function listFilesRecursively(dir, filelist = []) {
    const files = fs.readdirSync(dir);

    files.forEach(file => {
        const filepath = path.join(dir, file);
        if (fs.statSync(filepath).isDirectory()) {
            filelist = listFilesRecursively(filepath, filelist);
        } else {
            filelist.push(filepath);
        }
    });

    return filelist;
}

const directoryPath = 'routes';

function getContentTypeByExt(ext) {
  if (ext === 'js')
    return 'text/javascript';
  else if (ext === 'js')
    return 'text/css';
  return 'text/html'
}

function buildOperationResource(filepath) {
  const [_, ...splited] = filepath.split(/[\/\._]/);
  const method = splited.slice(-2)[0];

  const route = splited.slice(0, -2).map(route_path => {
    if (route_path[0] === ':')
      return {name: route_path.slice(1)}
    else
      return route_path});

  const ext = splited.slice(-1)[0];

  return {id: 'aidbox-' + filepath.replaceAll(/[_\.\/]/g, '-'),
          resourceType: "Operation",
          request: [method].concat(route),
          action: ext === 'clj' ? "ui/clojure" : "ui/static",
          data: {content: fs.readFileSync(filepath, {encoding: 'utf8'}),
                 headers: {'content-type': getContentTypeByExt(ext)}}}}

const AIDBOX_URL = 'http://localhost:8765';

async function deployOperation(operation) {
  await axios.put(
    `${AIDBOX_URL}/Operation/${operation.id}`,
    operation,
    {
      headers: {
        "content-type": "application/json",
        "Authorization": "Basic cm9vdDpzZWNyZXQ="
      }
    }
  );

  console.log(operation.id + " deployed");
}

const operations = listFilesRecursively(directoryPath)
      .filter(filename => !filename.endsWith('.DS_Store'))
      .map(buildOperationResource);

const operationIdsToDeploy = new Set(operations.map(op => op.id));

async function removeRedundantOperationIdsFromAidbox() {
  const resp = await axios.post(
    `${AIDBOX_URL}/$sql`,
    "select id from operation where id like 'aidbox-ui-%'",
    {
      headers: {
        "content-type": "application/json",
        "Authorization": "Basic cm9vdDpzZWNyZXQ="
      }
    }
  );

  const deployedOperationIds = resp.data.map(op => op.id);

  const operationsToDelete = deployedOperationIds.filter(id => !operationIdsToDeploy.has(id));

  operationsToDelete.map(async operationId => {

    await axios.delete(
      `${AIDBOX_URL}/Operation/${operationId}`,
      {headers: {"Authorization": "Basic cm9vdDpzZWNyZXQ="}}
    )

    console.log(operationId + " deleted")

  });

}


// console.log(files[0]);



async function getSeedImport() {
  try {
    const resp = await axios.get(
      `${AIDBOX_URL}/SeedImport/aidbox-ui`,
      {
        headers: {
          "content-type": "application/json",
          "Authorization": "Basic cm9vdDpzZWNyZXQ="
        }
      }
    );
    return resp.data.resources;
  } catch (e) {
    if (e.response.status === 404)
      return []
    else
      throw e
  }
}

function getResourcesToUpload() {
  return fs.readFileSync('aidbox-resources.yaml', 'utf8')
           .split(/\n---\n/)
           .map(yaml.load)
           .filter(res => res !== undefined);
}

const resToRefString = res => `${res.resourceType}/${res.id}`;


async function syncAidboxResources() {
  const seedImportRefs = await getSeedImport();
  const resourcesToUpload = getResourcesToUpload();

  const resourceIdsToUpload = new Set(resourcesToUpload.map(resToRefString))

  const toDelete = seedImportRefs.filter(ref => !resourceIdsToUpload.has(resToRefString(ref)));

  toDelete.map(async ref => {

    await axios.delete(
      `${AIDBOX_URL}/${resToRefString(ref)}`,
      {headers: {"Authorization": "Basic cm9vdDpzZWNyZXQ="}}
    )

    console.log(resToRefString(ref) + " deleted")
  });


  await axios.put(
    `${AIDBOX_URL}/SeedImport/aidbox-ui`,
    {resources: resourcesToUpload.map(res => ({id: res.id, resourceType: res.resourceType}))},
    {headers: {
          "content-type": "application/json",
          "Authorization": "Basic cm9vdDpzZWNyZXQ="
        }}
  )

  resourcesToUpload.map(async res => {

    await axios.put(
      `${AIDBOX_URL}/${resToRefString(res)}`,
      res,
      {headers: {"Authorization": "Basic cm9vdDpzZWNyZXQ="}}
    )

  })

}

syncAidboxResources()
removeRedundantOperationIdsFromAidbox();
operations.map(deployOperation);
