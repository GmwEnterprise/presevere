// css
import '@/assets/css.import.js'

// sys
import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import store from './store'

// bootstrap
import 'bootstrap'

Vue.config.productionTip = false

import '@/components/index.js'

// toast全局命令
Vue.prototype.$toast = {
  success(message = 'success message', title = '成功提示', duration = 3000) {
    store.commit('toast', {
      message,
      type: 'success',
      title,
      duration
    })
  },
  warning(message = 'warning message', title = '警告提示', duration = 3000) {
    store.commit('toast', {
      message,
      type: 'warning',
      title,
      duration
    })
  },
  error(message = 'error message', title = '错误提示', duration = 3000) {
    store.commit('toast', {
      message,
      type: 'danger',
      title,
      duration
    })
  },
  info(message = 'info message', title = '消息提示', duration = 3000) {
    store.commit('toast', {
      message,
      type: 'success',
      title,
      duration
    })
  }
}

// messageBox全局命令
Vue.prototype.$message = config => {
  store.commit('message', config)
}

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

// 手动挂载toast插件
import Toast from '@/components/Toast.vue'
let ToastConstructor = Vue.extend(Toast)
let toastInstance = new ToastConstructor({
  store
}).$mount()
document.body.appendChild(toastInstance.$el)

// 手动挂载messagebox插件
import MessageBox from '@/components/MessageBox.vue'
let MessageBoxConstructor = Vue.extend(MessageBox)
let messageBoxInstance = new MessageBoxConstructor({
  store
}).$mount()
document.body.appendChild(messageBoxInstance.$el)
