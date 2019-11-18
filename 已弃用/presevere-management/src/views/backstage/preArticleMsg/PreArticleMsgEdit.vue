<template>
  <div>
    <form class="edit-form" @submit="submitForm">
            <div class="form-group">
        <label for="email">主键</label>
        <input class="form-control" id="id" v-model="data.id" placeholder="主键" required />
      </div>
      <div class="form-group">
        <label for="email">文章标题</label>
        <input class="form-control" id="title" v-model="data.title" placeholder="文章标题" required />
      </div>
      <div class="form-group">
        <label for="email">文章作者</label>
        <input class="form-control" id="writer" v-model="data.writer" placeholder="文章作者" required />
      </div>
      <div class="form-group">
        <label for="email">创建时间</label>
        <input class="form-control" id="createTime" v-model="data.createTime" placeholder="创建时间" required />
      </div>
      <div class="form-group">
        <label for="email">上次更新时间</label>
        <input class="form-control" id="updateTime" v-model="data.updateTime" placeholder="上次更新时间" required />
      </div>
      <div class="form-group">
        <label for="email">文章介绍</label>
        <input class="form-control" id="introduction" v-model="data.introduction" placeholder="文章介绍" required />
      </div>
      <div class="form-group">
        <label for="email">分类标签（逗号分隔）</label>
        <input class="form-control" id="tag" v-model="data.tag" placeholder="分类标签（逗号分隔）" required />
      </div>
      <div class="form-group">
        <label for="email">状态</label>
        <input class="form-control" id="status" v-model="data.status" placeholder="状态" required />
      </div>
      <div class="edit-page-btn-wrapper">
        <button type="submit" class="btn btn-primary">提交</button>
        <button type="reset" class="btn btn-secondary">重置</button>
        <button type="button" class="btn btn-link" @click="returnPage">返回</button>
      </div>
    </form>
  </div>
</template>

<script>
import preArticleMsgService from './preArticleMsg.service.js'
export default {
  name: 'PreArticleMsgEdit',
  data() {
    return {
      // 1-add, 2-modify
      editType: 1,
      data: {
        id: null,
        title: null,
        writer: null,
        createTime: null,
        updateTime: null,
        introduction: null,
        tag: null,
        status: null,
      }
    }
  },
  methods: {
    async queryData(key) {
      const response = await preArticleMsgService.queryByKey(key)
      if (response.data) {
        this.data = response.data
      }
    },
    /**
     * @param {Event} e
     */
    submitForm(e) {
      e.preventDefault()
      let flag, promise
      if (this.data.id) {
        // 修改
        promise = preArticleMsgService.modify(this.data)
        flag = '修改'
      } else {
        // 新增
        promise = preArticleMsgService.add(this.data)
        flag = '新增'
      }
      promise
        .then(() => {
          this.$toast.success(`${flag}成功`)
          this.$router.push({
            name: 'preArticleMsgList'
          })
        })
        .catch((code, msg) => {
          this.$toast.error(`${flag}失败: ${msg}`, `错误编码: ${code}`)
        })
    },
    returnPage() {
      this.$router.go(-1)
    }
  },
  mounted() {
    this.data.id = this.$route.query.rowId
    if (this.data.id) {
      console.log('修改')
      this.editType = 2
      this.queryData(this.data.id)
    } else {
      console.log('新增')
      this.editType = 1
    }
  }
}
</script>

<style>
.edit-form {
  width: 60%;
  margin: 0 auto;
}
.edit-page-btn-wrapper > button {
  margin-right: 1rem;
}
</style>