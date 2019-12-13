<template>
  <div class="presevere-markdown-render-wrapper">
    <div class="presevere-markdown-render" v-html="afterRendering"></div>
  </div>
</template>

<script>
import './presevere-markdown-render.css'
import MarkdownIt from 'markdown-it'
const mdit = new MarkdownIt()
export default {
  name: 'PresevereRender',
  props: {
    render: {
      required: true,
      type: String
    }
  },
  computed: {
    afterRendering() {
      return mdit.render(this.render)
    },
    style() {
      return `width:${this.width}px;`
    }
  }
}
</script>

<style scoped>
.presevere-markdown-render-wrapper {
  width: 50%;
  padding: 10px;
  overflow-x: auto;
  overflow-y: auto;
  height: 100%;
  box-sizing: border-box;
  border-left: 2px solid #20a157;
}
.presevere-markdown-render {
  width: 100%;
}
</style>