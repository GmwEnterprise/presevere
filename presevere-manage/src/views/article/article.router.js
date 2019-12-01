export default [
  {
    path: 'article',
    name: 'articleList',
    meta: {
      loginRequired: true,
      title: '文章列表'
    },
    component: () => import('@/views/article/ArticleList.vue')
  }, {
    path: 'article/edit',
    name: 'articleEdit',
    meta: {
      title: '文章编辑',
      loginRequired: true
    },
    component: () => import('@/views/article/ArticleEdit.vue')
  }
]