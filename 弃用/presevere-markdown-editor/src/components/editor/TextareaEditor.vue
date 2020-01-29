<template>
  <div id="md-editor-wrapper">
    <textarea
      ref="editor"
      id="md-editor"
      :value="value"
      @input="input"
    ></textarea>
  </div>
</template>

<script>
export default {
  name: 'TextareaEditor',
  props: {
    value: {
      type: String,
      required: true
    }
  },
  methods: {
    input(e) {
      console.log(JSON.stringify(this.$refs['editor']))
      this.$emit('input', e.target.value)
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
  box-sizing: border-box;
  padding: 10px;
  font-size: 16px;
  line-height: 1.5em;
  font-family: 'monaco';
  border: 0;
  background-color: rgb(226, 255, 226);
  height: 100%;
  width: 100%;
}
#md-editor:focus {
  outline: none;
}
</style>