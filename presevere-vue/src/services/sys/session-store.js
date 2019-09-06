export default {
  set(key, val) {
    sessionStorage.setItem(key, JSON.stringify(val))
  },

  get(key) {
    return JSON.parse(sessionStorage.getItem(key))
  },

  exist(key) {
    return sessionStorage.getItem(key) !== null
  },

  remove(key) {
    sessionStorage.removeItem(key)
  }
}