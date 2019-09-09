export default [
  {
    path: 'modules/sysRouter',
    name: 'sysRouterList',
    component: () => import('@/views/backstage/sysRouter/SysRouterList.vue')
  }, {
    path: 'modules/sysRouter/edit',
    name: 'sysRouterEdit',
    component: () => import('@/views/backstage/sysRouter/SysRouterEdit.vue')
  }
]