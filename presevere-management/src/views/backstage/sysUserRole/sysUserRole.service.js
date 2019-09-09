export default {

  queryByKey(key) {
    return window.axios.get(`/app/sysUserRole/${key}`)
  },

  queryPage(params) {
    return window.axios.get('/app/sysUserRole', { params })
  },

  add(params) {
    return window.axios.post('/app/sysUserRole', params)
  },

  modify(params) {
    return window.axios.patch('/app/sysUserRole', params)
  },

  delByKey(key) {
    return window.axios.delete(`/app/sysUserRole/${key}`)
  }
}
