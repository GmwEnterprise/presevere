<template>
  <div class="customize-datetime-picker" :style="`width: ${width};`">
    <input
      v-model="displayValue"
      class="form-control"
      type="text"
      placeholder="请选择日期"
      @click="togglePicker()"
    />
    <div class="c-d-p-wrapper" v-show="showPicker">
      <div class="c-d-p-title">
        <span class="c-d-p-btn" @click="prev()">
          <i class="fa fa-caret-left" aria-hidden="true"></i>
        </span>
        <span
          @click="monthModel()"
          style="display: inline-block;width: 75%;text-align: center; cursor: pointer;"
        >
          <template v-if="displayModel === 'day'">{{ `${temp.year} / ${temp.month}` }}</template>
          <template v-else>{{ `${temp.year}` }}</template>
        </span>
        <span class="c-d-p-btn" @click="next()">
          <i class="fa fa-caret-right" aria-hidden="true"></i>
        </span>
      </div>
      <table v-show="displayModel === 'day'" class="c-d-p-content">
        <thead>
          <tr>
            <th>Su</th>
            <th>Mo</th>
            <th>Tu</th>
            <th>We</th>
            <th>Th</th>
            <th>Fr</th>
            <th>Sa</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(row, rowId) of currentDisplay" :key="rowId">
            <td v-for="(item, itemId) of row" :key="itemId">
              <div
                class="c-d-p-calendar-item"
                @click="emitValueSubmit(currentLdt.year, currentLdt.month, item.dayOfMonth)"
              >{{ item.dayOfMonth }}</div>
            </td>
          </tr>
          <tr>
            <td colspan="7">
              <a
                class="choose-today"
                href="javascript:void(0)"
                @click="emitValueSubmit(today.getFullYear(), today.getMonth() + 1, today.getDate())"
              >Choose today.</a>
            </td>
          </tr>
        </tbody>
      </table>
      <div v-show="displayModel === 'month'" class="c-d-p-month-choose">
        <span v-for="month of 12" :key="month" @click="setMonth(month)">{{ month }}</span>
      </div>
    </div>
  </div>
</template>

<script>
import LocalDateTime from '@/tools/local-datetime.js'
import DateTimeFormat from '@/tools/datetime-format.js'
export default {
  name: 'CustomizeDateTimePicker',
  data() {
    return {
      today: new Date(),
      ldt: null,
      currentLdt: {},
      currentDisplay: [],
      displayModel: 'day',
      temp: {
        year: 0,
        month: 0
      },
      showPicker: false,
      displayValue: ''
    }
  },
  props: {
    year: Number,
    month: Number,
    value: String,
    width: String
  },
  watch: {
    year() {
      this.setLdt(this.year, this.temp.month)
    },
    month() {
      this.setLdt(this.temp.year, this.month)
    },
    currentLdt() {
      const row = []
      let line = []
      for (let i = 0, week = 0; i < this.currentLdt.day.length; i++, week++) {
        if (week === 7) {
          week = 0
        }
        if (week === this.currentLdt.day[i].ofWeek) {
          line.push({
            dayOfMonth: this.currentLdt.day[i].ofMonth,
            dayOfWeek: this.currentLdt.day[i].ofWeek
          })
        } else {
          line.push({})
          i--
        }
        if (week === 6) {
          row.push(line)
          line = []
        } else if (i === this.currentLdt.day.length - 1) {
          for (let a = 0; a < 6 - week; a++) {
            line.push({})
          }
          row.push(line)
        }
      }
      this.currentDisplay = row
    }
  },
  methods: {
    dateInit() {
      this.currentLdt = this.ldt.getCurrentCalendar()
      this.temp.year = this.currentLdt.year
      this.temp.month = this.currentLdt.month
    },
    prev() {
      if (this.displayModel === 'day') {
        this.ldt.prevMonth()
        this.dateInit()
      } else if (this.displayModel === 'month') {
        this.temp.year--
      }
    },
    next() {
      if (this.displayModel === 'day') {
        this.ldt.nextMonth()
        this.dateInit()
      } else if (this.displayModel === 'month') {
        this.temp.year++
      }
    },
    setMonth(monthValue) {
      this.temp.month = monthValue
      this.setLdt(this.temp.year, this.temp.month)
    },
    setLdt(year, month) {
      this.ldt = new LocalDateTime(year, month)
      this.dateInit()
      this.displayModel = 'day'
    },
    monthModel() {
      this.displayModel = 'month'
    },
    togglePicker() {
      this.showPicker = !this.showPicker
    },
    emitValueSubmit(year, month, dayOfMonth) {
      this.displayValue = `${year}/${month}/${dayOfMonth}`
      this.togglePicker()
      this.$emit('input', DateTimeFormat.date2String(year, month, dayOfMonth))
    }
  },
  mounted() {
    this.ldt = new LocalDateTime(this.year, this.month)
    this.dateInit()
  }
}
</script>

<style>
.customize-datetime-picker {
  position: relative;
  height: auto;
  border-radius: 5px;
  z-index: 100;
}
.c-d-p-wrapper {
  position: absolute;
  box-shadow: 0px 0px 10px 0px #cecece;
  left: 0;
  width: 256px;
}
.c-d-p-title {
  display: flex;
  justify-content: center;
  height: 2em;
  line-height: 2em;
  padding: 10px;
  box-sizing: content-box;
  background-color: lightgray;
}
.c-d-p-btn {
  width: 10%;
  text-align: center;
  cursor: pointer;
}
.c-d-p-content {
  width: 96%;
  margin: 0.3rem auto;
  text-align: center;
  line-height: 2em;
  color: #4e4e4e;
}
.c-d-p-calendar-item {
  transition: 0.2s;
  cursor: pointer;
}
.c-d-p-calendar-item:hover {
  color: white;
  background-color: lightgray;
}
.c-d-p-month-choose {
  width: 90%;
  margin: 0.8rem auto 0.6rem;
}
.c-d-p-month-choose > span {
  display: inline-block;
  width: 25%;
  box-sizing: border-box;
  height: 4rem;
  line-height: 4rem;
  text-align: center;
  cursor: pointer;
  transition: 0.2s;
}
.c-d-p-month-choose > span:hover {
  color: white;
  background-color: lightgray;
}
a.choose-today:hover {
  text-decoration: none;
}
</style>