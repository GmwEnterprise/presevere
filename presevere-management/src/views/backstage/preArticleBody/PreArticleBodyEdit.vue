<template>
  <div>
    <form class="edit-form" @submit="submitForm">
            <div class="form-group">
        <label for="email"></label>
        <input class="form-control" id="id" v-model="data.id" placeholder="" required />
      </div>
      <div class="form-group">
        <label for="email">文章外键</label>
        <input class="form-control" id="articleMsgId" v-model="data.articleMsgId" placeholder="文章外键" required />
      </div>
      <div class="form-group">
        <label for="email">内容</label>
        <input class="form-control" id="content" v-model="data.content" placeholder="内容" required />
      </div>
      <div class="form-group">
        <label for="email">内容类型（默认为markdown）</label>
        <input class="form-control" id="contentType" v-model="data.contentType" placeholder="内容类型（默认为markdown）" required />
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
import preArticleBodyService from './preArticleBody.service.js'
export default {
  name: 'PreArticleBodyEdit',
  data() {
    return {
      // 1-add, 2-modify
      editType: 1,
      data: {
        id: null,
        articleMsgId: null,
        content: null,
        contentType: null,
      }
    }
  },
  methods: {
    async queryData(key) {
      const response = await preArticleBodyService.queryByKey(key)
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
        promise = preArticleBodyService.modify(this.data)
        flag = '修改'
      } else {
        // 新增
        promise = preArticleBodyService.add(this.data)
        flag = '新增'
      }
      promise
        .then(() => {
          this.$toast.success(`${flag}成功`)
          this.$router.push({
            name: 'preArticleBodyList'
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