<template>
  <div>
    <div style="display: flex;">
      <a-button type="primary" @click="addNewProduct">新增产品信息</a-button>
      <i style="width: 15px;"></i>
      <a-button type="primary">修改选中产品</a-button>
      <i style="width: 15px;"></i>
      <a-button type="danger">删除选中产品</a-button>
    </div>
    <hr style="border-color: red; margin: 2em 0; border: .1px solid #d9d9d9;" />
    <div style="display: flex;">
      <a-form layout="inline">
        <a-form-item :model="condition">
          <a-input placeholder="产品编号" v-model="condition.productId" />
        </a-form-item>
        <a-form-item>
          <a-input placeholder="产品名称" v-model="condition.productName" />
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="query(1)">模糊查询</a-button>
        </a-form-item>
      </a-form>
    </div>
    <hr style="border-color: red; margin: 2em 0; border: .1px solid #d9d9d9;" />
    <a-table
      v-if="pageParam.hasData"
      :row-selection="rowSelection"
      :columns="columns"
      :data-source="data"
      bordered
    >
      <a slot="name" slot-scope="text">{{ text }}</a>
    </a-table>
    <div v-else class="has-no-data">暂无数据</div>
  </div>
</template>
<script>
const columns = [
  {
    title: 'Name',
    dataIndex: 'name',
    scopedSlots: { customRender: 'name' }
  },
  {
    title: 'Age',
    dataIndex: 'age'
  },
  {
    title: 'Address',
    dataIndex: 'address'
  }
]
const data = [
  {
    key: '1',
    name: 'John Brown',
    age: 32,
    address: 'New York No. 1 Lake Park'
  },
  {
    key: '2',
    name: 'Jim Green',
    age: 42,
    address: 'London No. 1 Lake Park'
  },
  {
    key: '3',
    name: 'Joe Black',
    age: 32,
    address: 'Sidney No. 1 Lake Park'
  },
  {
    key: '4',
    name: 'Disabled User',
    age: 99,
    address: 'Sidney No. 1 Lake Park'
  }
]

export default {
  data() {
    return {
      data,
      columns,

      // 查询条件
      condition: {
        productId: '',
        productName: ''
      },
      pageParam: {
        hasData: true,
        currentPage: -1,
        totalPageSize: -1,
        hasNext: false,
        hasPrev: false,

        pageSize: 10 // 默认一页显示10条数据
      }
    }
  },
  computed: {
    rowSelection() {
      return {
        onChange: (selectedRowKeys, selectedRows) => {
          console.log(
            `selectedRowKeys: ${selectedRowKeys}`,
            'selectedRows: ',
            selectedRows
          )
        },
        getCheckboxProps: record => ({
          props: {
            disabled: record.name === 'Disabled User', // Column configuration not to be checked
            name: record.name
          }
        })
      }
    }
  },
  methods: {
    addNewProduct() {
      this.$router.push({ path: '/admin/product/new' })
    },
    query(targetPageNum) {
      // console.log(this.condition.productId, this.condition.productName)
      this.axios
        .get('http://localhost:4200/api/v1/product', {
          params: {
            productId: this.condition.productId,
            productName: this.condition.productName,
            pageNum: targetPageNum, // 查询指定页面
            pageSize: this.pageParam.pageSize
          }
        })
        .then(resp => {
          const data = resp.data
          this.pageParam.hasData = data.hasdata
          if (!this.pageParam.hasData) {
            // TODO 将列表页的显示修改为无数据模式
            this.data = []
          } else {
            // TODO 展示数据
          }
        })
    }
  }
}
</script>
<style scoped>
.has-no-data {
  font-size: 1.5em;
  font-style: italic;
  color: lightgray;
  line-height: 5em;
}
</style>