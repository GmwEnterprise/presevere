<template>
  <v-app>
    <!-- Post -->
    {{ postId }}
  </v-app>
</template>

<script>
export default {
  name: 'post',
  data: () => ({
    postId: 0
  }),
  created() {
    this.postId = this.$route.params.postId || 0
  }
}
</script>