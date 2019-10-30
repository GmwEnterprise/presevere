<template>
  <div id="show-markdown">
    <article class="markdown-body" v-html="render"></article>
  </div>
</template>

<script>
import showdown from 'showdown'
import preArticleMsgService from './preArticleMsg.service.js'
import 'github-markdown-css'

const converter = new showdown.Converter()

export default {
  data() {
    return {
      head: {
        id: null,
        title: null,
        introduction: null,
        tag: null,
        status: null,
        writer: null,
        createTime: null,
        updateTime: null
      },
      body: {
        id: null,
        content: null,
        contentType: null,
        articleMsgId: null
      }
    }
  },

  computed: {
    render() {
      return converter.makeHtml(this.body.content)
    }
  },

  mounted() {
    preArticleMsgService
      .queryArticle(this.$route.query.articleId)
      .then(response => {
        this.head = response.data.head
        this.body = response.data.body
      })
  },

  methods: {}
}
</script>

<style scoped>
.markdown-body {
  box-sizing: border-box;
  min-width: 200px;
  max-width: 980px;
  margin: 0 auto;
  padding: 45px;
}
@media (max-width: 767px) {
  .markdown-body {
    padding: 15px;
  }
}
</style>