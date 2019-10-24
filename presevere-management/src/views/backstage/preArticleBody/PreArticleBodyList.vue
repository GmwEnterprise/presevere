<template>
  <div id="table-list-vue">
    <h3>{{ table.className + ' 表' }}</h3>
    <!--
    <query-form @submit="querySubmit">
            <form-control v-model="formParam.id" label="" id="id" />
      <form-control v-model="formParam.articleMsgId" label="文章外键" id="articleMsgId" />
      <form-control v-model="formParam.content" label="内容" id="content" />
      <form-control v-model="formParam.contentType" label="内容类型（默认为markdown）" id="contentType" />
    </query-form> -->
    <router-link
      to="/sys/modules/preArticleBody/edit"
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
import preArticleBodyService from './preArticleBody.service.js'
import { setTimeout } from 'timers'
export default {
  name: 'PreArticleBodyList',
  data() {
    return {
      formParam: {
        id: null,
        articleMsgId: null,
        content: null,
        contentType: null,
      },
      table: {
        className: 'PreArticleBody',
        columns: [
          { code: 'id', name: '', type: 'number', show: true },
          { code: 'articleMsgId', name: '文章外键', type: 'number', show: true },
          { code: 'content', name: '内容', type: 'string', show: true },
          { code: 'contentType', name: '内容类型（默认为markdown）', type: 'string', show: true },
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
      const response = await preArticleBodyService.queryPage({
        currentPage: this.tableData.currentPage || 1,
        pageSize: this.tableData.pageSize || 8
      })
      this.tableData = response.data || {}
    },
    async querySubmit() {
      const response = await preArticleBodyService.queryPage({
        currentPage: 1,
        pageSize: 8,
        ...this.formParam
      })
      this.tableData = response.data || {}
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
        name: 'preArticleBodyEdit',
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
          await preArticleBodyService.delByKey(rowId)
          this.$toast.success('删除成功')
          await this.initTable()
          close()
        }
      })
    }
  }
}
</script>