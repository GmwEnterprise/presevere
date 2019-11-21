<template>
  <div>
    <h4>{{ token }}</h4>
    <p>{{ currentUserStr }}</p>
    <hr />
    <button @click="logout">注销</button>
  </div>
</template>

<script>
export default {
  name: 'Home',
  data() {
    return {
      token: '',
      currentUser: {}
    }
  },
  computed: {
    currentUserStr() {
      return JSON.stringify(this.currentUser)
    }
  },
  mounted() {
    this.token = localStorage.getItem('token') || '没有token'
    this.axios.get('/user/self').then(res => {
      this.currentUser = res.data
    })
  },

  methods: {
    logout() {
      localStorage.removeItem('token')
      this.$message({
        message: '注销成功',
        type: 'success',
        center: true
      })
      this.$router.push({
        path: '/login'
      })
    }
  }
}
</script>