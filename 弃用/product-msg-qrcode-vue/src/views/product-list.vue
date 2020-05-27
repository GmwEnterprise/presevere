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
    <a-table :data-source="data" bordered>
      <a-table-column key="productId" data-index="productId" title="产品编号" />
      <a-table-column key="productName" data-index="productName" title="产品名称" />
      <a-table-column key="productDesc" data-index="productDesc" title="产品描述" />
      <a-table-column key="productCreatedTime" data-index="productCreatedTime" title="生产日期" />
      <a-table-column key="base64QRCode" data-index="base64QRCode" title="二维码">
        <template slot-scope="text, record">
          <img :src="`data:image/jpeg;base64,${record.base64QRCode}`" width="100" height="100" />
        </template>
      </a-table-column>
    </a-table>
  </div>
</template>
<script>
export default {
  data() {
    return {
      data: [],

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
  computed: {},
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
          this.data = []
          const data = resp.data
          if (!data.hasData) {
            this.$notification['info']({
              message: '提示',
              description: '暂无数据'
            })
          } else {
            console.log(data)

            this.pageParam.currentPage = data.currentPage
            this.pageParam.hasData = true
            this.pageParam.hasNext = data.hasNext
            this.pageParam.hasPrev = data.hasPrev
            this.pageParam.totalPageSize = data.totalPageSize

            data.data.forEach(item => {
              this.data.push({
                key: item.id,
                productId: item.productId,
                productName: item.productName,
                productDesc: item.productDesc,
                productCreatedTime: item.productCreatedTime,
                base64QRCode: item.base64qrcode
              })
            })
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