<template>
  <div id="table-list-vue">
    <h3>{{ table.className + ' 表' }}</h3>
    <!--
    <query-form @submit="querySubmit">
      <#list columnList as column>
      <form-control v-model="formParam.${column.fieldName}" label="${column.columnComment}" id="${column.fieldName}" />
      </#list>
    </query-form> -->
    <router-link
      to="/sys/modules/${entityAlias}/edit"
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
import ${entityAlias}Service from './${entityAlias}.service.js'
import { setTimeout } from 'timers'
export default {
  name: '${entityName}List',
  data() {
    return {
      formParam: {
        <#list columnList as column>
        ${column.fieldName}: null,
        </#list>
      },
      table: {
        className: '${entityName}',
        columns: [
          <#list columnList as column>
          { code: '${column.fieldName}', name: '${column.columnComment}', type: '${column.javascriptType}', show: true },
          </#list>
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
      const response = await ${entityAlias}Service.queryPage({
        currentPage: this.tableData.currentPage || 1,
        pageSize: this.tableData.pageSize || 8
      })
      this.tableData = response.data || {}
    },
    async querySubmit() {
      const response = await ${entityAlias}Service.queryPage({
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
      this.$router.push({
        name: '${entityAlias}Edit',
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
          await ${entityAlias}Service.delByKey(rowId)
          this.$toast.success('删除成功')
          await this.initTable()
          close()
        }
      })
    }
  }
}
</script>