export default {
  set(key, val) {
    localStorage.setItem(key, JSON.stringify(val))
  },

  get(key) {
    return JSON.parse(localStorage.getItem(key))
  },

  exist(key) {
    return localStorage.getItem(key) !== null
  },

  remove(key) {
    localStorage.removeItem(key)
  }
}