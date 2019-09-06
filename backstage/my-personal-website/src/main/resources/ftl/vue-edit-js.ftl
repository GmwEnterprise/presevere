<template>
  <div>
    <form class="edit-form" @submit="submitForm">
      <#list columnList as column>
      <div class="form-group">
        <label for="email">${column.columnComment}</label>
        <input class="form-control" id="${column.fieldName}" v-model="data.${column.fieldName}" placeholder="${column.columnComment}" required />
      </div>
      </#list>
      <div class="edit-page-btn-wrapper">
        <button type="submit" class="btn btn-primary">提交</button>
        <button type="reset" class="btn btn-secondary">重置</button>
        <button type="button" class="btn btn-link" @click="returnPage">返回</button>
      </div>
    </form>
  </div>
</template>

<script>
import ${entityAlias}Service from './${entityAlias}.service.js'
export default {
  name: '${entityName}Edit',
  data() {
    return {
      // 1-add, 2-modify
      editType: 1,
      data: {
        <#list columnList as column>
        ${column.fieldName}: null,
        </#list>
      }
    }
  },
  methods: {
    async queryData(key) {
      const response = await ${entityAlias}Service.queryByKey(key)
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
        promise = ${entityAlias}Service.modify(this.data)
        flag = '修改'
      } else {
        // 新增
        promise = ${entityAlias}Service.add(this.data)
        flag = '新增'
      }
      promise
        .then(() => {
          ${'this.$toast.success(`$' + '{flag}成功`)'}
          this.$router.push({
            name: '${entityAlias}List'
          })
        })
        .catch((code, msg) => {
          ${'this.$toast.error(`$' + '{flag}失败: $' + '{msg}`, `错误编码: $' + '{code}`)'}
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