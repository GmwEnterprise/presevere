<template>
  <div>
    <article class="markdown-body" v-html="renderedHtml"></article>
  </div>
</template>

<script>
export default {
  name: 'Article',
  data() {
    return {
      article: {
        urlNumber: 0,
        title: '',
        introduction: '',
        tags: '',
        publishedTime: '',
        content: '',
        writer: 0
      }
    }
  },
  computed: {
    tagList() {
      return this.article.tags ? this.article.tags.split(',') : []
    },
    renderedHtml() {
      return this.article.content
    }
  },
  mounted() {
    const url = this.$route.params.url
    console.log(url)
    this.axios.get(`/article/${url}`).then(res => {
      this.article = res.data
    })
  },
  methods: {}
}
</script>

<style scoped>
.markdown-body {
  padding: 1rem;
}
</style>