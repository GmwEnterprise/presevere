export default {

  queryByKey(key) {
    return window.axios.get(`/app/account/${key}`)
  },

  queryPage(params) {
    return window.axios.get('/app/account', { params })
  },

  add(params) {
    return window.axios.post('/app/account', params)
  },

  modify(params) {
    return window.axios.patch('/app/account', params)
  },

  delByKey(key) {
    return window.axios.delete(`/app/account/${key}`)
  }
}
