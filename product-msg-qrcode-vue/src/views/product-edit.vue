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
          { required: true, message: '产品编号为必填项', trigger: 'blur' }
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
        } else {
          alert('请完成表单') // TODO 提示框美化
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
