<template>
  <div>
    <common-nav></common-nav>
    <router-view></router-view>
  </div>
</template>

<script>
import CommonNav from './components/CommonNav.vue'
export default {
  name: 'Home',
  components: {
    CommonNav
  }
}
</script>