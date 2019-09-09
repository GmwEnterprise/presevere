export default {

  queryByKey(key) {
    return window.axios.get(`/app/sysRole/${key}`)
  },

  queryPage(params) {
    return window.axios.get('/app/sysRole', { params })
  },

  add(params) {
    return window.axios.post('/app/sysRole', params)
  },

  modify(params) {
    return window.axios.patch('/app/sysRole', params)
  },

  delByKey(key) {
    return window.axios.delete(`/app/sysRole/${key}`)
  }
}
