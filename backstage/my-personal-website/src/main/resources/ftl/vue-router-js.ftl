export default [
  {
    path: 'modules/${entityAlias}',
    name: '${entityAlias}List',
    component: () => import('@/views/backstage/${entityAlias}/${entityName}List.vue')
  }, {
    path: 'modules/${entityAlias}/edit',
    name: '${entityAlias}Edit',
    component: () => import('@/views/backstage/${entityAlias}/${entityName}Edit.vue')
  }
]