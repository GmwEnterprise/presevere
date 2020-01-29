export default {

  queryByKey(key) {
    return window.axios.get(`/app/preArticleBody/${key}`)
  },

  queryPage(params) {
    return window.axios.get('/app/preArticleBody', { params })
  },

  add(params) {
    return window.axios.post('/app/preArticleBody', params)
  },

  modify(params) {
    return window.axios.patch('/app/preArticleBody', params)
  },

  delByKey(key) {
    return window.axios.delete(`/app/preArticleBody/${key}`)
  }
}
