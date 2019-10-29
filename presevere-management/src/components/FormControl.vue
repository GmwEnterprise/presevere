<template>
  <div class="form-item-wrapper">
    <template v-if="type === 'number'"></template>
    <template v-else-if="type === 'date-range'"></template>
    <template v-else-if="type === 'single-select'">
      <!-- 单选框 -->
      <div class="ftw-row">
        <div class="ftw-col-3 ftw-label">
          <label :for="id" :title="label">{{ label }}：</label>
        </div>
        <div class="ftw-col-9">
          <select class="custom-select" @change="emitEvent">
            <option
              class="single-select-item"
              v-for="(item, index) of selectdItems"
              :key="index"
              :value="item.value"
            >{{ item.title }}</option>
          </select>
        </div>
      </div>
    </template>
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
    },

    /////// 各种类型下需要的一些预设值

    // 单选预设值
    // itemType[选项值的类型，默认string]和selectdItem[选项列表数据]
    itemType: {
      type: String, // string | number | any
      required: false,
      default: 'date'
    },
    selectdItems: {
      type: Array,
      required: false,
      default: () => [
        // itemType === 'date'时，时间类型如下
        { title: '时间一', value: '2000-01-01T00:00:00' },
        { title: '时间二', value: '1990-12-31T23:59:59' },
      ]
    },
  },
  methods: {
    /**
     * @param {Event} e
     */
    emitEvent(e) {
      console.log(e)
      if (this.type === 'single-select') {
        let val
        if (this.itemType === 'number') {
          val = Number.parseInt(e.target.value)
        } else if (this.itemType === 'date') {
          val = new Date(e.target.value)
        } else {
          val = e.target.value
        }
        this.$emit('input', val)
      } else {
        this.$emit('input', e.target.value)
      }
    }
  }
}
</script>

<style scoped>
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
select.custom-select {
  font-size: .8rem;
}
</style>