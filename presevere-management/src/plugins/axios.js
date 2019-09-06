'use strict'

import Vue from 'vue'
import axios from 'axios'

// Full config:  https://github.com/axios/axios#request-config
// axios.defaults.baseURL = process.env.baseURL || process.env.apiUrl || ''
// axios.defaults.headers.common['Authorization'] = AUTH_TOKEN
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'

let config = {
  // baseURL: process.env.baseURL || process.env.apiUrl || ''
  timeout: 60 * 1000, // Timeout
  // withCredentials: true, // Check cross-site Access-Control
}

const _axios = axios.create(config)

_axios.interceptors.request.use(
  function (config) {
    // Do something before request is sent
    return config
  },
  function (error) {
    // Do something with request error
    return Promise.reject(error)
  }
)

// Add a response interceptor
_axios.interceptors.response.use(
  function (response) {
    // Do something with response data
    console.log('进入axios response拦截器')
    // eslint-disable-next-line no-unused-vars
    return new Promise((resolve, reject) => {
      if (response.data.code === 0) {
        // 请求成功
        resolve(response.data)
      } else {
        console.log('错误信息(code !== 0): ' + response.data.msg)
        reject(response.data)
      }
    }) //.catch(errorMsg => console.log('错误信息(code !== 0): ' + errorMsg))
  },
  function (error) {
    // Do something with response error
    console.error('进入axios response拦截器 [系统报错, httpStatus !== 200]')
    return Promise.reject(error)
  }
)

Plugin.install = function (Vue) {
  Vue.axios = _axios
  window.axios = _axios
  Object.defineProperties(Vue.prototype, {
    axios: {
      get() {
        return _axios
      }
    },
    $axios: {
      get() {
        return _axios
      }
    },
  })
}

Vue.use(Plugin)

export default Plugin
