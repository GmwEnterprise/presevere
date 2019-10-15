export default [
  {
    path: 'modules/preArticleBody',
    name: 'preArticleBodyList',
    meta: {
      auth: true
    },
    component: () => import('@/views/backstage/preArticleBody/PreArticleBodyList.vue')
  }, {
    path: 'modules/preArticleBody/edit',
    name: 'preArticleBodyEdit',
    meta: {
      auth: true
    },
    component: () => import('@/views/backstage/preArticleBody/PreArticleBodyEdit.vue')
  }
]