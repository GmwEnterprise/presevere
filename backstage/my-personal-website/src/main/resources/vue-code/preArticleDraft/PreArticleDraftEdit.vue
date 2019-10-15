<template>
  <div>
    <form class="edit-form" @submit="submitForm">
            <div class="form-group">
        <label for="email">主键</label>
        <input class="form-control" id="id" v-model="data.id" placeholder="主键" required />
      </div>
      <div class="form-group">
        <label for="email">草稿标题</label>
        <input class="form-control" id="title" v-model="data.title" placeholder="草稿标题" required />
      </div>
      <div class="form-group">
        <label for="email">草稿创建者</label>
        <input class="form-control" id="creator" v-model="data.creator" placeholder="草稿创建者" required />
      </div>
      <div class="form-group">
        <label for="email">草稿更新时间</label>
        <input class="form-control" id="updateTime" v-model="data.updateTime" placeholder="草稿更新时间" required />
      </div>
      <div class="form-group">
        <label for="email">草稿介绍</label>
        <input class="form-control" id="introduction" v-model="data.introduction" placeholder="草稿介绍" required />
      </div>
      <div class="form-group">
        <label for="email">草稿分类</label>
        <input class="form-control" id="tag" v-model="data.tag" placeholder="草稿分类" required />
      </div>
      <div class="form-group">
        <label for="email">草稿内容</label>
        <input class="form-control" id="content" v-model="data.content" placeholder="草稿内容" required />
      </div>
      <div class="form-group">
        <label for="email">草稿文章类型</label>
        <input class="form-control" id="contentType" v-model="data.contentType" placeholder="草稿文章类型" required />
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
import preArticleDraftService from './preArticleDraft.service.js'
export default {
  name: 'PreArticleDraftEdit',
  data() {
    return {
      // 1-add, 2-modify
      editType: 1,
      data: {
        id: null,
        title: null,
        creator: null,
        updateTime: null,
        introduction: null,
        tag: null,
        content: null,
        contentType: null,
      }
    }
  },
  methods: {
    async queryData(key) {
      const response = await preArticleDraftService.queryByKey(key)
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
        promise = preArticleDraftService.modify(this.data)
        flag = '修改'
      } else {
        // 新增
        promise = preArticleDraftService.add(this.data)
        flag = '新增'
      }
      promise
        .then(() => {
          this.$toast.success(`${flag}成功`)
          this.$router.push({
            name: 'preArticleDraftList'
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
    this.data.id = this.$route.params.rowId
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