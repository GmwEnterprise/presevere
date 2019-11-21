import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/login',
      component: () => import('@/views/sign/Login.vue')
    }, {
      path: '/register',
      component: () => import('@/views/sign/Register.vue')
    }, {
      path: '/',
      component: () => import('@/views/Home.vue')
    }
  ]
})
