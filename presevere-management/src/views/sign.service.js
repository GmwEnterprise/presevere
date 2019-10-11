import qs from 'qs'

/**
 * 登录
 * @param {String} username 
 * @param {String} password 
 */
function login(username, password) {
  return window.axios.post('/app/login', qs.stringify({
    username,
    password
  }))
}

/**
 * 注册
 * @param {String} username 
 * @param {String} password 
 * @param {String} nickname 
 */
function register(username, password, nickname) {
  return window.axios.post('/app/auth/register', {
    username, password, nickname
  })
}

export default {
  login, register
}