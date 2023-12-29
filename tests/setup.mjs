import axios from 'axios'
import dotenv from 'dotenv';

dotenv.config();

const baseURL = process.env.AIDBOX_URL;
const username = process.env.AIDBOX_USER;
const password = process.env.AIDBOX_PASSWORD;


const client = axios.create({
  baseURL,
  auth: { username, password },
  headers: {
    "content-type": "application/json",
  }
})

async function http(opts) {
  return await client.request(
    {
      url: opts.url,
      method: opts.method ?? 'get',
      data: opts.body,
      headers: opts.headers,
    }
  ).then(resp => resp.data);
}


globalThis.http = http
