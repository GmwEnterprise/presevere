<template>
  <div id="app">
    <presevere-editor :width="500" :height="350" v-model="content"></presevere-editor>
    <presevere-render :render="content"></presevere-render>
  </div>
</template>

<script>
import PresevereEditor from '@/components/editor/TextareaEditor.vue'
import PresevereRender from '@/components/editor/Render.vue'
export default {
  name: 'app',
  components: {
    PresevereEditor,
    PresevereRender
  },
  data() {
    return {
      content: ''
    }
  }
}
</script>

<style scoped>
</style>
