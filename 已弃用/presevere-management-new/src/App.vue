<template>
  <div>
    <router-link to="/login">helo</router-link>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  mounted() {
    console.log(this.$route)
  }
}
</script>