<template>
  <div style="text-align: left;">
    <a-form-model :model="form" :label-col="labelCol" :wrapper-col="wrapperCol">
      <a-form-model-item label="产品编号">
        <a-input v-model="form.productId" />
      </a-form-model-item>
      <a-form-model-item label="产品名称">
        <a-input v-model="form.productName" />
      </a-form-model-item>
      <a-form-model-item label="产品描述">
        <a-textarea placeholder="输入本产品的详细描述..." :rows="4" v-model="form.productDesc" />
      </a-form-model-item>
      <!-- <a-form-model-item label="Activity zone">
        <a-select v-model="form.region" placeholder="please select your zone">
          <a-select-option value="shanghai">Zone one</a-select-option>
          <a-select-option value="beijing">Zone two</a-select-option>
        </a-select>
      </a-form-model-item>-->
      <a-form-model-item label="产品发布时间">
        <a-date-picker
          v-model="form.productCreatedTime"
          show-time
          type="date"
          placeholder="选择一个具体日期"
          style="width: 100%;"
        />
      </a-form-model-item>
      <!-- <a-form-model-item label="Instant delivery">
        <a-switch v-model="form.delivery" />
      </a-form-model-item>
      <a-form-model-item label="Activity type">
        <a-checkbox-group v-model="form.type">
          <a-checkbox value="1" name="type">Online</a-checkbox>
          <a-checkbox value="2" name="type">Promotion</a-checkbox>
          <a-checkbox value="3" name="type">Offline</a-checkbox>
        </a-checkbox-group>
      </a-form-model-item>
      <a-form-model-item label="Resources">
        <a-radio-group v-model="form.resource">
          <a-radio value="1">Sponsor</a-radio>
          <a-radio value="2">Venue</a-radio>
        </a-radio-group>
      </a-form-model-item>
      <a-form-model-item label="Activity form">
        <a-input v-model="form.desc" type="textarea" />
      </a-form-model-item>-->
      <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
        <a-button type="primary" @click="onSubmit">发布产品信息</a-button>
        <a-popconfirm
          title="将放弃输入并返回产品信息列表."
          ok-text="确定返回"
          cancel-text="继续编辑"
          @confirm="goback"
        >
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
      form: {
        productId: '',
        productName: '',
        productDesc: '',
        region: undefined,
        productCreatedTime: undefined,
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      }
    }
  },
  methods: {
    onSubmit() {
      console.log('submit!', this.form)
    },
    goback() {
      this.$router.push({ path: '/admin/products' })
    }
  }
}
</script>
