export default {

  queryByKey(key) {
    return window.axios.get(`/app/article/${key}`)
  },

  queryPage(params) {
    return window.axios.get('/app/article', { params })
  },

  add(params) {
    return window.axios.post('/app/article', params)
  },

  modify(params) {
    return window.axios.patch('/app/article', params)
  },

  delByKey(key) {
    return window.axios.delete(`/app/article/${key}`)
  }
}
