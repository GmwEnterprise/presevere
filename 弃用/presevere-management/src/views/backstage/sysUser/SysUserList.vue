<template>
  <div id="table-list-vue">
    <h3>{{ table.className + ' 表' }}</h3>
    <query-form @submit="querySubmit">
      <!-- <form-control v-model="formParam.id" label="主键" id="id" /> -->
      <form-control v-model="formParam.nickname" label="昵称" id="nickname" />
      <form-control v-model="formParam.username" label="用户名" id="username" />
      <!-- <form-control v-model="formParam.password" label="密码" id="password" /> -->
      <!-- <form-control v-model="formParam.available" label="是否可用" id="available" /> -->
      <!-- <form-control v-model="formParam.sex" label="性别" id="sex" /> -->
      <form-control v-model="formParam.phone" label="手机号" id="phone" />
      <form-control v-model="formParam.email" label="邮箱" id="email" />
      <!-- <form-control v-model="formParam.createTime" label="创建时间" id="createTime" /> -->
      <!-- <form-control v-model="formParam.updateTime" label="更新时间" id="updateTime" /> -->
    </query-form>
    <router-link
      to="/sys/modules/sysUser/edit"
      class="btn btn-light"
      style="margin-bottom: 5px; font-size: .8em;"
    >新增</router-link>
    <table class="table table-striped" style="width: auto;font-size: .8em;">
      <thead class="thead-dark">
        <tr>
          <th class="back-th-td">序号</th>
          <th class="back-th-td">操作</th>
          <th
            class="back-th-td"
            v-for="(column, index) of table.columns"
            :key="index"
            v-show="column.show"
            :title="column.code"
          >{{ column.name }}</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(row, index) of tableData.list" :key="index">
          <td class="back-th-td">{{ index + 1 + tableData.pageSize * (tableData.currentPage - 1) }}</td>
          <td class="back-th-td back-btn-cell" style="padding: 0;">
            <span class="back-temp-td-operator">占位符</span>
            <div class="back-btn-box">
              <button
                class="back-btn-operation back-btn-edit"
                style="left: 0;"
                @click="editRow(row.id)"
              >编辑</button>
              <span>/</span>
              <button
                class="back-btn-operation back-btn-delete"
                style="right: 0;"
                @click="deleteRow(row.id)"
              >删除</button>
            </div>
          </td>
          <td
            class="back-th-td"
            v-for="(column, columnIndex) of table.columns"
            :key="columnIndex"
            :title="row[column.code]"
            v-show="column.show"
          >
            <template v-if="column.type === 'string'">{{ row[column.code] }}</template>
            <template v-else-if="column.type === 'number'">
              <span class="back-column-number">{{ row[column.code] }}</span>
            </template>
            <template v-else-if="column.type === 'date'">
              <span class="back-column-date">{{ row[column.code] }}</span>
            </template>
            <template v-else-if="column.type === 'string'">{{ row[column.code] }}</template>
            <div
              class="back-column-customize-box"
              v-else-if="column.type === 'customize'"
              v-html="column.customize(row[column.code])"
            ></div>
            <template v-else class="back-column-othertype">{{ row[column.code] }}</template>
          </td>
        </tr>
      </tbody>
    </table>
    <page-component v-on:jump="pageJump" :param="tableData"></page-component>
  </div>
</template>

<script>
import sysUserService from './sysUser.service.js'
import { setTimeout } from 'timers'
export default {
  name: 'SysUserList',
  data() {
    return {
      formParam: {
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
      },
      table: {
        className: 'SysUser',
        columns: [
          // { code: 'id', name: '主键', type: 'number', show: true },
          { code: 'nickname', name: '昵称', type: 'string', show: true },
          { code: 'username', name: '用户名', type: 'string', show: true },
          // { code: 'password', name: '密码', type: 'string', show: true },
          { code: 'available', name: '是否可用', type: 'any', show: true },
          { code: 'sex', name: '性别', type: 'number', show: true },
          { code: 'phone', name: '手机号', type: 'string', show: true },
          { code: 'email', name: '邮箱', type: 'string', show: true },
          { code: 'createTime', name: '创建时间', type: 'date', show: true },
          { code: 'updateTime', name: '更新时间', type: 'date', show: true }
        ]
      },
      tableData: {},
      currentEvent: null
    }
  },
  created() {
    this.initTable()
  },
  methods: {
    async initTable() {
      const response = await sysUserService.queryPage({
        currentPage: this.tableData.currentPage || 1,
        pageSize: this.tableData.pageSize || 8
      })
      this.tableData = response.data || {}
      this.$toast.success(`共${this.tableData.total | 0}条数据`, '查询成功')
    },
    async querySubmit() {
      const response = await sysUserService.queryPage({
        currentPage: 1,
        pageSize: 8,
        ...this.formParam
      })
      this.tableData = response.data || {}
      this.$toast.success(`共${this.tableData.total | 0}条数据`, '查询成功')
    },
    async pageJump(pageValue) {
      if (!this.currentEvent) {
        this.currentEvent = 1
        this.tableData.currentPage = pageValue
        await this.initTable()
        setTimeout(() => {
          this.currentEvent = null
        }, 100)
      }
    },
    editRow(rowId) {
      console.log(rowId)
      this.$router.push({
        name: 'sysUserEdit',
        query: {
          rowId
        }
      })
    },
    deleteRow(rowId) {
      this.$message({
        type: 'warning',
        title: '确定删除吗？',
        detail: '该操作将不可逆！',
        btnName: '删除',
        event: async close => {
          await sysUserService.delByKey(rowId)
          this.$toast.success('删除成功')
          await this.initTable()
          close()
        }
      })
    }
  }
}
</script>