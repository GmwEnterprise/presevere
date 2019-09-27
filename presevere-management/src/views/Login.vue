<template>
  <div>login</div>
</template>

<script>
export default {
  name: 'Login',
  data() {
    return {
      redirectPath: '/sys'
    }
  },
  created() {
    const redirectPath = this.$route.query.redirect
    if (redirectPath) {
      this.redirectPath = redirectPath
    }
  }
}
</script>