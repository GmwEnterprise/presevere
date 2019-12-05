<template>
  <div>
    <!-- <div class="flag-wrapper">
      <span style>
        <i></i>自己的
      </span>
      <span style>
        <i style="background-color:#f5faff"></i>其他人的
      </span>
    </div> -->
    <el-table
      :data="articleDrafts"
      style="width:100%"
      @selection-change="handleSelectionChange"
      @row-click="rowClick"
      :row-class-name="tableRowClassName"
    >
      <el-table-column type="expand" v-slot="props">
        <el-button type="danger" @click="deleteDraft(props)">删除</el-button>
      </el-table-column>
      <!-- <el-table-column prop="createTime" label="创建时间" sortable>
        <template v-slot:default="scope">
          <i class="el-icon-time"></i>
          <span
            style="margin-left: 10px"
            :title="scope.row.createTime"
          >{{ scope.row.createTime.substring(0,10).replace(/\-/g, '/') }}</span>
        </template>
      </el-table-column> -->
      <el-table-column prop="title" label="标题"></el-table-column>
      <el-table-column prop="version" label="版本" width="50"></el-table-column>
      <el-table-column prop="introduction" label="简介"></el-table-column>
      <el-table-column prop="tags" label="标签"></el-table-column>
      <el-table-column prop="lastUpdateTime" label="更新时间" sortable v-slot="scope">
        <i class="el-icon-time"></i>
        <span
          style="margin-left: 10px"
          :title="scope.row.lastUpdateTime"
        >{{ scope.row.lastUpdateTime.substring(0,10).replace(/\-/g, '/') }}</span>
      </el-table-column>
      <!-- <el-table-column prop="writerObject.nickname" label="作者" width="100"></el-table-column> -->
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
  name: 'ArticleDrafts',
  data() {
    return {
      loadingBtnName: '加载更多...',
      currentUserId: null,
      /**
       * id, url_number, title, introduction, tags, writer
       */
      articleDrafts: [],
      searchCondition: {
        startPage: 1,
        countByPage: 5,
        self: false, // 是否只查询自己的草稿
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
    this.loadDrafts(false)
    this.currentUserId = Number.parseInt(tokenService.currentUserId())
  },
  methods: {
    loadDrafts(more) {
      const loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      this.axios
        .get('/article/drafts', {
          params: this.searchCondition
        })
        .then(res => {
          const page = res.data
          if (more) {
            this.articleDrafts.push(...page.list)
          } else {
            this.articleDrafts = page.list
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
      this.loadDrafts(true)
    },
    handleSelectionChange() {
      console.log(arguments)
    },
    rowClick(row) {
      console.log(row.writer)
      if (row.writer === this.currentUserId) {
        this.$router.push({
          path: `/home/article/write/${row.id}`
        })
      }
    },
    deleteDraft(param) {
      // index = param.$index,
      const draftId = param.row.id
      this.$confirm('将删除指定内容.', '删除确认', {
        confirmButtonText: '删除',
        cancelButtonText: '返回',
        type: 'warning'
      })
        .then(() => {
          const loading = this.$loading({
            lock: true,
            text: 'Loading',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          })
          this.axios
            .delete(`/article/draft/${draftId}`)
            .then(() => {
              // this.articleDrafts.splice(index, 1) // 排序后会误删
              this.articleDrafts = this.articleDrafts.filter(
                item => item.id !== draftId
              )
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
            })
            .finally(() => loading.close())
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
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