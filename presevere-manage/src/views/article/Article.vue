<template>
  <div>
    <h1 class="article-title">{{ article.title }}</h1>
    <div class="article-metadata">
      <div class="line">
        <span>发布于 {{ article.publishedTime }}</span>
      </div>
      <div class="line">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item v-for="(tag, i) of tagList" :key="i">
            <span>{{ tag }}</span>
          </el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <article class="markdown-body" id="md-article-wrapper"></article>
  </div>
</template>

<script>
import hljs from 'highlight.js'
import 'highlight.js/styles/github.css'
import 'github-markdown-css/github-markdown.css'
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
    }
  },
  watch: {
    ['article.content']() {
      document.getElementById(
        'md-article-wrapper'
      ).innerHTML = this.article.content
      document.querySelectorAll('pre code').forEach(block => {
        hljs.highlightBlock(block)
      })
    }
  },
  mounted() {
    const url = this.$route.params.url
    // console.log(url)
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
h1.article-title {
  text-align: center;
  line-height: 1.5em;
  font-size: 2.5em;
  padding-bottom: 0.7em;
  border-bottom: 0.5px solid lightgray;
}
.line {
  display: flex;
  justify-content: center;
  font-style: italic;
  margin-bottom: 1rem;
}
</style>