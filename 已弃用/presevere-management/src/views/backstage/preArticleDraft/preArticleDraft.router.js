export default [
  {
    path: 'modules/preArticleDraft',
    name: 'preArticleDraftList',
    meta: {
      auth: true
    },
    component: () => import('@/views/backstage/preArticleDraft/PreArticleDraftList.vue')
  }, {
    path: 'modules/preArticleDraft/edit',
    name: 'preArticleDraftEdit',
    meta: {
      auth: true
    },
    component: () => import('@/views/backstage/preArticleDraft/PreArticleDraftEdit.vue')
  }
]