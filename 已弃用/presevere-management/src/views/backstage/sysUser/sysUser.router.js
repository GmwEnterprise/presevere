export default [
  {
    path: 'modules/sysUser',
    name: 'sysUserList',
    meta: {
      auth: true
    },
    component: () => import('@/views/backstage/sysUser/SysUserList.vue')
  }, {
    path: 'modules/sysUser/edit',
    name: 'sysUserEdit',
    meta: {
      auth: true
    },
    component: () => import('@/views/backstage/sysUser/SysUserEdit.vue')
  }
]