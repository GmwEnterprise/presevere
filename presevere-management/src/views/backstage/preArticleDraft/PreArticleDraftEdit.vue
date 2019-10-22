<template>
  <div style="margin-top: 3.5em;">
    <div class="header">
      <h3 class="show-title" :title="showTitle">{{ showTitle }}</h3>
      <button id="pub">发布</button>
    </div>
    <div class="draft-head">
      <input
        class="form-control"
        type="text"
        v-model="data.title"
        placeholder="请输入标题"
        @change="showTitleChange"
      />
      <input
        class="form-control"
        type="text"
        v-model="data.introduction"
        placeholder="写一段介绍"
        @input="introChange"
      />
    </div>
    <div class="draft-body">
      <mark-down v-bind="markdownProps" @on-save="mdsave" />
    </div>
    <div class="draft-foot">
      <input
        class="form-control"
        type="text"
        v-model="data.tag"
        @input="draftTagChange"
        @keypress="enterPress"
        placeholder="请输入文章分类，逗号分隔"
        style="margin-top: 30px;"
      />
      <div class="tag-list" v-show="tagList">
        <span
          class="tag-item"
          v-for="(tag, i) of tagList"
          :key="i"
          @click="removeTag(tag)"
        >{{ tag }}</span>
      </div>
    </div>
  </div>
</template>

<script>
import MarkDown from 'vue-meditor'
import preArticleDraftService from './preArticleDraft.service.js'
export default {
  name: 'PreArticleDraftEdit',
  components: {
    MarkDown
  },
  data() {
    return {
      markdownProps: {
        autoSave: false
      },
      // 1-add, 2-modify
      editType: 1,
      showTitle: '',
      data: {
        id: null,
        // 标题
        title: null,
        // 创建者主键
        creator: null,
        // 介绍
        introduction: null,
        // 标签分类
        tag: null,
        // 内容
        content: null,
        contentType: 'markdown'
      },
      tagList: [],
      introTimer: null
    }
  },
  methods: {
    introChange() {
      if (this.introTimer) {
        clearTimeout(this.introTimer)
      }
      this.introTimer = setTimeout(() => {
        console.log('intro timer')
        const prom = preArticleDraftService.saveIntroduction(this.data)
        prom != null
          ? prom.then(response => {
              if (response.data) {
                this.data.id = response.data.id
              }
            })
          : 0
      }, 1000)
    },
    showTitleChange() {
      this.showTitle = this.data.title
      const prom = preArticleDraftService.saveTitle(this.data)
      prom != null
        ? prom.then(response => {
            if (response.data) {
              this.data.id = response.data.id
            }
          })
        : 0
    },
    removeTag(tag) {
      this.tagList.splice(this.tagList.indexOf(tag), 1)
    },
    tagListPush(tag) {
      console.log(this.tagList.indexOf(tag))
      if (this.tagList.indexOf(tag) === -1) {
        this.tagList.push(tag)
      }
    },
    draftTagChange(e) {
      if (e.data === ',') {
        console.log(e)
        let tagStr = this.data.tag
        tagStr = tagStr.substring(0, tagStr.length - 1)
        this.tagListPush(tagStr)
      }
    },
    enterPress(e) {
      if (e.charCode === 13 && this.data.tag) {
        this.tagListPush(this.data.tag)
        this.data.tag = ''
      }
    },
    mdsave(param) {
      this.data.content = param.value
      console.log(this.data.content)
    },
    async queryData(key) {
      const response = await preArticleDraftService.queryByKey(key)
      if (response.data) {
        this.data = response.data
      }
    },
    /**
     * @param {Event} e
     */
    submitForm(e) {
      e.preventDefault()
      let flag, promise
      if (this.data.id) {
        // 修改
        promise = preArticleDraftService.modify(this.data)
        flag = '修改'
      } else {
        // 新增
        promise = preArticleDraftService.add(this.data)
        flag = '新增'
      }
      promise
        .then(() => {
          this.$toast.success(`${flag}成功`)
          this.$router.push({
            name: 'preArticleDraftList'
          })
        })
        .catch((code, msg) => {
          this.$toast.error(`${flag}失败: ${msg}`, `错误编码: ${code}`)
        })
    },
    returnPage() {
      this.$router.go(-1)
    }
  },
  mounted() {
    this.data.id = this.$route.params.rowId
    if (this.data.id) {
      console.log('修改')
      this.editType = 2
      this.queryData(this.data.id)
    } else {
      console.log('新增')
      this.editType = 1
    }
  }
}
</script>

<style scoped>
.edit-form {
  width: 60%;
  margin: 0 auto;
}
.edit-page-btn-wrapper > button {
  margin-right: 1rem;
}
.draft-head > input {
  margin-bottom: 20px;
}
.tag-list {
  margin-top: 10px;
  display: flex;
}
.tag-item {
  background-color: #0072bc;
  color: white;
  font-size: 1em;
  line-height: 2em;
  padding-left: 0.5em;
  margin-right: 1em;
  cursor: pointer;
}
.tag-item::after {
  content: '×';
  font-size: 1em;
  line-height: 2em;
  padding: 0 0.3em;
  margin-left: 0.3em;
}
.header {
  display: flex;
  position: absolute;
  z-index: 10;
  background-color: white;
  box-sizing: border-box;
  top: 0;
  left: 0.6rem;
  right: 0;
  box-shadow: rgba(26, 26, 26, 0.1) 0 1px 3px;
}
.show-title {
  margin: 0;
  line-height: 2.1em;
  height: 2.1em;
  padding-left: 1em;
  width: 80%;
  box-sizing: border-box;
  padding-right: 1em;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
#pub {
  border: 0;
  color: white;
  background-color: #0072bc;
  padding: 0 1em;
}
</style>