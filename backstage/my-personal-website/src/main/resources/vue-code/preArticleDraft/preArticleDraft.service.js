export default {

  queryByKey(key) {
    return window.axios.get(`/app/preArticleDraft/${key}`)
  },

  queryPage(params) {
    return window.axios.get('/app/preArticleDraft', { params })
  },

  add(params) {
    return window.axios.post('/app/preArticleDraft', params)
  },

  modify(params) {
    return window.axios.patch('/app/preArticleDraft', params)
  },

  delByKey(key) {
    return window.axios.delete(`/app/preArticleDraft/${key}`)
  }
}
