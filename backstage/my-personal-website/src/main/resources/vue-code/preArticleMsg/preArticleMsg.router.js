export default [
  {
    path: 'modules/preArticleMsg',
    name: 'preArticleMsgList',
    meta: {
      auth: true
    },
    component: () => import('@/views/backstage/preArticleMsg/PreArticleMsgList.vue')
  }, {
    path: 'modules/preArticleMsg/edit',
    name: 'preArticleMsgEdit',
    meta: {
      auth: true
    },
    component: () => import('@/views/backstage/preArticleMsg/PreArticleMsgEdit.vue')
  }
]