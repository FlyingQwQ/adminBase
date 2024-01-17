import fetchTool from '@/utils/fetchTool'
import api from "./api";

let fetch = {};

for(let key in api) {
  fetch[key] = (params, isJson) => fetchTool.post(api[key], params, isJson);
}

export default fetch;