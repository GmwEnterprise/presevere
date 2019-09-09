export default {

  queryByKey(key) {
    return window.axios.get(`/app/sysUser/${key}`)
  },

  queryPage(params) {
    return window.axios.get('/app/sysUser', { params })
  },

  add(params) {
    return window.axios.post('/app/sysUser', params)
  },

  modify(params) {
    return window.axios.patch('/app/sysUser', params)
  },

  delByKey(key) {
    return window.axios.delete(`/app/sysUser/${key}`)
  }
}
