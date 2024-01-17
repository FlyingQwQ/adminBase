
let type = 'dev';

let platform = {}
let host = '';

switch(type) {
  case 'dev':
    platform.host = 'http://localhost:8081'
    break;
  case 'pro':
    break;
  default:
    break;
}

platform.type = type;
platform.systemName = 'Base';


export {
  platform
}