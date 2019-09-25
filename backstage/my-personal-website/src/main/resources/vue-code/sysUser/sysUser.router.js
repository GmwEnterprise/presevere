export default [
  {
    path: 'modules/sysUser',
    name: 'sysUserList',
    component: () => import('@/views/backstage/sysUser/SysUserList.vue')
  }, {
    path: 'modules/sysUser/edit',
    name: 'sysUserEdit',
    component: () => import('@/views/backstage/sysUser/SysUserEdit.vue')
  }
]