/* eslint-disable no-unused-vars */
'use strict'

import Vue from 'vue'
import axios from 'axios'
import router from '../router.js'
import { Notification, Message } from 'element-ui'

import tokenService from '@/services/token.service.js'

// Full config:  https://github.com/axios/axios#request-config
// axios.defaults.baseURL = process.env.baseURL || process.env.apiUrl || ''
// axios.defaults.headers.common['Authorization'] = AUTH_TOKEN
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'

let config = {
  // baseURL: process.env.baseURL || process.env.apiUrl || ''
  baseURL: process.env.VUE_APP_API_URL,
  // timeout: 60 * 1000, // Timeout
  // withCredentials: true, // Check cross-site Access-Control,
}

const _axios = axios.create(config)

const API_PREFIX = '/HTTP_API_PREFIX'

_axios.interceptors.request.use(
  function (config) {
    // Do something before request is sent
    // if (!config.url.includes(API_PREFIX)) {
    //   config.url = API_PREFIX + config.url
    // }
    const token = tokenService.getToken()
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

let refreshTokenFlag = false
let requestList = []

function executeRequests(index) {
  if (index < requestList.length) {
    requestList[index]().then(() => {
      executeRequests(index + 1)
    })
  } else {
    refreshTokenFlag = false
    requestList.splice(0, requestList.length)
  }
}

// Add a response interceptor
_axios.interceptors.response.use(
  function (response) {
    // // console.log(` >>> 拦截器开始`)
    // // console.log(response.config.url)
    // // console.log(response.config.headers.Authorization)
    // // console.log(response.data)
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
      return Promise.reject(response.data)
    } else if (response.data.code === 5) {
      // 需要刷新token
      const userId = response.data.data
      if (refreshTokenFlag) {
        // 正在刷新
        return new Promise((resolve, reject) => {
          requestList.push(async function () {
            try {
              const r = await _axios(response.config)
              resolve(r)
            } catch (err) {
              reject(err)
            }
          })
        })
      } else {
        // 未刷新
        refreshTokenFlag = true
        return new Promise((resolve, reject) => {
          requestList.push(async function () {
            try {
              const result = await _axios.post(`/sign/refreshToken/${userId}`)
              tokenService.setToken(result.data)
              const r = await _axios(response.config)
              resolve(r)
            } catch (err) {
              reject(err)
            }
          })
          executeRequests(0)
        })
      }
    }
  },
  function (error) {
    // // console.log('Ajax系统错误')
    // // console.log(arguments)
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
