import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/admin/:key',
    component: () => import('@/views/admin/Admin.vue')
  }, {
    path: '/',
    name: 'home',
    component: () => import('@/views/home/Home.vue')
  }, {
    path: '/post/:postId',
    name: 'post',
    component: () => import('@/views/post/Post.vue')
  }, {
    path: '/archives',
    name: 'archives',
    component: () => import('@/views/archives/Archives.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
