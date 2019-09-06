export default {
  /**
   * 校验手机号
   * @param {string} phone 
   */
  validPhone(phone) {
    return /^1\d{10}$/.test(phone)
  },

  /**
   * 校验邮箱
   * @param {string} email 
   */
  validEmail(email) {
    return /^[\w.-]+@([1-9]|[a-z]|[A-Z])+(\.[A-Za-z]{2,4}){1,2}$/.test(email)
  }
}