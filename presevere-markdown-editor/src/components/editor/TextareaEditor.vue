<template>
  <div id="md-editor-wrapper">
    <textarea id="md-editor" :style="hwStyle" @input="editorInput" :value="value"></textarea>
    <div v-html="renderContent"></div>
  </div>
</template>

<script>
export default {
  name: 'TextareaEditor',
  props: {
    width: {
      type: Number,
      required: false,
      default: 400
    },
    height: {
      type: Number,
      required: false,
      default: 300
    },
    value: {
      type: String,
      required: true
    }
  },
  computed: {
    hwStyle() {
      return `width:${this.width}px;height:${this.height}px`
    }
  },
  methods: {
    editorInput() {
      this.$emti('input', this.value)
    }
  }
}
</script>

<style scoped>
#md-editor {
  resize: none;
  white-space: nowrap;
  overflow-x: auto;
  overflow-y: auto;
}
#md-editor:focus {
  outline: none;
}
</style>