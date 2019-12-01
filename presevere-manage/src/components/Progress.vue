<template>
  <div id="progress-wrapper" :style="wrapperStyle">
    <span id="progress-bar" :style="barStyle"></span>
  </div>
  <!-- <div class="test-wrapper">
    <button @click="start">Start</button>
    <button @click="stop">Stop</button>
  </div>-->
</template>

<script>
const Stopped = 1,
  Running = 2,
  Stopping = 3
export default {
  name: 'ProgressBar',
  data() {
    return {
      // 进度条加速因子
      deltaIndex: 0,
      // deltaList: [2, 1, 0.5, 0.25, 0.12, 0.06, 0.03, 0.01, 0.005, 0],
      deltaList: [
        2.0,
        3.3,
        4.4,
        3.5,
        3.6,

        3.7,
        2.8,
        2.7,
        2.3,
        1.2,
        
        0.6,
        0.27,
        0.14,
        0.08,
        0.02,
        
        0.005,
        0.002,
        0.001,
        0,
        0
      ],

      // 100 / sep = deltaList的必需长度
      sep: 5,

      // 进度条当前宽度
      barWidth: 0,
      // 进度条状态：[Stopped]已停止, [Running]运行中, [Stopping]停止中
      barStatus: Stopped,
      // 加速度改变阈值
      temp: 0,
      // interval定时器
      timer: null,

      // 样式
      wrapperStyle: {
        display: 'none'
      },
      barStyle: {
        width: '0'
      }
    }
  },
  methods: {
    start() {
      if (this.barStatus === Stopped) {
        this.barStatus = Running
        this.progressBarInit()
        this.showProgressBar()

        this.timer = setInterval(() => {
          this.barWidth += this.deltaList[this.deltaIndex]
          this.barStyle.width = `${this.barWidth}%`

          if (this.barStatus === Running) {
            if (this.barWidth - this.temp > this.sep) {
              this.temp = this.barWidth
              // this.deltaIndex++
              if (this.deltaIndex + 1 < 100 / this.sep) {
                this.deltaIndex++
              }
            }
          }

          if (this.barWidth > 150) {
            clearInterval(this.timer)
            this.timer = null
            this.hideProgressBar()
            this.barStatus = Stopped
            this.deltaIndex = 0
          }
        }, 10)
      }
    },
    stop() {
      if (this.barStatus === Running) {
        this.barStatus = Stopping
        this.deltaIndex = 0
      }
    },

    progressBarInit() {
      this.barWidth = 0
      this.barStyle.width = 0
      this.temp = 0
    },
    showProgressBar() {
      this.wrapperStyle.display = 'flex'
    },
    hideProgressBar() {
      this.wrapperStyle.display = 'none'
    }
  },
  beforeDestroy() {
    if (this.timer) {
      clearInterval(this.timer)
    }
  }
}
</script>

<style scoped>
#progress-wrapper {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  overflow: hidden;
  height: 2px;
  /* display: none; */
}
#progress-bar {
  height: 100%;
  max-width: 100%;
  /* width: 0; */
  background-color: red;
}
</style>
