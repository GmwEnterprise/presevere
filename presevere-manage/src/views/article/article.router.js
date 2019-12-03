export default [
  {
    path: 'article/write',
    name: 'WriteArticle',
    meta: {
      title: '写文章',
      loginRequired: true
    },
    component: () => import('@/views/article/WriteArticle.vue')
  }, {
    path: 'article/write/:draftId',
    name: 'WriteArticleWithDraftId',
    meta: {
      title: '写文章',
      loginRequired: true
    },
    component: () => import('@/views/article/WriteArticle.vue')
  }, {
    path: 'article/drafts',
    name: 'ArticleDrafts',
    meta: {
      title: '文章草稿',
      loginRequired: true
    },
    component: () => import('@/views/article/ArticleDrafts.vue')
  },
]