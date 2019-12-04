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
    <div class="write-wrapper" style="display:flex">
      <input
        id="tagString"
        ref="tagStringInputRef"
        type="text"
        v-model="tagString"
        :placeholder="tagPlaceholder"
        @input="detectTagValue"
        @keypress="addTag"
        @change="outputChange"
        maxlength="20"
        :disabled="tagIsFull"
      />
    </div>
    <div class="write-wrapper" style="display:flex;justify-content:center">
      <!-- <span class="tag" v-for="(tag, i) of draft.tags" :key="i">{{ tag }}</span> -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item v-for="(tag, i) of draft.tags" :key="i">
          <el-button
            class="customize-btn-style"
            type="text"
            size="small"
            :title="tagBtnTitle"
            @click="deleteTag(i)"
            :id="`tag-btn-${i}`"
          >{{ tag }}</el-button>
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="write-wrapper">
      <!-- box-shadow:none !important; -->
      <mavon-editor
        ref="mavon"
        style="border:none"
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
      <el-button type="text" :round="true" size="medium" @click="publish" :disabled="saveFlag">
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
      contentInit: true,
      tagIsFull: false, // 标签数量是否已达上限
      allowAddTag: true, // 是否允许添加标签（用于鉴别重复标签）
      allowDeleteTag: true, // 是否允许删除标签（重复情况下不允许删除）
      tagPlaceholder: '添加标签，回车确认',
      tagBtnTitle: '点击删除',
      duplicateId: null,
      tagString: '',
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
        content: null,
        tags: null
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
  watch: {
    tagString(newVal, oldVal) {
      if (newVal.indexOf(',') >= 0) {
        this.tagString = oldVal
      }
    }
  },
  components: {
    mavonEditor
  },
  mounted() {
    const draftId = this.$route.params.draftId
    if (draftId) {
      const loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      this.axios
        .get(`/article/draft/${draftId}`)
        .then(res => {
          this.draft.key = res.data.urlNumber
          this.draft.title = res.data.title
          this.draft.introduction = res.data.introduction
          this.draft.tags = res.data.tags ? res.data.tags.split(',') : []
          this.draft.markdown = res.data.content ? res.data.content : ''
        })
        .finally(() => loading.close())
    }
  },
  methods: {
    detectTagValue() {
      const index = this.draft.tags.indexOf(this.tagString)
      if (index >= 0) {
        // tag重复
        this.allowDeleteTag = false
        this.tagBtnTitle = ''
        if (this.allowAddTag) {
          this.$refs['tagStringInputRef'].setAttribute('style', 'color:red')
          const id = `tag-btn-${index}`
          document.getElementById(id).setAttribute('style', 'color:red')
          this.duplicateId = id
        }
        this.allowAddTag = false
      } else {
        this.allowDeleteTag = true
        this.tagBtnTitle = '点击删除'
        if (!this.allowAddTag) {
          this.$refs['tagStringInputRef'].removeAttribute('style')
          document.getElementById(this.duplicateId).removeAttribute('style')
        }
        this.allowAddTag = true
      }
    },
    deleteTag(i) {
      if (!this.allowDeleteTag) return
      this.draft.tags.splice(i, 1)
      this.saveTags()
      if (this.draft.tags.length < 5) {
        this.tagIsFull = false
        this.tagPlaceholder = '添加标签，回车确认'
      }
    },
    outputChange(e) {
      console.log(e)
    },
    addTag(e) {
      if (e.keyCode === 13 && this.allowAddTag) {
        this.draft.tags.push(this.tagString)
        this.saveTags()
        this.tagString = ''
        if (this.draft.tags.length >= 5) {
          // 最大长度为5
          this.tagIsFull = true
          this.tagPlaceholder = '最多允许5个标签'
        }
      }
    },
    saveTitle() {
      this.saving()
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
      }, 1000)
    },
    saveIntroduction() {
      this.saving()
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
      }, 1000)
    },
    saveTags() {
      this.saving()
      if (this.timer.tags) {
        clearTimeout(this.timer.tags)
      }
      this.timer.tags = setTimeout(() => {
        this.articleSave({
          tags: this.draft.tags
        }).finally(() => {
          clearTimeout(this.timer.tags)
          this.timer.tags = null
        })
      }, 1000)
    },
    saveContent() {
      this.saving()
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
      }, 1000)
    },
    async articleSave(param) {
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
      if (!this.contentInit) {
        this.saveContent()
      } else {
        this.contentInit = false
      }
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
        this.axios
          .post(
            '/article/publish',
            qs.stringify({
              key: this.draft.key
            })
          )
          .then(() => {
            this.$message({
              message: '发布成功!',
              type: 'success',
              center: true
            })
            this.$router.push({
              path: '/home/article/drafts'
            })
          })
      }
    }
  }
}
</script>

<style scoped>
@import './writeArticle.css';
</style>