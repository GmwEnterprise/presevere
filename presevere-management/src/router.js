import Vue from 'vue'
import Router from 'vue-router'
import _ from 'lodash'

Vue.use(Router)

const moduleRoutes = (() => {
  const r = require.context('./', true, /\.router\.js$/)
  const routes = r.keys().map(key => r(key).default)
  return _.flatten(routes)
})()

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      redirect: '/sys'
    }, {
      path: '/sys',
      name: 'system',
      meta: {
        auth: true
      },
      component: () => import('@/views/backstage/SystemMain.vue'),
      children: [
        ...moduleRoutes
      ]
    }, {
      path: '/login',
      name: 'login',
      component: () => import('@/views/Login.vue')
    }, {
      path: '/reg',
      name: 'register',
      component: () => import('@/views/Register.vue')
    }
  ]
})

router.beforeEach((to, from, next) => {
  if (to.meta.auth && !localStorage.getItem('token')) {
    next({
      path: '/login',
      query: {
        // 登录成功后跳转回页面
        redirect: to.fullPath
      }
    })
  } else {
    next()
  }
})

export default router