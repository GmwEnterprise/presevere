<template>
  <div>
    <!-- <div class="flag-wrapper">
      <span style>
        <i></i>自己的
      </span>
      <span style>
        <i style="background-color:#f5faff"></i>其他人的
      </span>
    </div>-->
    <el-table
      :data="articles"
      style="width:100%"
      @selection-change="handleSelectionChange"
      @row-click="rowClick"
      :row-class-name="tableRowClassName"
    >
      <el-table-column prop="title" label="标题"></el-table-column>
      <!-- <el-table-column prop="writerObject.nickname" label="作者" width="100"></el-table-column> -->
      <el-table-column prop="introduction" label="简介"></el-table-column>
      <el-table-column prop="tags" label="标签"></el-table-column>
      <el-table-column prop="createTime" label="发布时间" sortable>
        <template v-slot:default="scope">
          <i class="el-icon-time"></i>
          <span
            style="margin-left: 10px"
            :title="scope.row.createTime"
          >{{ scope.row.createTime.substring(0,10).replace(/\-/g, '/') }}</span>
        </template>
      </el-table-column>
      <template v-slot:append>
        <div style="display:flex;justify-content:center;align-items:center">
          <el-button
            type="text"
            @click="loadMore"
            :disabled="!pageInfo.hasNextPage"
          >{{ loadingBtnName }}</el-button>
        </div>
      </template>
    </el-table>
  </div>
</template>

<script>
import tokenService from '@/services/token.service.js'
export default {
  name: 'ArticleList',
  data() {
    return {
      loadingBtnName: '加载更多...',
      currentUserId: null,
      /**
       * id, url_number, title, introduction, tags, writer
       */
      articles: [],
      searchCondition: {
        startPage: 1,
        countByPage: 5,
        self: true, // 是否只查询自己的文章
        title: '', // 标题查询
        writerName: '' // 作者名查询，与self互斥
      },
      pageInfo: {
        hasNextPage: false, // 是否有下一页
        nextPage: 1 // 下一页
      }
    }
  },
  mounted() {
    this.loadArticles(false)
    this.currentUserId = Number.parseInt(tokenService.currentUserId())
  },
  methods: {
    loadArticles(more) {
      const loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      this.axios
        .get('/article/metadata', {
          params: this.searchCondition
        })
        .then(res => {
          const page = res.data
          if (more) {
            this.articles.push(...page.list)
          } else {
            this.articles = page.list
          }
          this.pageInfo.hasNextPage = page.hasNextPage
          this.pageInfo.nextPage = page.nextPage
          this.searchCondition.startPage = page.nextPage
          if (page.hasNextPage) {
            this.loadingBtnName = '加载更多...'
          } else {
            this.loadingBtnName = '没有更多了'
          }
        })
        .finally(() => loading.close())
    },
    loadMore() {
      this.loadArticles(true)
    },
    handleSelectionChange() {
      console.log(arguments)
    },
    rowClick(row) {
      console.log(row)
      this.$router.push({
        path: `/home/article/published/${row.urlNumber}`
      })
    },
    tableRowClassName(param) {
      if (param.row.writer === this.currentUserId) {
        return 'self-row'
      }
      return 'others-row'
    }
  }
}
</script>

<style>
tr.el-table__row.others-row {
  background-color: #f5faff;
}
.flag-wrapper > span {
  font-size: 0.9rem;
  display: inline-flex;
  align-items: center;
  margin-right: 1rem;
}
.flag-wrapper > span > i {
  border: 0.5px solid lightgray;
  width: 1rem;
  height: 1rem;
  margin-right: 0.3rem;
  display: inherit;
}
</style>