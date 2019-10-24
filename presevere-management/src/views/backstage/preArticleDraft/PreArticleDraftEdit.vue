<template>
  <div style="margin-top: 3.5em;">
    <div class="header" :style="headerStyleZIndex">
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
      <mavon-editor v-model="data.content" @change="mdsave" @fullScreen="markdownEditorFullScreenChange" />
      <!-- <mark-down v-bind="markdownProps" @on-save="mdsave" /> -->
    </div>
    <div class="draft-foot">
      <input
        class="form-control"
        type="text"
        v-model="tagString"
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
import preArticleDraftService from './preArticleDraft.service.js'
export default {
  name: 'PreArticleDraftEdit',
  data() {
    return {
      // 1-add, 2-modify
      editType: 1,
      showTitle: '',
      tagString: '',
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
        content: '',
        contentType: 'markdown',
        htmlRender: ''
      },
      tagList: [],
      introTimer: null,
      headerStyleZIndex: {
        zIndex: 2000
      }
    }
  },
  methods: {
    markdownEditorFullScreenChange(status) {
      if (status) {
        // 设置header的z-index小于1501
        this.headerStyleZIndex.zIndex = 1000
      } else {
        // 设置header的z-index大于1501
        this.headerStyleZIndex.zIndex = 2000
      }
    },
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
      preArticleDraftService.removeTag(this.data.id, tag)
    },
    tagListPush(tag) {
      console.log(this.tagList.indexOf(tag))
      if (this.tagList.indexOf(tag) === -1) {
        this.tagList.push(tag)
        preArticleDraftService.pushNewTag(this.data.id, tag).then(response => {
          if (response.data) {
            this.data.id = response.data.id
          }
        })
      }
    },
    draftTagChange(e) {
      if (e.data === ',') {
        console.log(e)
        let tagStr = this.tagString
        tagStr = tagStr.substring(0, tagStr.length - 1)
        this.tagListPush(tagStr)
        this.tagString = ''
      }
    },
    enterPress(e) {
      if (e.charCode === 13 && this.tagString) {
        this.tagListPush(this.tagString)
        this.tagString = ''
      }
    },
    mdsave(value, render) {
      /**
       * 编辑便触发保存操作，设置延迟执行；
       * 若持续编辑，则覆盖延迟执行器
       */
      if (this.mdSaveTimer) {
        clearTimeout(this.mdSaveTimer)
      }
      // 内容发生变化后延迟1500ms执行
      this.mdSaveTimer = setTimeout(() => {
        preArticleDraftService
          .pushContent(this.data.id, this.data.content, render)
          .then(response => {
            if (response.data) {
              this.data.id = response.data.id
            }
          })
      }, 1500)
    },
    async queryData(key) {
      const response = await preArticleDraftService.queryByKey(key)
      if (response.data) {
        this.data = response.data
        if (!this.data.content) {
          this.data.content = ''
        }
        if (this.data.tag) {
          const tags = this.data.tag.split(',')
          this.tagList = tags
          this.data.tag = ''
        }
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
    this.data.id = this.$route.query.rowId
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
  font-size: 0.8em;
  line-height: 2rem;
  padding-left: 0.5rem;
  margin-right: 1rem;
  cursor: pointer;
}
.tag-item::after {
  content: '×';
  font-size: 1rem;
  line-height: 2rem;
  padding: 0 0.3rem;
  margin-left: 0.3rem;
}
.header {
  display: flex;
  position: absolute;
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