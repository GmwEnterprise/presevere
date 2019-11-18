export default {

  queryByKey(key) {
    return window.axios.get(`/app/sysRouter/${key}`)
  },

  queryPage(params) {
    return window.axios.get('/app/sysRouter', { params })
  },

  add(params) {
    return window.axios.post('/app/sysRouter', params)
  },

  modify(params) {
    return window.axios.patch('/app/sysRouter', params)
  },

  delByKey(key) {
    return window.axios.delete(`/app/sysRouter/${key}`)
  }
}
