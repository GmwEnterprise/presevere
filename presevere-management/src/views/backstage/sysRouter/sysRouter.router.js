export default [
  {
    path: 'modules/sysRouter',
    name: 'sysRouterList',
    meta: {
      auth: true
    },
    component: () => import('@/views/backstage/sysRouter/SysRouterList.vue')
  }, {
    path: 'modules/sysRouter/edit',
    name: 'sysRouterEdit',
    meta: {
      auth: true
    },
    component: () => import('@/views/backstage/sysRouter/SysRouterEdit.vue')
  }
]