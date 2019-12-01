import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    currentRouteTitle: ''
  },
  mutations: {
    setRouteTitle(state, title) {
      state.currentRouteTitle = title
    }
  },
  actions: {
  }
})
