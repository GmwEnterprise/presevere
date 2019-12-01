<template>
  <div>
    <nav-router :username="currentUser.nickname"></nav-router>
    <div class="body">
      <div class="current-place">
        当前位置：
        <span>{{ currentPlace }}</span>
      </div>
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import NavRouter from './Nav.vue'
export default {
  name: 'Home',
  components: {
    NavRouter
  },
  data() {
    return {
      currentUser: {
        nickname: ''
      },
      currentPlace: ''
    }
  },
  mounted() {
    this.axios
      .get('/user/currentUser')
      .then(res => (this.currentUser = res.data))
    this.currentPlace = this.$route.meta.title
  }
}
</script>

<style scoped>
@import './home.css';
</style>