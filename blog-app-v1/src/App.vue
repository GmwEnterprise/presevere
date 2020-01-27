<template>
  <div id="app-outside">
    <router-view></router-view>
    <back-to-top></back-to-top>
  </div>
</template>

<script>
import backToTop from './components/Back2Top.vue'

export default {
  components: {
    backToTop
  }
}
</script>