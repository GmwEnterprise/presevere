<template>
  <div>{{ urlNumber }}</div>
</template>

<script>
export default {
  name: 'ArticlePost',
  data() {
    return {
      urlNumber: 0
    }
  },
  mounted() {
    const urlNumber = this.$route.params.urlNumber
    console.log(urlNumber)
    this.urlNumber = Number.parseInt(urlNumber)
  }
}
</script>