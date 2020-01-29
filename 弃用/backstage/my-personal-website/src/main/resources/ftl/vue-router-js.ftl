export default [
  {
    path: 'modules/${entityAlias}',
    name: '${entityAlias}List',
    meta: {
      auth: true
    },
    component: () => import('@/views/backstage/${entityAlias}/${entityName}List.vue')
  }, {
    path: 'modules/${entityAlias}/edit',
    name: '${entityAlias}Edit',
    meta: {
      auth: true
    },
    component: () => import('@/views/backstage/${entityAlias}/${entityName}Edit.vue')
  }
]