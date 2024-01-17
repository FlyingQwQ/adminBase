import Vue from 'vue'
import VueRouter from 'vue-router'

import * as config from '@/config'

import Login from '@/pages/base/login/login.vue'


Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'login',
    component: Login // 登录页面
  },
  {
    path: '/main',
    component: () => import('@/pages/base/main/Frame.vue'),
    children: [
      ...config.router,
      {
        path: '',
        component: () => import('@/pages/base/main/Home.vue')
      },
    ]
  },

  {
    name: '404',
    path: '/404',
    component: () => import('@/pages/base/404.vue')
  },
  {
    path: '*',    // 此处需特别注意至于最底部
    redirect: '/404'
  }
]

const router = new VueRouter({
  mode: 'hash',
  base: process.env.BASE_URL,
  routes
})

const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function (location) {
    return originalPush.call(this, location).catch(err => err)
}


export default router
