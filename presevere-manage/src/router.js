import Vue from 'vue'
import Router from 'vue-router'
import _ from 'lodash'

Vue.use(Router)

const subRoutes = (() => {
  const r = require.context('./views/', true, /\.router\.js$/)
  const routes = r.keys().map(key => r(key).default)
  return _.flatten(routes)
})()

const router =  new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      component: () => import('@/views/Home.vue'),
      meta: {
        loginRequired: true,
      },
      children: [
        ...subRoutes
      ]
    }, {
      path: '/login',
      component: () => import('@/views/sign/Login.vue')
    }, {
      path: '/register',
      component: () => import('@/views/sign/Register.vue')
    }, {
      path: '/demo',
      component: () => import('@/demo/Demo.vue')
    }
  ]
})

router.beforeEach((to, from, next) => {
  if (to.meta.loginRequired && !localStorage.getItem('token')) {
    next({
      path: '/login',
      query: {
        // 登录成功后跳转回页面
        redirectUrl: to.fullPath
      }
    })
  } else {
    next()
  }
})

export default router