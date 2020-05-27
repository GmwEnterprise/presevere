import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/admin',
    component: () => import('../views/system-wrapper.vue'),
    children: [
      {
        path: '/',
        name: '主页',
        component: () => import('../views/welcome.vue')
      },
      {
        path: 'products',
        name: '产品列表',
        component: () => import('../views/product-list.vue')
      },
      {
        path: 'product/edit/:productId',
        name: '修改产品信息',
        component: () => import('../views/product-edit.vue')
      },
      {
        path: 'product/new',
        name: '新增产品信息',
        component: () => import('../views/product-edit.vue')
      },
      {
        path: 'product/:productId',
        name: '产品详细信息',
        component: () => import('../views/product-detail.vue')
      },
      {
        path: 'users',
        name: '管理员列表',
        component: () => import('../views/user-management.vue')
      }
    ]
  },
  {
    path: '/',
    redirect: '/admin'
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
