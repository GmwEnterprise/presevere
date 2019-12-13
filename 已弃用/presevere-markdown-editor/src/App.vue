<template>
  <div id="app">
    <markdown-editor></markdown-editor>
  </div>
</template>

<script>
// import VersionOne from '@/components/Version1.vue'
import MarkdownEditor from '@/components/editor/Editor.vue'
export default {
  name: 'app',
  components: {
    MarkdownEditor
  }
}
</script>