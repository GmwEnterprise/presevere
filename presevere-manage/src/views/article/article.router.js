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
  }, {
    path: 'article/published',
    name: 'PublishedArticleList',
    meta: {
      title: '已发布文章',
      loginRequired: true
    },
    component: () => import('@/views/article/PublishedList.vue')
  }, {
    path: 'article/published/:url',
    name: 'Article',
    meta: {
      title: '文章',
      loginRequired: true
    },
    component: () => import('@/views/article/Article.vue')
  }
]