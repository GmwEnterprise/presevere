/* eslint-disable no-unused-vars */
'use strict'

import Vue from 'vue'
import axios from 'axios'
import router from '../router.js'
import { Notification, Message } from 'element-ui'

// Full config:  https://github.com/axios/axios#request-config
// axios.defaults.baseURL = process.env.baseURL || process.env.apiUrl || ''
// axios.defaults.headers.common['Authorization'] = AUTH_TOKEN
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'

let config = {
  // baseURL: process.env.baseURL || process.env.apiUrl || ''
  // timeout: 60 * 1000, // Timeout
  withCredentials: true, // Check cross-site Access-Control,
}

const _axios = axios.create(config)

_axios.interceptors.request.use(
  function (config) {
    // Do something before request is sent
    config.url = 'http://127.0.0.1:4399' + config.url
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = token
    }
    return config
  },
  function (error) {
    // Do something with request error
    return Promise.reject(error)
  }
)

// token是否处于刷新状态
let isTokenRefreshing = false
// 等待重试请求队列
let retryRequests = []

// Add a response interceptor
_axios.interceptors.response.use(
  function (response) {
    console.log(response)
    // Do something with response data
    /** 
     * 在这里返回Promise.reject会进入代码的catch块;
     * 返回Promise.resolve或正常数据会进入then块;
     * 不返回 = 返回undefined也会进入then块
     */
    // 成功
    if (response.data.code === 1) {
      return response.data
    }
    if (response.data.code === 2 || response.data.code === 4) {
      // 失败 || 无权访问
      Message({
        message: response.data.data || '网络繁忙！',
        type: 'error',
        center: true
      })
      // Notification.error({
      //   title: '错误',
      //   message: `${response.data.message}: ${response.data.data || '网络繁忙！'}`
      // })
      return Promise.reject(response.data)
    } else if (response.data.code === 3) {
      // 需要登录验证权限
      const redirectUrl = router.currentRoute.fullPath
      router.push({
        path: '/login',
        query: {
          redirectUrl
        }
      })
      // return Promise.reject(response.data)
    } else if (response.data.code === 5) {
      // 需要刷新token
      const userId = response.data.data
      if (isTokenRefreshing) {
        // 正在刷新，将当前请求添加进等待队列


      } else {
        // 未刷新，先将请求刷新token请求添加进等待队列，然后再将当前请求添加进等待队列，然后开始执行队列
        isTokenRefreshing = true
        return new Promise((resolve, reject) => {
          
        })
      }
    }
  },
  function (error) {
    console.log('Ajax系统错误')
    Notification.error({
      title: '系统错误',
      message: `${error || 'No message available'}`
    })
    return Promise.reject(error)
  }
)

Plugin.install = function (Vue, options) {
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
