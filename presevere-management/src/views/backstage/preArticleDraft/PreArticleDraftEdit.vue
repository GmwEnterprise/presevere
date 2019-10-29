<template>
  <div style="margin-top: 3.5em;">
    <div class="header" :style="headerStyleZIndex">
      <h3 class="show-title" :title="showTitle">{{ showTitle }}</h3>
      <button id="pub" @click="publish">发布</button>
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
      <mavon-editor
        ref="md"
        v-model="data.content"
        :tabSize="4"
        @change="mdsave"
        @fullScreen="markdownEditorFullScreenChange"
        @imgAdd="addImage"
        @imgDel="removeImage"
      />
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
        zIndex: 21
      }
    }
  },
  computed: {
    showTitle() {
      return this.data.title ? this.data.title : ''
    }
  },
  methods: {
    /**
     * 所有草稿内容都已经保存好了的
     * 发布的时候，重定向到列表页
     * 并将草稿删除
     * 检查必填项是否完整
     */
    publish() {
      // 检查标题
      if (!this.data.title) {
        alert('输入标题')
        return
      }
      // 检查内容
      if (!this.data.content) {
        alert('请编辑内容')
        return
      }
      // 提交data.id的发布任务
      preArticleDraftService.publish(this.data.id)
      // 重定向回到列表页
      this.$router.push({
        name: 'preArticleDraftList',
        replace: true
      })
    },
    addImage(pos, image) {
      console.log(pos)
      console.log(image)
      const formData = new FormData()
      formData.append('image', image)
      this.axios({
        url: '/app/preArticleImageStore/upload',
        method: 'post',
        data: formData,
        headers: { 'Content-Type': 'multipart/form-data' }
      }).then(response => {
        const callbackUrl = response.data
        this.$refs.md.$img2Url(pos, `http://127.0.0.1:4200/${callbackUrl}`)
      })
    },
    removeImage() {},
    markdownEditorFullScreenChange(status) {
      if (status) {
        // 设置header的z-index小于20
        this.headerStyleZIndex.zIndex = 19
      } else {
        // 设置header的z-index大于20
        this.headerStyleZIndex.zIndex = 21
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
    mdsave() {
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
          .pushContent(this.data.id, this.data.content)
          .then(response => {
            if (response.data) {
              this.data.id = response.data.id
            }
          })
      }, 2500)
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