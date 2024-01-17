import axios from 'axios'
import store from '@/store'
import router from '@/router'
import { Message } from 'element-ui';


axios.defaults.timeout = 60000

let defaultHeaders = {
  "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8"
};


let fetch = {
  get: (url, params = {}) => {
    return new Promise((resolve, reject) => {
      axios.get(url, params).then(response => {
        resolve(response.data);
      }).catch(error => {
        reject(error);
      })
    });
  },
  post: (url, params = {}, isJson = false) => {
    return new Promise((resolve, reject) => {

      fetch.postBasics(url, params, isJson).then(res => {
        let data = res.data;
        if(data.code == 5) {
          Message.error(`${data.msg}(${data.data})`);
          router.push('/login');
        } else if(data.code == 500) {
          Message.error(data.msg);
        }
        resolve(data);
      }).catch(error => {
        Message.error(error);
        reject(error);
      })
    });
  },


  postBasics: (url, params = {}, isJson) => {
    return new Promise((resolve, reject) => {
        defaultHeaders.satoken = store.state.user.userInfo.token ?? '';

        if(isJson) {
          defaultHeaders['Content-Type'] = 'application/json;charset=UTF-8'
        } else {
          defaultHeaders['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8'
        }
        
        axios.post(url, params, {
          headers: defaultHeaders
        }).then(response => {
          resolve(response);
        }).catch(error => {
          reject(error);
        });
    });
  }
}

export default fetch;
