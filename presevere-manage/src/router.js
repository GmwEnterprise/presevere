import Vue from 'vue'
import Router from 'vue-router'
import store from './store'
import _ from 'lodash'

Vue.use(Router)

const subRoutes = (() => {
  const context = require.context('./views/', true, /\.router\.js$/)
  const routes = context.keys().map(key => context(key).default)
  return _.flatten(routes)
})()

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    { path: '/', redirect: '/home' }, {
      path: '/home',
      component: () => import('@/views/Home.vue'),
      meta: {
        title: '主页',
        loginRequired: true,
      },
      children: [
        ...subRoutes
      ]
    }, {
      path: '/login',
      component: () => import('@/views/sign/Login.vue'),
      meta: {
        title: '登录页'
      }
    }, {
      path: '/register',
      component: () => import('@/views/sign/Register.vue'),
      meta: {
        title: '注册页'
      }
    }, {
      path: '/demo',
      component: () => import('@/demo/Demo.vue'),
      meta: {
        title: '测试页'
      }
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
    store.commit('setRouteTitle', to.meta.title || to.fullPath)
    next()
  }
})

const originalPush = router.push

Router.prototype.push = function (location) {
  originalPush.call(this, location).catch(err => err)
}

export default router