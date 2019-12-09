import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'Home',
      component: () => import('@/views/Home.vue')
    }, {
      path: '/archives',
      name: 'Archives',
      component: () => import('@/views/Archives.vue')
    }, {
      path: '/catalog',
      name: 'Catalog',
      component: () => import('@/views/Catalog.vue')
    }, {
      path: '/about',
      name: 'About',
      component: () => import('@/views/About.vue')
    }, {
      path: '/post/:urlNumber',
      name: 'Post',
      component: () => import('@/views/Article.vue')
    }
  ]
})
