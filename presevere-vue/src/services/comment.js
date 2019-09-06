export default {

  queryByKey(key) {
    return window.axios.get(`/app/comment/${key}`)
  },

  queryPage(params) {
    return window.axios.get('/app/comment', { params })
  },

  add(params) {
    return window.axios.post('/app/comment', params)
  },

  modify(params) {
    return window.axios.patch('/app/comment', params)
  },

  delByKey(key) {
    return window.axios.delete(`/app/comment/${key}`)
  }
}
