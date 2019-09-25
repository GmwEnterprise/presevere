export default [
  {
    path: 'modules/sysRole',
    name: 'sysRoleList',
    component: () => import('@/views/backstage/sysRole/SysRoleList.vue')
  }, {
    path: 'modules/sysRole/edit',
    name: 'sysRoleEdit',
    component: () => import('@/views/backstage/sysRole/SysRoleEdit.vue')
  }
]