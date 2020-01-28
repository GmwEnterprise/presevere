<template>
  <div class="back2top">
    <v-btn icon @click="back2Top">
      <v-icon size="50px">mdi-arrow-up-bold-circle</v-icon>
    </v-btn>
  </div>
</template>

<style lang="scss" scoped>
.back2top {
  position: fixed;
  bottom: 1.5em;
  right: 1.5em;
  background-color: white;
}
</style>

<script>
export default {
  name: 'back2Top',
  methods: {
    back2Top(e) {
      console.log(e)
    }
  }
}
</script>