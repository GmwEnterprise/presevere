import Vue from 'vue'
import Router from 'vue-router'
import _ from 'lodash'

Vue.use(Router)

const moduleRoutes = (() => {
  const r = require.context('./', true, /\.router\.js$/)
  const routes = r.keys().map(key => r(key).default)
  return _.flatten(routes)
})()

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/sign-in',
      name: 'signIn',
      component: () => import('@/views/sign/SignIn.vue')
    }, {
      path: '/sign-on',
      name: 'signOn',
      component: () => import('@/views/sign/SignOn.vue')
    }, {
      path: '/sys',
      name: 'system',
      component: () => import('@/views/backstage/SystemMain.vue'),
      children: [
        ...moduleRoutes
      ]
    }
  ]
})