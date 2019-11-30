<template>
  <div>
    <button @click="click">Click</button>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'Home',
  methods: {
    click() {
      this.axios.get('/user/self').then(() => console.log(11111))
      this.axios.get('/t/hello').then(() => console.log(22222))
    }
  }
}
</script>