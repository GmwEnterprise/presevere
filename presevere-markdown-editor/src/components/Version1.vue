<template>
  <div id="version-one">
    <div class="editor-box" :style="`width:${width}px`">
      <div class="nav"></div>
      <div class="editor-area" :style="`height:${height}px`">
        <presevere-editor v-model="content" :style="`width: ${showRender ? '50' : '100'}%;${editorBorder}`"></presevere-editor>
        <presevere-render :render="content" v-show="showRender"></presevere-render>
      </div>
    </div>
  </div>
</template>

<script>
import PresevereEditor from '@/components/editor/TextareaEditor.vue'
import PresevereRender from '@/components/editor/Render.vue'
export default {
  name: 'VersionOne',
  components: {
    PresevereEditor,
    PresevereRender
  },
  data() {
    return {
      content: 'hello',
      showRender: true,
      width: 1200,
      height: 700
    }
  },
  computed: {
    editorBorder() {
      return this.showRender ? 'border-right:2px solid #20a157' : ''
    }
  },
  methods: {
    inputE(e) {
      console.log(e)
    }
  }
}
</script>

<style scoped>
.editor-area {
  display: flex;
  justify-content: space-between;
  margin: 0 auto;
  box-sizing: content-box;
  border: 4px solid #20a157;
}
.nav {
  width: 100%;
  height: 50px;
  border: 4px solid #20a157;
  border-bottom: 0;
  box-sizing: border-box;
}
</style>
