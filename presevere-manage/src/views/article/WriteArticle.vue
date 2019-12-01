<template>
  <div class="article-area">
    
    <div class="write-wrapper">
      <input type="text" placeholder="输入标题" />
    </div>
    <div class="write-wrapper">
      <mavon-editor
        ref="mavon"
        style="box-shadow:none !important"
        v-model="markdownContent"
        :toolbars="toolbars"
        @change="contentChange"
        @imgAdd="imageUpload"
        @imgDel="imageRemove"
      ></mavon-editor>
    </div>
  </div>
</template>

<script>
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
export default {
  name: 'WriteArticleComponent',
  data() {
    return {
      markdownContent: '',
      // ==========================
      //       以下是工具栏属性
      // ==========================
      toolbars: {
        bold: false, // 粗体
        italic: false, // 斜体
        header: false, // 标题
        underline: false, // 下划线
        strikethrough: false, // 中划线
        mark: false, // 标记
        superscript: false, // 上角标
        subscript: false, // 下角标
        quote: false, // 引用
        ol: false, // 有序列表
        ul: false, // 无序列表
        link: true, // 链接
        imagelink: true, // 图片链接
        code: false, // code
        table: true, // 表格
        fullscreen: false, // 全屏编辑
        readmodel: false, // 沉浸式阅读
        htmlcode: true, // 展示html源码
        help: true, // 帮助
        /* 1.3.5 */
        undo: true, // 上一步
        redo: true, // 下一步
        trash: true, // 清空
        save: false, // 保存（触发events中的save事件）
        /* 1.4.2 */
        navigation: false, // 导航目录
        /* 2.1.8 */
        alignleft: false, // 左对齐
        aligncenter: false, // 居中
        alignright: false, // 右对齐
        /* 2.2.1 */
        subfield: false, // 单双栏模式
        preview: true // 预览
      }
    }
  },
  components: {
    mavonEditor
  },
  methods: {
    contentChange(markdown, html) {
      console.log(markdown)
      console.log(html)
    },
    imageUpload() {
      // arg[0]: image_index,
      // arg[1]: file
    },
    imageRemove() {}
  }
}
</script>

<style scoped>
@import './writeArticle.css';
</style>