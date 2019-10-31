export default {

  queryByKey(key) {
    return window.axios.get(`/app/preArticleMsg/${key}`)
  },

  queryPage(params) {
    return window.axios.get('/app/preArticleMsg', { params })
  },

  add(params) {
    return window.axios.post('/app/preArticleMsg', params)
  },

  modify(params) {
    return window.axios.patch('/app/preArticleMsg', params)
  },

  delByKey(key) {
    return window.axios.delete(`/app/preArticleMsg/${key}`)
  },

  queryArticle(key) {
    return window.axios.get(`/app/preArticleMsg/detail/${key}`)
  },

  modifyPublishedArticle(msgId) {
    return window.axios.post(`/app/preArticleMsg/modifyPublishedArticle/${msgId}`)
  }
}
