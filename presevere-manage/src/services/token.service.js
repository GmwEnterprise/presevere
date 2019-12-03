function setToken(token, userId = null) {
  localStorage.setItem('token', token)
  if (userId) {
    localStorage.setItem('currentUserId', userId)
  }
}

function currentUserId() {
  return localStorage.getItem('currentUserId')
}

function getToken() {
  return localStorage.getItem('token')
}

function exists() {
  return localStorage.getItem('token') !== null && localStorage.getItem('currentUserId') !== null
}

function removeToken() {
  localStorage.removeItem('currentUserId')
  localStorage.removeItem('token')
}

export default {
  setToken, currentUserId, getToken, exists, removeToken
}