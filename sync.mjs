import { readdirSync, statSync, readFileSync } from 'fs';
import { join } from 'path';
import axios from 'axios';
import { load } from 'js-yaml';
import dotenv from 'dotenv';


const resToRefString = res => `${res.resourceType}/${res.id}`;

function listFilesRecursively(dir, fileList = []) {
  const files = readdirSync(dir);

  files.forEach(file => {
    const filepath = join(dir, file);
    if (statSync(filepath).isDirectory()) {
      fileList = listFilesRecursively(filepath, fileList);
    } else {
      fileList.push(filepath);
    }
  });

  return fileList;
}

const directoryPath = 'routes';



const operations = listFilesRecursively(directoryPath)
  .filter(filename => !filename.endsWith('.DS_Store')
    && !filename.endsWith('.test.js')
    && !filename.endsWith('.test.mjs')
    && !filename.endsWith(".md"))
  .map(buildOperationResource);

const operationIdsToDeploy = new Set(operations.map(op => op.id));

function getContentTypeByExt(ext) {
  switch (ext) {
    case "js":
      return 'text/javascript';
    case "css":
      return 'text/css';
    default:
      return 'text/html';
  }
}

function buildOperationResource(filepath) {
  const [_, ...splited] = filepath.split(/[\/\._]/);
  const method = splited.slice(-2)[0];
  console.log(filepath);
  const route = splited.slice(0, -2).map(route_path => {
    if (route_path[0] === ':')
      return { name: route_path.slice(1) }
    else
      return route_path
  });

  const ext = splited.slice(-1)[0];
  return {
    id: 'aidbox-' + filepath.replaceAll(/[_\.\/]/g, '-'),
    resourceType: "Operation",
    request: [method].concat(route),
    action: ext === 'clj' ? "ui/clojure" : "ui/static",
    data: {
      content: readFileSync(filepath, { encoding: 'utf8' }),
      headers: { 'content-type': getContentTypeByExt(ext) }
    }
  }
}

/**
 * 
 * @param {import('axios').AxiosInstance} client 
 */
async function removeRedundantOperationIdsFromAidbox(client) {
  const resp = await client.post(
    '/$sql',
    "select id from operation where id like 'aidbox-ui-%'",
  );


  const operationsToDelete = resp.data
    .map(op => op.id)
    .filter(id => !operationIdsToDeploy.has(id));

  await Promise.all(operationsToDelete.map(async operationId => {

    return client.delete(
      `/Operation/${operationId}`,
    ).then(() => {
      console.log("[Deleted] ", operationId)
    })
  }))

}

/**
 * 
 * @param {import('axios').AxiosInstance} client 
 */
async function getSeedImport(client) {
  try {
    const resp = await client.get(
      '/SeedImport/aidbox-ui');
    return resp.data.resources;
  } catch (e) {
    console.log(e);
    if (e.response.status === 404)
      return []
    else
      throw e
  }
}

function getResourcesToUpload() {
  return readFileSync('aidbox-resources.yaml', 'utf8')
    .split(/\n---\n/)
    .map(load)
    .filter(res => res !== undefined);
}


/**
 * 
 * @param {import('axios').AxiosInstance} client 
 */
async function syncAidboxResources(client) {
  const seedImportRefs = await getSeedImport(client);
  const resourcesToUpload = getResourcesToUpload();

  const resourceIdsToUpload = new Set(resourcesToUpload.map(resToRefString))

  const toDelete = seedImportRefs.filter(ref => !resourceIdsToUpload.has(resToRefString(ref)));


  await Promise.all(toDelete.map(async ref => {
    return client.delete(`/${resToRefString(ref)}`).then(() => {
      console.log("[Deleted]: ", resToRefString(ref))
    })
  }));


  await client.put(
    `/SeedImport/aidbox-ui`,
    { resources: resourcesToUpload.map(res => ({ id: res.id, resourceType: res.resourceType })) },
  )

  await Promise.all(resourcesToUpload.map(res => {

    return client.put(
      `/${resToRefString(res)}`,
      res,
    ).then(() => {
      console.log('[Aidbox resource saved]: ', resToRefString(res))
    })
  }))
}

/**
 * 
 * @param {import('axios').AxiosInstance} client 
 */
async function deployOperations(client) {
  await Promise.all(operations.map(operation => {
    return client.put(
      `/Operation/${operation.id}`,
      operation,
    ).then(() => {
      console.log("[Deployed]: ", operation.id);
    })
  }))
}


const main = async () => {
  dotenv.config();

  const baseURL = process.env.AIDBOX_URL;
  const username = process.env.AIDBOX_USER;
  const password = process.env.AIDBOX_PASSWORD;

  if (!baseURL || !username || !password) {
    console.error("Please that your provide environments variables: AIDBOX_URL,AIDBOX_USER,AIDBOX_PASSWORD ")
    process.exit(1)
  }
  const aidboxClient = axios.create({
    baseURL,
    headers: {
      "Content-Type": "application/json",
    },
    auth: {
      username,
      password
    }
  })

  await syncAidboxResources(aidboxClient);
  await removeRedundantOperationIdsFromAidbox(aidboxClient);
  await deployOperations(aidboxClient);
}

main();