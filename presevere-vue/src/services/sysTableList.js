export default {

  queryByKeyWithFieldMsg(key) {
    return window.axios.get(`/app/sysTableList/more/${key}`)
  },

  queryByKey(key) {
    return window.axios.get(`/app/sysTableList/${key}`)
  },

  queryPage(params) {
    return window.axios.get('/app/sysTableList', { params })
  },

  add(params) {
    return window.axios.post('/app/sysTableList', params)
  },

  modify(params) {
    return window.axios.patch('/app/sysTableList', params)
  },

  delByKey(key) {
    return window.axios.delete(`/app/sysTableList/${key}`)
  }
}
