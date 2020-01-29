import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import VueMaterial from 'vue-material'
import 'vue-material/dist/vue-material.min.css'

Vue.use(VueMaterial)

Vue.material = {
  // activeness of ripple effect
  ripple: true,

  theming: {},
  locale: {
    // range for datepicker
    startYear: 1900,
    endYear: 2099,

    // date format for date picker
    dateFormat: 'yyyy-MM-dd',

    // i18n strings
    days: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
    shortDays: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
    shorterDays: ['S', 'M', 'T', 'W', 'T', 'F', 'S'],
    months: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
    shortMonths: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'June', 'July', 'Aug', 'Sept', 'Oct', 'Nov', 'Dec'],
    shorterMonths: ['J', 'F', 'M', 'A', 'M', 'Ju', 'Ju', 'A', 'Se', 'O', 'N', 'D'],

    // `0` stand for Sunday, `1` stand for Monday
    firstDayOfAWeek: 0
  }
}

Vue.config.productionTip = false

export default function createApp() {
  const app = {
    router,
    store,
    head: {},
    render: h => h(App)
  }
  return app
}
