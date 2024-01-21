import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import '@/assets/css/base.css'
import '@/components/index'

import * as echarts from 'echarts';
Vue.prototype.$echarts = echarts;

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);

import less from 'less';
Vue.use(less);

Vue.config.productionTip = false;

Vue.prototype.$bus = new Vue(); //全局事件总线

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
