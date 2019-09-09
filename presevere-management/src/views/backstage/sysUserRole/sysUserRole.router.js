export default [
  {
    path: 'modules/sysUserRole',
    name: 'sysUserRoleList',
    component: () => import('@/views/backstage/sysUserRole/SysUserRoleList.vue')
  }, {
    path: 'modules/sysUserRole/edit',
    name: 'sysUserRoleEdit',
    component: () => import('@/views/backstage/sysUserRole/SysUserRoleEdit.vue')
  }
]