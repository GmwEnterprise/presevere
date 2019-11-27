// import Crypto from 'crypto-js'
// const CryptoJS = require('crypto-js')

// const secretKey = 'abc'

// const password = 'ganmingwei123'

// let encoded = CryptoJS.AES.encrypt(password, secretKey).toString()

// let decoded = CryptoJS.AES.decrypt(encoded, secretKey).toString(CryptoJS.enc.Utf8)

// console.log(encoded)
// console.log(decoded)

import CryptoJS from 'crypto-js'

function encode(password, salt) {
  // AES加密每次结果都不一样
  // return CryptoJS.AES.encrypt(password, salt).toString()
  return CryptoJS.MD5(salt + password).toString()
}

// function decode(encodedPassword, salt) {
//   return CryptoJS.AES.decrypt(encodedPassword, salt).toString(CryptoJS.enc.Utf8)
// }

export default {
  encode //, decode
}