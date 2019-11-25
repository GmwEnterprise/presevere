export default [
  {
    path: 'article',
    name: 'articleList',
    meta: {
      loginRequired: true
    },
    component: () => import('@/views/article/List.vue')
  }, {
    path: 'article/edit',
    name: 'articleEdit',
    meta: {
      loginRequired: true
    },
    component: () => import('@/views/article/Edit.vue')
  }
]