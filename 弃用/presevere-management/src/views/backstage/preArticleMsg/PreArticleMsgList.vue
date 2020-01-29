<template>
  <div id="table-list-vue">
    <h3>{{ table.className + ' 表' }}</h3>
    
    <query-form @submit="querySubmit">
            <!-- <form-control v-model="formParam.id" label="主键" id="id" /> -->
      <form-control v-model="formParam.title" label="标题" id="title" />
      <form-control v-model="formParam.writer" label="作者" id="writer" />
      <!-- <form-control v-model="formParam.createTime" label="创建时间" id="createTime" /> -->
      <!-- <form-control v-model="formParam.updateTime" label="上次更新时间" id="updateTime" /> -->
      <!-- <form-control v-model="formParam.introduction" label="文章介绍" id="introduction" /> -->
      <form-control v-model="formParam.tag" label="标签" id="tag" />
      <form-control
        v-model="formParam.status"
        label="状态"
        id="status"
        type="single-select"
        :selectdItems="statusItems"
        itemType="number"
      />
    </query-form>
    <router-link
      to="/sys/modules/preArticleMsg/edit"
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
              v-html="column.customize(row)"
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
const statusItems = [
  { title: '有效', value: 1 },
  { title: '无效', value: 0 },
]

import preArticleMsgService from './preArticleMsg.service.js'
import { setTimeout } from 'timers'
export default {
  name: 'PreArticleMsgList',
  data() {
    return {
      statusItems,
      formParam: {
        id: null,
        title: null,
        writer: null,
        createTime: null,
        updateTime: null,
        introduction: null,
        tag: null,
        status: null,
      },
      table: {
        className: 'PreArticleMsg',
        columns: [
          { code: 'title', name: '文章标题', type: 'customize', show: true,
            customize(row) {
              // 显示一个链接
              // console.log(row)
              const currentHref = window.location.href
              const calculateHref = currentHref + `/look?articleId=${row['id']}`
              return `<a href="${calculateHref}">${row[this.code]}</a>`
            }
          },
          { code: 'writer', name: '文章作者', type: 'number', show: true },
          { code: 'introduction', name: '文章介绍', type: 'string', show: true },
          { code: 'tag', name: '分类标签', type: 'string', show: true },
          { code: 'status', name: '状态', type: 'customize', show: true,
            customize(row) {
              return statusItems.filter(item => row[this.code] === item.value)[0].title
            }
          },
          { code: 'createTime', name: '创建时间', type: 'date', show: true },
          { code: 'updateTime', name: '上次更新时间', type: 'date', show: true },
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
      const response = await preArticleMsgService.queryPage({
        currentPage: this.tableData.currentPage || 1,
        pageSize: this.tableData.pageSize || 8
      })
      this.tableData = response.data || {}
    },
    async querySubmit() {
      const response = await preArticleMsgService.queryPage({
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
      // 跳转到草稿页面的编辑界面，重新编辑已完成的草稿；
      // 如果重新发布草稿，先删除掉已发布的版本，然后插入新版本

      // this.$router.push({
      //   name: 'preArticleMsgEdit',
      //   query: {
      //     rowId
      //   }
      // })
    },
    deleteRow(rowId) {
      this.$message({
        type: 'warning',
        title: '确定删除吗？',
        detail: '该操作将不可逆！',
        btnName: '删除',
        event: async close => {
          await preArticleMsgService.delByKey(rowId)
          this.$toast.success('删除成功')
          await this.initTable()
          close()
        }
      })
    }
  }
}
</script>