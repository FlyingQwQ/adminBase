
let api = [];
let fetch = [];
let router = [];

import * as base from '@/pages/base/config'
api = Object.assign(api, base.api);
fetch = Object.assign(fetch, base.fetch);
router = Object.assign(router, base.router);



export {
  api,
  fetch,
  router
}