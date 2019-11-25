<template>
  <div>
    <h2>Demo页面，显示/测试一些功能/组件/方法</h2>
    <hr />
    <button @click="loading">开始加载</button>
    <button @click="loadComplete">结束加载</button>
  </div>
</template>

<script>
export default {
  name: 'Demo',
  methods: {
    loading() {
      this.startLoading()
    },
    loadComplete() {
      this.stopLoading()
    }
  }
}
</script>