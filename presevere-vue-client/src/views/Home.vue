<template>
  <div id="home">
    <el-row>
      <el-col :sm="16" :xs="24">
        <div class="block-head">
          <span>按时间排序：</span>
          <el-dropdown trigger="click" @command="handleCommand">
            <span class="el-dropdown-link">
              {{ currentOrderString }}
              <i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item :command="currentOrder">{{ choiceOrderString }}</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <span
            class="tag"
            @click="resetTag"
            v-if="currentTag"
            style="float:right"
          >#{{ currentTag }}</span>
        </div>
        <div
          v-for="(article, i) of articleList"
          :key="i"
          class="item"
          @click="toArticle(article.urlNumber)"
        >
          <el-card shadow="never" style="border-radius:0">
            <div class="floor item-title">
              <span class="word">{{ article.title }}</span>
            </div>
            <div class="floor introduction">
              <span>{{ article.introduction }}</span>
            </div>
            <div class="metadata">
              <span class="metadata-item time">发表于{{ article.publishedDate }}</span>
              <div class="metadata-item tags">
                <span
                  class="tag"
                  v-for="(tag, j) of article.tagList"
                  :key="j"
                  @click="chooseTag(tag, $event)"
                >{{ `#${tag}` }}</span>
              </div>
            </div>
          </el-card>
        </div>
        <div class="see-more">
          <el-button type="text" :disabled="!hasNextPage" @click="load()">{{ loadMoreTitle }}</el-button>
        </div>
      </el-col>
      <el-col :sm="8" style="padding-left:1.2rem" class="hidden-xs-only">
        <!-- 边栏 -->
        <div class="block-head">
          <span>标签</span>
        </div>
        <el-card shadow="never" style="border-radius:0" class="bar-item">
          <div class="bar-tags">
            <span
              v-for="(item, i) of tagList"
              :key="i"
              class="tag"
              @click="chooseTag(item.tag)"
            >{{ `#${item.tag}(${item.count})` }}</span>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
const DESC = 1,
  ASC = 2
export default {
  data() {
    return {
      items: 5,
      tagList: [],
      pageStart: 1,
      orderBy: '',
      articleList: [],
      hasNextPage: false,

      // 切换order
      currentOrderString: '最新',
      choiceOrderString: '最早',
      currentOrder: DESC,

      // 筛选tag
      currentTag: '',

      // 状态
      statusLoading: false
    }
  },
  computed: {
    loadMoreTitle() {
      if (this.statusLoading) {
        return '加载中...'
      }
      return this.hasNextPage ? '查看更多...' : '已加载全部.'
    }
  },
  created() {
    this.load()
    this.axios.get('/article/allTabs').then(res => {
      // console.log(res)
      this.tagList = res.data
    })
  },
  methods: {
    resetTag() {
      this.currentTag = null
      this.articleList = []
      this.pageStart = 1
      this.load()
    },
    handleCommand(order) {
      if (order === DESC) {
        // 用ASC
        this.currentOrderString = '最早'
        this.choiceOrderString = '最新'
        this.currentOrder = ASC
      } else {
        // DESC
        this.currentOrderString = '最新'
        this.choiceOrderString = '最早'
        this.currentOrder = DESC
      }
      this.pageStart = 1
      this.nextPage = 1
      this.articleList = []
      this.load()
    },
    load() {
      this.statusLoading = true
      this.axios
        .get('/article/list', {
          params: {
            start: this.pageStart,
            orderBy: this.orderBy,
            desc: this.currentOrder === DESC,
            tag: this.currentTag
          }
        })
        .then(res => {
          const list = res.data.list
          list.forEach(item => {
            item.publishedDate = item.publishedTime.substring(0, 10)
            item.tagList = item.tags ? item.tags.split(',') : []
          })
          if (this.articleList.length > 0) {
            list.forEach(item => this.articleList.push(item))
          } else {
            this.articleList = list
          }
          this.pageStart = res.data.nextPage
          this.hasNextPage = res.data.hasNextPage
        })
        .finally(() => (this.statusLoading = false))
    },
    toArticle(key) {
      this.$router.push({
        path: `/post/${key}`
      })
    },
    chooseTag(tag, e) {
      if (e) {
        e.stopPropagation()
      }
      this.currentTag = tag
      this.articleList = []
      this.pageStart = 1
      this.load()
    }
  }
}
</script>

<style scoped>
@import './home.css';
</style>