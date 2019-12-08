<template>
  <div>
    <common-nav></common-nav>
    <div class="container">
      <router-view></router-view>
    </div>
  </div>
</template>

<style scoped>
.container {
  width: 100%;
  padding: 15px;
  margin: 0 auto;
}
@media screen and (min-width: 768px) {
  .container {
    max-width: 1080px;
  }
}
</style>

<script>
import CommonNav from './components/CommonNav.vue'
export default {
  name: 'Home',
  components: {
    CommonNav
  }
}
</script>