<template>
  <div>
    <div class="write-wrapper">
      <input id="title" type="text" v-model="draft.title" placeholder="输入标题" @input="saveTitle" />
    </div>
    <div class="write-wrapper">
      <input
        id="introduction"
        type="text"
        v-model="draft.introduction"
        placeholder="输入一段介绍（可不填写）"
        @input="saveIntroduction"
      />
    </div>
    <div class="write-wrapper">
      <!-- box-shadow:none !important; -->
      <mavon-editor
        ref="mavon"
        style="border:none;height:calc(100vh - 20rem);"
        v-model="draft.markdown"
        :toolbars="toolbars"
        @change="contentChange"
        @imgAdd="imageUpload"
        @imgDel="imageRemove"
        :tabSize="4"
        codeStyle="atom-one-dark"
        :boxShadow="false"
        :subfield="false"
      ></mavon-editor>
    </div>
    <div class="empty-bar"></div>
    <div id="publish">
      <el-button type="text" :round="true" size="medium" @click="publish">
        <i class="el-icon-upload el-icon--right"></i>
        {{ publishButtonWord }}
      </el-button>
    </div>
  </div>
</template>

<script>
import qs from 'qs'
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
export default {
  name: 'WriteArticleComponent',
  data() {
    return {
      draft: {
        key: null, // 唯一键
        title: '', // 标题
        introduction: '', // 介绍
        tags: [], // 标签
        markdown: '', // markdown内容
        render: '' // 渲染后的html
      },
      publishButtonWord: '发布',
      saveFlag: false,
      timer: {
        title: null,
        introduction: null,
        content: null
      },
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
  mounted() {
    const draftId = this.$route.params.draftId
    if (draftId) {
      this.axios.get(`/article/draft/${draftId}`).then(res => {
        this.draft.key = res.data.urlNumber
        this.draft.title = res.data.title
        this.draft.introduction = res.data.introduction
        this.draft.tags = res.data.tags ? res.data.tags.split(',') : []
        this.draft.markdown = res.data.content ? res.data.content : ''
      })
    }
  },
  methods: {
    saveTitle() {
      if (this.timer.title) {
        clearTimeout(this.timer.title)
      }
      this.timer.title = setTimeout(() => {
        this.articleSave({
          title: this.draft.title
        }).finally(() => {
          clearTimeout(this.timer.title)
          this.timer.title = null
        })
      }, 1500)
    },
    saveIntroduction() {
      if (this.timer.introduction) {
        clearTimeout(this.timer.introduction)
      }
      this.timer.introduction = setTimeout(() => {
        this.articleSave({
          introduction: this.draft.introduction
        }).finally(() => {
          clearTimeout(this.timer.introduction)
          this.timer.introduction = null
        })
      }, 1500)
    },
    saveContent() {
      if (this.timer.content) {
        clearTimeout(this.timer.content)
      }
      this.timer.content = setTimeout(() => {
        this.articleSave({
          markdown: this.draft.markdown,
          render: this.draft.render
        }).finally(() => {
          clearTimeout(this.timer.content)
          this.timer.content = null
        })
      }, 1500)
    },
    async articleSave(param) {
      this.saving()
      const res = await this.axios.post('/article/save', {
        ...param,
        key: this.draft.key || null
      })
      if (res.data) {
        this.draft.key = res.data
      }
      this.saved()
    },
    saving() {
      if (!this.saveFlag) {
        this.saveFlag = true
        this.publishButtonWord = '保存中...'
      }
    },
    saved() {
      if (this.saveFlag) {
        this.saveFlag = false
        this.publishButtonWord = '发布'
      }
    },
    // 内容变化回调函数
    contentChange(markdown, html) {
      this.draft.render = html
      this.saveContent()
    },
    // 上传图片回调
    imageUpload(image_index, file) {
      console.log(image_index)
      console.log(file)
    },
    // 移除图片回调
    imageRemove() {},
    // 发布文章按钮事件
    publish() {
      if (!this.draft.title || !this.draft.title.trim()) {
        this.$message({
          message: '标题不能为空',
          type: 'error',
          center: true
        })
      } else if (!this.draft.markdown || !this.draft.markdown.trim()) {
        this.$message({
          message: '内容不能为空',
          type: 'error',
          center: true
        })
      } else {
        // 发布
        console.log('published')
        this.axios
          .post(
            '/article/publish',
            qs.stringify({
              key: this.draft.key
            })
          )
          .then(() => {})
      }
    }
  }
}
</script>

<style scoped>
@import './writeArticle.css';
</style>