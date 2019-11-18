export default [
  {
    path: 'modules/sysRole',
    name: 'sysRoleList',
    meta: {
      auth: true
    },
    component: () => import('@/views/backstage/sysRole/SysRoleList.vue')
  }, {
    path: 'modules/sysRole/edit',
    name: 'sysRoleEdit',
    meta: {
      auth: true
    },
    component: () => import('@/views/backstage/sysRole/SysRoleEdit.vue')
  }
]