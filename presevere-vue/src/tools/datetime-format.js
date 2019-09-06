/**
 * 
 * @param {number} year 
 * @param {number} month 
 * @param {number} dayOfMonth 
 */
function date2String(year, month, dayOfMonth) {
  return `${year}-${(month + '').padStart(2, '0')}-${(dayOfMonth + '').padStart(2, '0')}`
}

export default {
  date2String
}