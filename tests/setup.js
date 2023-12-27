import axios from 'axios'

const AIDBOX_URL = 'http://localhost:8765';

const client = axios.create({baseURL: AIDBOX_URL,
                             headers: {"content-type": "application/json",
                                       "Authorization": "Basic cm9vdDpzZWNyZXQ="}})

async function http(opts) {
  return await client.request(
    {url: opts.url,
     method: opts.method ?? 'get',
     data: opts.body}
  ).then(resp => resp.data);
}


globalThis.http = http
