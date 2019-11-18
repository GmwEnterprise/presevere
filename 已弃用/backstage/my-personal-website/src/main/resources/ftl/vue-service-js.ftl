export default {

  queryByKey(key) {
    ${'return window.axios.get(`/app/${entityAlias}/$' + '{key}`)'}
  },

  queryPage(params) {
    return window.axios.get('/app/${entityAlias}', { params })
  },

  add(params) {
    return window.axios.post('/app/${entityAlias}', params)
  },

  modify(params) {
    return window.axios.patch('/app/${entityAlias}', params)
  },

  delByKey(key) {
    ${'return window.axios.delete(`/app/${entityAlias}/$' + '{key}`)'}
  }
}
