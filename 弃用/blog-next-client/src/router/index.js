import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: () => import('@/views/Home.vue')
  }, {
    path: '/archives',
    name: 'archives',
    component: () => import('@/views/Archives.vue')
  }, {
    path: '/topics',
    name: 'topics',
    component: () => import('@/views/Topics.vue')
  }, {
    path: '/contact',
    name: 'contact',
    component: () => import('@/views/Contact.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
