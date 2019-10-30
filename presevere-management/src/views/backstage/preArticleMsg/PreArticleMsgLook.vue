<template>
  <div class="wrapper">
    <h2 class="title">{{ article.head.title }}</h2>
    <div class="article-metadata">
      <span>{{ metaData }}</span>
    </div>
    <div class="tag-listshow" v-if="tagRender" v-html="tagRender"></div>
    <hr />
    <article class="markdown-body" v-html="render"></article>
    <button class="btn btn-link fixed-btn" style="left: 35px;" @click="modify">修改</button>
    <button class="btn btn-link fixed-btn" style="right: 35px;" @click="turnBack">返回</button>
  </div>
</template>

<script>
import showdown from 'showdown'
import preArticleMsgService from './preArticleMsg.service.js'
import 'github-markdown-css'

const converter = new showdown.Converter()

export default {
  name: 'ArticleLook',
  data() {
    return {
      article: {
        head: {
          id: -1,
          title: '',
          introduction: '',
          tag: '',
          status: -1,
          writer: -1,
          createTime: '',
          updateTime: ''
        },
        body: {
          id: -1,
          content: '',
          contentType: '',
          articleMsgId: -1
        },
        writerName: ''
      }
    }
  },

  computed: {
    render() {
      return converter.makeHtml(this.article.body.content)
    },
    metaData() {
      return `${this.article.writerName} / ${this.article.head.updateTime}`
    },
    tagRender() {
      const tags = this.article.head.tag
      if (!tags) {
        return ''
      }
      const tagRenderList = tags
        .split(',')
        .map(
          tag => `<span class="tag-listshow-item" title="${tag}">${tag}</span>`
        )
      return tagRenderList.join('<i style="padding: 0 5px">/</i>')
    }
  },

  mounted() {
    preArticleMsgService
      .queryArticle(this.$route.query.articleId)
      .then(response => {
        this.article = response.data
      })
  },

  methods: {
    turnBack() {
      this.$router.go(-1)
    },
    modify() {
      // TODO 修改已发布的文章
    }
  }
}
</script>

<style scoped>
.markdown-body {
  box-sizing: border-box;
  min-width: 200px;
  max-width: 80%;
  margin: 0 auto;
  padding: 45px;
}
.article-metadata {
  display: flex;
  justify-content: center;
  margin-top: 20px;
  font-size: 0.85em;
}
.title {
  text-align: center;
}
.tag-listshow {
  display: flex;
  font-size: 0.75em;
  justify-content: center;
  margin-top: 10px;
}
.fixed-btn {
  position: absolute;
  bottom: 35px;
}
</style>