/* eslint-disable no-unused-vars */
import Vue from 'vue'
import ProgressComp from './Progress.vue'

Vue.use({
  install: function (vue, options) {
    /**
     * vue.func 在组件中只能通过Vue.func调用[全局方法]
     * vue.prototype.func 则能通过this.func调用[实例方法]
     */
    // console.log('Progress plugin init...')

    // 创建progress组件实例
    const ProgressConstructor = Vue.extend(ProgressComp)
    const progressObj = new ProgressConstructor()

    // 挂载实例
    document.body.appendChild(progressObj.$mount().$el)

    // vue实例方法
    vue.prototype.startLoading = () => progressObj.start()
    vue.prototype.stopLoading = () => progressObj.stop()
  }
})