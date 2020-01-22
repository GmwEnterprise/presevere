<template>
  <div class="home">
    <my-header title="Mrag's blogs" link-style @click="clickEvent"></my-header>
    
  </div>
</template>

<script>
import MyHeader from '../components/Header'
export default {
  name: 'home',
  components: {
    MyHeader
  },
  methods: {
    clickEvent(e) {
      console.log(e)
    }
  }
}
</script>

<style scoped>

</style>