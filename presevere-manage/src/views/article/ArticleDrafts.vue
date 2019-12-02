<template>
  <div>
    <el-table :data="articleDrafts" style="width:100%">
      <el-table-column prop="title" label="标题"></el-table-column>
      <el-table-column prop="writerName" label="作者"></el-table-column>
      <el-table-column prop="introduction" label="简介"></el-table-column>
      <el-table-column prop="tags" label="标签"></el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  name: 'ArticleDrafts',
  data() {
    return {
      /**
       * id, url_number, title, introduction, tags, writer
       */
      articleDrafts: [],
      searchCondition: {
        startPage: 0,
        countByPage: 10,
        self: true, // 是否只查询自己的草稿
        title: '', // 标题查询
        writerName: '' // 作者名查询，与self互斥
      }
    }
  },
  mounted() {
    this.loadDrafts()
  },
  methods: {
    loadDrafts() {
      this.axios.get('/article/drafts', {
        params: this.searchCondition
      }).then(res => {

      })
    }
  }
}
</script>