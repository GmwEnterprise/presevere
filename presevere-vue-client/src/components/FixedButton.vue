<template>
  <transition name="el-fade-in">
    <div v-show="visible" class="fixed-button" :style="style" @click="click">
      <slot></slot>
    </div>
  </transition>
</template>

<script>
export default {
  name: 'FixedButton',
  props: {
    bottom: {
      type: Number,
      required: false,
      default: 20
    },
    right: {
      type: Number,
      required: false,
      default: 20
    },
    visibilityHeight: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      visible: true,
      container: null
    }
  },
  computed: {
    style() {
      return {
        bottom: this.bottom + 'px',
        right: this.right + 'px'
      }
    }
  },
  mounted() {
    this.visible = this.visibilityHeight <= 0
    if (!this.visible) {
      const vue = this
      vue.onScroll = function(e) {
        // console.log(e)
        const scrollTop = e.target.scrollingElement.scrollTop
        // console.log(scrollTop)
        vue.visible = scrollTop >= vue.visibilityHeight
      }
      document.addEventListener('scroll', vue.onScroll)
    }
  },
  beforeDestroy() {
    document.removeEventListener('scroll', this.onScroll)
  },
  methods: {
    click() {
      if (this.visibilityHeight <= 0) {
        this.$emit('click')
      } else {
        if (!document.documentElement.scrollTop) {
          alert('该浏览器无法使用此按钮, 请使用Chrome浏览器.')
        }
        // 设置了可见高度，则click时间由组件本身使用，不向外传递
        const beginTime = Date.now()
        const rAF = window.requestAnimationFrame || (func => setTimeout(func, 16))
        const el = document.documentElement
        const beginValue = el.scrollTop
        // const easeInOutCubic = value => value < 0.5
        const frameFunc = () => {
          const progress = (Date.now() - beginTime) / 400
          if (progress < 1) {
            el.scrollTop = beginValue * (1 - progress)
            rAF(frameFunc)
          } else {
            el.scrollTop = 0
          }
        }
        rAF(frameFunc)
      }
    }
  }
}
</script>

<style scoped>
.fixed-button:hover {
  cursor: pointer;
}
.fixed-button {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  box-shadow: 0 0 6px rgba(0, 0, 0, 0.12);
  position: fixed;
  z-index: 20000;
  background-color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #43c2cb;
  font-size: 1.2rem;
}
</style>