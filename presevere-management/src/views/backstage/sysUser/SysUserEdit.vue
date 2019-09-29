<template>
  <div>
    <form class="edit-form" @submit="submitForm">
      <!-- <div class="form-group">
        <label for="email">主键</label>
        <input class="form-control" id="id" v-model="data.id" placeholder="主键" required />
      </div>-->
      <div class="form-group">
        <label for="email">昵称</label>
        <input class="form-control" id="nickname" v-model="data.nickname" placeholder="昵称" required />
      </div>
      <div class="form-group">
        <label for="email">用户名</label>
        <input
          class="form-control"
          id="username"
          v-model="data.username"
          placeholder="用户名"
          required
        />
      </div>
      <div class="form-group">
        <label for="email">密码</label>
        <input class="form-control" id="password" v-model="data.password" placeholder="密码" disabled />
      </div>
      <div class="form-group">
        <label for="email">是否可用</label>
        <input
          class="form-control"
          id="available"
          v-model="data.available"
          placeholder="是否可用"
          required
        />
      </div>
      <div class="form-group">
        <label for="email">性别</label>
        <input class="form-control" id="sex" v-model="data.sex" placeholder="性别" required />
      </div>
      <div class="form-group">
        <label for="email">手机号</label>
        <input class="form-control" id="phone" v-model="data.phone" placeholder="手机号" required />
      </div>
      <div class="form-group">
        <label for="email">邮箱</label>
        <input class="form-control" id="email" v-model="data.email" placeholder="邮箱" required />
      </div>
      <div class="form-group">
        <label for="email">创建时间</label>
        <input
          class="form-control"
          id="createTime"
          v-model="data.createTime"
          placeholder="创建时间"
          disabled
        />
      </div>
      <div class="form-group">
        <label for="email">更新时间</label>
        <input
          class="form-control"
          id="updateTime"
          v-model="data.updateTime"
          placeholder="更新时间"
          disabled
        />
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
import sysUserService from './sysUser.service.js'
export default {
  name: 'SysUserEdit',
  data() {
    return {
      // 1-add, 2-modify
      editType: 2,
      data: {
        id: null,
        nickname: null,
        username: null,
        password: null,
        available: null,
        sex: null,
        phone: null,
        email: null,
        createTime: null,
        updateTime: null
      }
    }
  },
  methods: {
    async queryData(key) {
      const response = await sysUserService.queryByKey(key)
      if (response.data) {
        this.data = response.data
      }
    },
    /**
     * @param {Event} e
     */
    submitForm(e) {
      e.preventDefault()
      let flag
      if (this.data.id) {
        // 修改
        flag = '修改'
        sysUserService
          .modify(this.data)
          .then(() => {
            this.$toast.success(`${flag}成功`)
            this.$router.push({
              name: 'sysUserList'
            })
          })
          .catch((code, msg) => {
            this.$toast.error(`${flag}失败: ${msg}`, `错误编码: ${code}`)
          })
      } else {
        this.$toast.error('管理员不允许添加用户', '非法操作')
      }
    },
    returnPage() {
      this.$router.go(-1)
    }
  },
  mounted() {
    this.data.id = this.$route.params.rowId
    if (this.data.id) {
      this.queryData(this.data.id)
    }
    // 管理员不能新建user
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