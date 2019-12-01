export default [
  {
    path: 'user/modify',
    name: 'userModify',
    meta: {
      loginRequired: true,
      title: '个人信息修改'
    },
    component: () => import('@/views/user/UserMessage.vue')
  }, {
    path: 'user/list',
    name: 'userList',
    meta: {
      title: '用户列表',
      loginRequired: true
    },
    component: () => import('@/views/user/UserList.vue')
  }
]