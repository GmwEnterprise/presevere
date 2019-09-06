<template>
  <form class="query-form" @submit="submitEvent">
    <div class="form-control-item-wrapper">
      <!-- 插槽 -->
      <slot></slot>
    </div>
    <div class="q-f-btn-wrapper">
      <button type="submit" class="btn btn-primary query-form-submit-btn">查询</button>
    </div>
  </form>
</template>

<script>
export default {
  name: 'PublicModuleQueryForm',
  methods: {
    /**
     * 传递事件
     * @param {Event} e
     */
    submitEvent(e) {
      e.preventDefault()
      this.$emit('submit')
    }
  }
}
</script>

<style>
form.query-form {
  background: #f2f2f2;
  box-sizing: border-box;
  padding: 0.8rem 0;
  margin: 1rem 0;
  width: 100%;
  font-size: 0.8rem;
}
button.query-form-submit-btn {
  font-size: 0.9em;
  width: 5rem;
  display: block;
  margin-left: 22%;
}
.q-f-btn-wrapper {
  width: 30%;
  box-sizing: border-box;
}
</style>
