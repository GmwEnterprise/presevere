<template>
  <div class="form-item-wrapper">
    <template v-if="type === 'number'"></template>
    <template v-else-if="type === 'date-range'"></template>
    <template v-else-if="type === 'single-select'"></template>
    <template v-else>
      <div class="ftw-row">
        <div class="ftw-col-3 ftw-label">
          <label :for="id" :title="label">{{ label }}：</label>
        </div>
        <div class="ftw-col-9">
          <input :id="id" class="form-control" type="text" :value="value" @input="emitEvent" />
        </div>
      </div>
    </template>
  </div>
</template>

<script>
export default {
  name: 'QueryFormControlItem',
  props: {
    // 确定输入框的类型, 有text/number/date-range/single-select
    type: {
      type: String,
      required: false,
      default: 'text'
    },
    // label 标签
    label: {
      type: String,
      required: true
    },
    // id 唯一标识
    id: {
      type: String,
      required: true
    },
    // value 绑定的值，类型为any
    value: {
      required: true
    }
  },
  methods: {
    /**
     * @param {Event} e
     */
    emitEvent(e) {
      this.$emit('input', e.target.value)
    }
  }
}
</script>

<style>
.ftw-row {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  align-items: center;
}
.ftw-col-3 {
  width: 20%;
}
.ftw-col-9 {
  width: 78%;
}
.ftw-label {
  display: flex;
  align-items: center;
}
.ftw-label > label {
  margin: 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  flex: 1;
  text-align: right;
}
.form-item-wrapper {
  width: 30%;
  margin-right: 3%;
  display: inline-block;
  margin-bottom: 1rem;
}
.form-control {
  font-size: 0.8rem;
}
</style>