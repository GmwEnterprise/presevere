<template>
  <div id="home">
    <el-row>
      <el-col :sm="16" :xs="24">
        <el-card
          v-for="(item, i) of items"
          :key="i"
          class="item"
          shadow="never"
          style="border-radius:0"
        >
          <template #header>
            <div class="floor title" @click="toArticle('key')">
              <span class="word">
                如何在前端实现Token过期无感知刷新
                如何在前端实现Token过期无感知刷新
                如何在前端实现Token过期无感知刷新
              </span>
            </div>
            <div class="metadata">
              <span class="time">发表于 2019/12/8</span>
              <span class="tags">java / javascript / 前后分离</span>
            </div>
          </template>
          <template #default>
            <div class="introduction" @click="toArticle('key')">
              <span>这里是介绍本文章的地方</span>
            </div>
          </template>
        </el-card>
        <div class="see-more">
          <el-button type="text">查看更多...</el-button>
        </div>
      </el-col>
      <el-col :sm="8">
        <!-- 边栏 -->
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  data() {
    return {
      items: 5
    }
  },
  methods: {
    toArticle(key) {
      console.log(key)
    }
  }
}
</script>

<style scoped>
@import './home.css';
</style>