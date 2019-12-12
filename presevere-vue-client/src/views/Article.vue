<template>
  <div class="article-wrapper">
    <fix-button @click="back()">
      <i class="el-icon-caret-left"></i>
    </fix-button>
    <fix-button :bottom="80" :visibilityHeight="300">
      <i class="el-icon-caret-top"></i>
    </fix-button>
    <el-row>
      <el-col :sm="18" :xs="24" class="article-block-wrapper">
        <h1 class="title">{{ post.title }}</h1>
        <div class="markdown-body" id="md-article-wrapper"></div>
        <div class="article-footer">
          <span>发表于 {{ post.publishedTime }}</span>
          <span>分类: {{ post.tags }}</span>
        </div>
      </el-col>
      <el-col :sm="6" style="padding-left:1.2rem;position:relative" class="hidden-xs-only">
        <div
          id="global-catalog-element-mounter-pc"
          :style="pcCatalogWrapperStyle + 'overflow-y:auto;transition:max-height .3s;'"
        ></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import hljs from 'highlight.js'
import 'highlight.js/styles/github.css'
import 'github-markdown-css/github-markdown.css'
export default {
  name: 'ArticlePost',
  data() {
    return {
      urlNumber: 0,
      articlePositionTop: 0,
      catalogPosition: 'absolute',
      catalogBottomDistance: 'calc(80vh - 2.4rem)',
      post: {
        title: '',
        content: ''
      }
    }
  },
  computed: {
    pcCatalogWrapperStyle() {
      return `
        position:${this.catalogPosition};
        top:${this.articlePositionTop}rem;
        max-height:${this.catalogBottomDistance};`
    }
  },
  watch: {
    ['post.content']() {
      this.articleContainer.innerHTML = this.post.content
      this.generateTitleCatalog(this.articleContainer)
      this.articleContainer.querySelectorAll('pre code').forEach(block => {
        hljs.highlightBlock(block)
      })
    }
  },
  mounted() {
    this.articleContainer = document.getElementById('md-article-wrapper')
    const urlNumber = this.$route.params.urlNumber
    this.urlNumber = Number.parseInt(urlNumber)
    if (typeof this.urlNumber === 'number') {
      this.axios.get(`/article/post/${this.urlNumber}`).then(res => {
        console.log(res)
        this.post = res.data
      })
    } else {
      this.$router.push({
        path: '/'
      })
    }
    this.onScroll = event => {
      const scrollTop = event.target.scrollingElement.scrollTop
      if (scrollTop > 50) {
        this.articlePositionTop = 1.2
        this.catalogPosition = 'fixed'
        this.catalogBottomDistance = 'calc(100vh - 2.4rem)'
      } else {
        this.articlePositionTop = 0
        this.catalogPosition = 'absolute'
        this.catalogBottomDistance = 'calc(80vh - 2.4rem)'
      }
    }
    document.addEventListener('scroll', this.onScroll)
  },
  beforeDestroy() {
    document.removeEventListener('scroll', this.onScroll)
  },
  methods: {
    generateTitleCatalog() {
      // mobileContainer必须在侧栏展开后才能显示
      const pcContainer = document.querySelector(
        '#global-catalog-element-mounter-pc'
      )
      const mobileContainer = document.querySelector(
        '#global-catalog-element-mounter-mobile'
      )
      console.log(pcContainer, mobileContainer)
      // 获取目录元信息
      const metadataList = []
      this.articleContainer
        .querySelectorAll('h1,h2,h3,h4,h5,h6')
        .forEach((h, hIndex) => {
          const id = `article-head-title-${hIndex}`
          h.setAttribute('id', id)
          metadataList.push({
            title: h.innerHTML,
            hIndex: Number.parseInt(h.tagName.substring(1)),
            targetId: id
          })
        })
      // 构建目录树
      const rEC = (list, index, hIndex) => {
        if (index < 0) {
          return -1
        }
        if (list[index].hIndex < hIndex) {
          return list[index].current
        }
        return rEC(list, index - 1, hIndex)
      }
      metadataList.forEach((meta, index) => {
        meta.current = index
        meta.parent = rEC(metadataList, index - 1, meta.hIndex)
      })
      console.log(
        metadataList.map(item => ({
          current: item.current,
          parent: item.parent
        }))
      )

      // 此时树状数组基本结构已构建成功，接下来构建html
      const root = document.createElement('ul')
      root.setAttribute(
        'style',
        ` font-size:.9rem;
          line-height:1.6;
          list-style-type:none;
          padding:0;
          margin: 0;`
      )
      const generateFunc = (node, parentId) => {
        const children = metadataList.filter(item => item.parent === parentId)
        if (children.length) {
          children.forEach(meta => {
            const li = document.createElement('li')
            li.setAttribute(
              'style',
              'overflow:hidden;white-space:nowrap;text-overflow:ellipsis'
            )
            li.innerHTML += `<a${
              parentId === -1 ? ' style="font-weight:bold"' : ''
            } class="article-title-router" href="#${meta.targetId}" title="${
              meta.title
            }">${meta.title}</a>`
            const subUl = document.createElement('ul')
            generateFunc(subUl, meta.current)
            if (subUl.innerHTML.trim()) {
              li.appendChild(subUl)
            }
            node.appendChild(li)
          })
        }
      }
      generateFunc(root, -1)
      pcContainer.innerHTML += `<span class="article-catalog-head-title">目录</span>`
      pcContainer.appendChild(root)
    },
    back() {
      this.$router.go(-1)
    },
    backTop() {
      console.log(1)
    }
  }
}
</script>

<style>
.article-catalog-head-title {
  display: block;
  border-bottom: 0.5px solid #d0cfcf;
  font-size: 1rem;
  line-height: 1.2;
  padding-bottom: 0.5rem;
  margin-bottom: 0.5rem;
  font-weight: bold;
}
#global-catalog-element-mounter-pc {
  -ms-overflow-style: none;
  overflow: -moz-scrollbars-none;
}
#global-catalog-element-mounter-pc::-webkit-scrollbar {
  width: 0 !important;
}
#global-catalog-element-mounter-pc ul {
  list-style-type: none;
  padding: 0;
  margin-left: 1rem;
}
.article-title-router {
  color: #444;
  text-decoration: none;
}
.article-title-router:hover {
  text-decoration: underline;
}
</style>

<style scoped>
.article-footer {
  margin-top: 7rem;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  font-size: 0.85rem;
  font-style: italic;
  color: darkgray;
  line-height: 2;
}
.title {
  margin: 0.7em auto;
  border-bottom: 0.5px solid gray;
  padding-bottom: 0.5em;
  line-height: 1.5;
}
.markdown-body {
  font-size: 0.95rem;
}
.article-block-wrapper {
  background-color: white;
  padding: 1.2rem;
}
</style>


[0] 3
[1]   4
[2]   4
[3]     5
[4]   4
[5] 3
[6] 3
[7]   4