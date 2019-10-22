export default {

  queryByKey(key) {
    return window.axios.get(`/app/preArticleDraft/${key}`)
  },

  queryPage(params) {
    return window.axios.get('/app/preArticleDraft', { params })
  },

  addAndReturn(params) {
    return window.axios.post('/app/preArticleDraft/addAndReturn', params)
  },

  add(params) {
    return window.axios.post('/app/preArticleDraft', params)
  },

  modify(params) {
    return window.axios.patch('/app/preArticleDraft', params)
  },

  delByKey(key) {
    return window.axios.delete(`/app/preArticleDraft/${key}`)
  },

  saveIntroduction(param) {
    if (param.introduction && param.introduction.trim().length > 0) {
      if (param.id) {
        return this.modify({
          id: param.id,
          introduction: param.introduction
        })
      } else {
        return this.addAndReturn({
          id: param.id,
          introduction: param.introduction,
          contentType: 'markdown'
        })
      }
    }
  },

  saveTitle(param) {
    if (!param.title || param.title.trim().length < 1) {
      return
    }
    if (param.id) {
      // modify
      return this.modify({
        id: param.id,
        title: param.title
      })
    } else {
      // insert
      return this.addAndReturn({
        id: param.id,
        title: param.title,
        contentType: 'markdown'
      })
    }
  },
}
