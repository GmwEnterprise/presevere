<template>
  <el-menu
    mode="horizontal"
    @select="handleSelect"
    menu-trigger="hover"
    :unique-opened="true"
    :router="false"
  >
    <el-submenu index="user">
      <template slot="title">欢迎您，{{ username }}</template>
      <el-menu-item :index="index.changeUsermsg">修改个人信息</el-menu-item>
      <el-menu-item :index="index.manageUserList">管理已注册用户</el-menu-item>
      <el-menu-item :index="index.logout">注销</el-menu-item>
    </el-submenu>
    <el-submenu index="article">
      <template slot="title">文章管理</template>
      <el-menu-item :index="index.publishedArticles">已发布文章</el-menu-item>
      <el-menu-item :index="index.draftArticles">编辑中文章(草稿)</el-menu-item>
      <el-menu-item :index="index.writeArticle">写文章</el-menu-item>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  data() {
    return {
      index: {
        changeUsermsg: '1-1',
        manageUserList: '1-2',
        logout: '1-3',
        publishedArticles: '2-1',
        draftArticles: '2-2',
        writeArticle: '2-3'
      }
    }
  },
  props: {
    username: {
      type: String,
      required: true
    }
  },
  methods: {
    changeUsermsg() {
      this.$router.push({
        path: '/home/user/modify'
      })
    },
    manageUserList() {
      this.$router.push({
        path: '/home/user/list'
      })
    },
    logout() {
      localStorage.removeItem('token')
      this.$router.push({
        path: '/login'
      })
    },
    publishedArticles() {
    },
    draftArticles() {
    },
    writeArticle() {
      this.$router.push({
        path: '/home/article/write'
      })
    },
    handleSelect(index, indexPath) {
      console.log(index, indexPath)
      switch (index) {
        case this.index.changeUsermsg:
          this.changeUsermsg()
          break
        case this.index.manageUserList:
          this.manageUserList()
          break
        case this.index.logout:
          this.logout()
          break
        case this.index.publishedArticles:
          this.publishedArticles()
          break
        case this.index.draftArticles:
          this.draftArticles()
          break
        case this.index.writeArticle:
          this.writeArticle()
          break
      }
    }
  }
}
</script>