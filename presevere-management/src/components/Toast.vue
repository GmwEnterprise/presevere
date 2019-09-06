<template>
  <div id="toast-wrapper" ref="toast-wrapper"></div>
</template>

<script>
import { setTimeout } from 'timers'
export default {
  name: 'CustomizeToast',
  computed: {
    toastObj() {
      return this.$store.state.toast
    }
  },
  watch: {
    toastObj() {
      switch (this.toastObj.type) {
        case 'info':
          this.infoToast(this.toastObj)
          break
        case 'danger':
          this.dangerToast(this.toastObj)
          break
        case 'warning':
          this.warningToast(this.toastObj)
          break
        case 'success':
          this.successToast(this.toastObj)
          break
        default:
          this.infoToast(this.toastObj)
      }
    }
  },
  methods: {
    successToast(obj) {
      this.toast(
        `
        <div
          class="custom-toast-header"
          style="color: #0b690b;background-color: #bee2d4;"
        >${obj.title}</div>
        <div
          class="custom-toast-body"
          style="background-color: #eaf7ea;color: #2d5f2d;"
        >${obj.message}</div>`,
        '1px solid rgba(126, 181, 127, 0.42)',
        obj.duration
      )
    },
    warningToast(obj) {
      this.toast(
        `
        <div
          class="custom-toast-header"
          style="color: #948934;background-color: #fff7c4;"
        >${obj.title}</div>
        <div
          class="custom-toast-body"
          style="background-color: #ffffeb;color: #adad1d;"
        >${obj.message}</div>`,
        '1px solid rgba(253, 227, 149, 0.5)',
        obj.duration
      )
    },
    dangerToast(obj) {
      this.toast(
        `
        <div
          class="custom-toast-header"
          style="color: #770000;background-color: #ffcfcf;"
        >${obj.title}</div>
        <div
          class="custom-toast-body"
          style="background-color: #ffe5e5;color: #8c0000;"
        >${obj.message}</div>`,
        '1px solid rgba(255, 172, 172, 0.5)',
        obj.duration
      )
    },
    infoToast(obj) {
      this.toast(
        `
        <div
          class="custom-toast-header"
          style="color: white;background-color: #71d2ff;"
        >${obj.title}</div>
        <div
          class="custom-toast-body"
          style="background-color: #fff;color: #333;"
        >${obj.message}</div>`,
        '1px solid rgba(131, 216, 255, 0.5)',
        obj.duration
      )
    },
    toast(value, borderStyle, duration) {
      const dom = document.getElementById('toast-wrapper')
      const divElement = document.createElement('div')
      divElement.innerHTML = value
      divElement.style.border = borderStyle
      divElement.classList.add(
        'custom-toast',
        'animated',
        'bounceInRight',
        'fast'
      )
      dom.appendChild(divElement)
      setTimeout(() => {
        divElement.classList.add('bounceOutRight')
        setTimeout(() => dom.removeChild(divElement), 800)
      }, duration)
    }
  }
}
</script>

<style>
#toast-wrapper {
  position: fixed;
  top: 0;
  right: 0;
  width: 16rem;
  height: auto;
  min-height: 5rem;
  background-color: rgba(0, 0, 0, 0);
}
.custom-toast {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.3);
  border-radius: 0.25rem;
  overflow: hidden;
  box-sizing: border-box;
  height: auto;
  width: 15rem;
  background-color: rgb(255, 255, 255);
  margin-top: 1rem;
}
.custom-toast-header {
  font-size: 0.9em;
  line-height: 1.8rem;
  font-weight: bold;
  padding: 0.2rem 0.7rem;
}
.custom-toast-body {
  padding: 0.7rem;
  font-size: 0.85em;
}
</style>