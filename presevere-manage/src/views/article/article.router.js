export default [
  {
    path: 'article/write',
    name: 'WriteArticle',
    meta: {
      title: '写文章',
      loginRequired: true
    },
    component: () => import('@/views/article/WriteArticle.vue')
  }
]