export default class LocalDateTime {
  /**
   * 构造函数
   * @param {number} year 
   * @param {number} month 
   * @param {number} dayOfMonth 
   * @param {number} hour 
   * @param {number} minute 
   * @param {number} second 
   */
  constructor(year, month, dayOfMonth, hour, minute, second) {
    const def = new Date()
    this.currentDate = {
      year: year || def.getFullYear(),
      month: month || def.getMonth() + 1,
      dayOfMonth: dayOfMonth || 1,
      hour: hour || 0,
      minute: minute || 0,
      second: second || 0
    }
  }

  getCurrentCalendar() {
    const year = this.currentDate.year
    const month = this.currentDate.month
    let ofWeek = new Date(year, month - 1, 1).getDay()
    const countDayOfMonth = this.getCountDayOfMonth(year, month)
    if (!countDayOfMonth) {
      throw new Error('the month value is wrong !')
    }
    const value = {
      year,
      month,
      day: []
    }
    for (let ofMonth = 1; ofMonth <= countDayOfMonth; ofMonth++) {
      value.day.push({
        ofMonth,
        ofWeek
      })
      if (ofWeek === 6) {
        ofWeek = 0
      } else {
        ofWeek++
      }
    }
    return value
  }

  prevMonth() {
    if (this.currentDate.month === 1) {
      this.currentDate.month = 12
      this.currentDate.year--
    } else {
      this.currentDate.month--
    }
    this.currentDate.dayOfMonth = null
  }

  nextMonth() {
    if (this.currentDate.month === 12) {
      this.currentDate.month = 1
      this.currentDate.year++
    } else {
      this.currentDate.month++
    }
    this.currentDate.dayOfMonth = null
  }

  getCountDayOfMonth(year, month) {
    switch (month) {
      case 1:
      case 3:
      case 5:
      case 7:
      case 8:
      case 10:
      case 12:
        return 31
      case 4:
      case 6:
      case 9:
      case 11:
        return 30
      case 2:
        if (year % (year % 100 ? 4 : 400)) {
          return 28
        } else {
          return 29
        }
      default:
        return 0
    }
  }
}