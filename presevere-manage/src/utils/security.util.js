// import Crypto from 'crypto-js'
// const CryptoJS = require('crypto-js')

// const secretKey = 'abc'

// const password = 'ganmingwei123'

// let encoded = CryptoJS.AES.encrypt(password, secretKey).toString()

// let decoded = CryptoJS.AES.decrypt(encoded, secretKey).toString(CryptoJS.enc.Utf8)

// console.log(encoded)
// console.log(decoded)

import CryptoJS from 'crypto-js'

export function encode(password, salt) {
  // AES加密每次结果都不一样
  // return CryptoJS.AES.encrypt(password, salt).toString()
  return CryptoJS.MD5(salt + password).toString()
}

export function symmetricEncryptionEncode(origin) {
  // TODO 实现前后端的对称加密
  console.log('对称加密未实现')
  return origin
}

export function symmetricEncryptionDecode(encoded) {
  // TODO 实现前后端的对称解密
  console.log('对称解密未实现')
  return encoded
}
