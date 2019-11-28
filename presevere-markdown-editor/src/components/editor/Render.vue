<template>
  <div v-html="afterRendering"></div>
</template>

<script>
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
    }
  }
}
</script>