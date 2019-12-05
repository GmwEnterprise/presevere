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
      }
    }
  },
  computed: {
    currentPlace() {
      return this.$store.state.currentRouteTitle
    }
  },
  mounted() {
    this.axios
      .get('/user/currentUser')
      .then(res => (this.currentUser = res.data))
  }
}
</script>

<style scoped>
@import './home.css';
</style>