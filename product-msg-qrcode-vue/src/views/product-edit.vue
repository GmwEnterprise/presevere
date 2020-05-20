<template>
  <div style="text-align: left;">
    <a-form-model
      ref="productRuleForm"
      :model="product"
      :rules="productRules"
      :label-col="labelCol"
      :wrapper-col="wrapperCol"
    >
      <a-form-model-item label="产品编号" prop="productId">
        <a-input v-model="product.productId" />
      </a-form-model-item>
      <a-form-model-item label="产品名称" prop="productName">
        <a-input v-model="product.productName" />
      </a-form-model-item>
      <a-form-model-item label="产品描述">
        <a-textarea placeholder="输入本产品的详细描述..." :rows="4" v-model="product.productDesc" />
      </a-form-model-item>
      <a-form-model-item label="产品发布时间">
        <a-date-picker
          v-model="product.productCreatedTime"
          show-time
          type="date"
          placeholder="选择一个具体日期"
          style="width: 100%;"
        />
      </a-form-model-item>
      <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
        <a-button type="primary" @click="onSubmit">发布产品信息</a-button>
        <a-popconfirm title="将放弃输入并返回产品信息列表." ok-text="确定返回" cancel-text="继续编辑" @confirm="goback">
          <a-button style="margin-left: 10px;">返回列表</a-button>
        </a-popconfirm>
      </a-form-model-item>
    </a-form-model>
  </div>
</template>
<script>
export default {
  data() {
    return {
      labelCol: { span: 4 },
      wrapperCol: { span: 14 },

      product: {
        productId: '',
        productName: '',
        productDesc: '',
        productCreatedTime: null
      },
      productRules: {
        // 校验规则：
        // https://www.antdv.com/components/form-model-cn/#components-form-model-demo-validation
        // https://github.com/yiminghe/async-validator
        // 需要通过 rules 属性传入约定的验证规则，并将 FormItem 的 prop 属性设置为需校验的字段名
        productId: [
          { required: true, message: '产品编号为必填项', trigger: 'blur' },
          {
            trigger: 'blur',
            validator: (rule, value, callback) => {
              if (!/^[a-zA-Z0-9_-]{4,40}$/.test(value)) {
                // 有空格
                callback(
                  new Error('编号长度为4-40，只能包含字母、数字、下划线和减号')
                )
              } else {
                callback()
              }
            }
          }
        ],
        productName: [
          { required: true, message: '产品名称为必填项', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    onSubmit() {
      console.log('submit!', this.form)
      this.$refs.productRuleForm.validate(valid => {
        if (valid) {
          // TODO submit
          this.axios
            .post('http://localhost:4200/api/v1/product', {
              productId: this.product.productId,
              productName: this.product.productName,
              productDesc: this.product.productDesc,
              productCreatedTime:
                this.product.productCreatedTime !== null
                  ? this.product.productCreatedTime.format(
                      'YYYY-MM-DD HH:mm:ss'
                    )
                  : null
            })
            .then(resp => {
              console.log('添加后响应：')
              console.log(resp)

              this.$notification['success']({
                message: '成功',
                description: '成功插入产品信息'
              })
              this.product.productId = ''
              this.product.productName = ''
              this.product.productDesc = ''
              this.product.productCreatedTime = null
            })
            .catch(error => {
              console.error(error)
              this.$notification['error']({
                message: '错误',
                description: '插入数据异常！请重试！'
              })
            })
        } else {
          this.$message.warning('请完成表单填写') // TODO 提示框美化
          return false
        }
      })
    },
    goback() {
      this.$router.push({ path: '/admin/products' })
    }
  }
}
</script>
