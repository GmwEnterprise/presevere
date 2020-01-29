<template>
  <div>
    <form class="edit-form" @submit="submitForm">
      <!-- <div class="form-group">
        <label for="email">主键</label>
        <input class="form-control" id="id" v-model="data.id" placeholder="主键" required />
      </div>-->
      <div class="form-group">
        <label for="email">路由标题</label>
        <input
          class="form-control"
          id="routerTitle"
          v-model="data.routerTitle"
          placeholder="路由标题"
          required
        />
      </div>
      <div class="form-group">
        <label for="email">路由名称</label>
        <input
          class="form-control"
          id="routerName"
          v-model="data.routerName"
          placeholder="路由名称"
          required
        />
      </div>
      <!-- <div class="form-group">
        <label for="email">创建时间</label>
        <input class="form-control" id="createDatetime" v-model="data.createDatetime" placeholder="创建时间" required />
      </div>
      <div class="form-group">
        <label for="email">最后更新时间</label>
        <input class="form-control" id="lastUpdateDatetime" v-model="data.lastUpdateDatetime" placeholder="最后更新时间" required />
      </div>-->
      <div class="edit-page-btn-wrapper">
        <button type="submit" class="btn btn-primary">提交</button>
        <button type="reset" class="btn btn-secondary">重置</button>
        <button type="button" class="btn btn-link" @click="returnPage">返回</button>
      </div>
    </form>
  </div>
</template>

<script>
import sysRouterService from './sysRouter.service.js'
export default {
  name: 'SysRouterEdit',
  data() {
    return {
      // 1-add, 2-modify
      editType: 1,
      data: {
        id: null,
        routerTitle: null,
        routerName: null,
        createDatetime: null,
        lastUpdateDatetime: null
      }
    }
  },
  methods: {
    async queryData(key) {
      const response = await sysRouterService.queryByKey(key)
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
        promise = sysRouterService.modify(this.data)
        flag = '修改'
      } else {
        // 新增
        promise = sysRouterService.add(this.data)
        flag = '新增'
      }
      promise
        .then(() => {
          this.$toast.success(`${flag}成功`)
          this.$router.push({
            name: 'sysRouterList'
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