<template>
  <div id="home">
    <el-row>
      <el-col :sm="16" :xs="24">
        <div
          v-for="(article, i) of articleList"
          :key="i"
          class="item"
          @click="toArticle(article.urlNumber)"
        >
          <el-card shadow="never" style="border-radius:0">
            <div class="floor title">
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
                <!-- 
                <span class="tag" @click="chooseTag('java', $event)">#java</span>
                <span class="tag" @click="chooseTag('javascript', $event)">#javascript</span>
                <span class="tag" @click="chooseTag('前后分离', $event)">#前后分离</span>-->
              </div>
            </div>
          </el-card>
        </div>
        <div class="see-more">
          <el-button type="text" :disabled="!hasNextPage">{{ hasNextPage ? '查看更多' : '已加载全部' }}</el-button>
        </div>
      </el-col>
      <el-col :sm="8" style="padding-left:1.2rem" class="hidden-xs-only">
        <!-- 边栏 -->
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
export default {
  data() {
    return {
      items: 5,
      tagList: [],
      pageStart: 0,
      orderBy: '',
      articleList: [],
      hasNextPage: false
    }
  },
  mounted() {
    this.load()
    this.axios.get('/article/allTabs').then(res => {
      // console.log(res)
      this.tagList = res.data
    })
  },
  methods: {
    load() {
      this.axios
        .get('/article/list', {
          params: {
            start: this.pageStart,
            orderBy: this.orderBy,
            desc: true
          }
        })
        .then(res => {
          this.articleList = res.data.list
          this.articleList.forEach(item => {
            item.publishedDate = item.publishedTime.substring(0, 10)
            item.tagList = item.tags ? item.tags.split(',') : []
          })
          this.pageStart = res.data.nextPage
          this.hasNextPage = res.data.hasNextPage
        })
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
      alert(tag)
    }
  }
}
</script>

<style scoped>
@import './home.css';
</style>