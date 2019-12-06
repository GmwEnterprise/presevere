<template>
  <div>
    <el-menu class="flex-box" mode="horizontal">
      <el-menu-item index="1">首页</el-menu-item>
      <el-menu-item index="2">分类</el-menu-item>
      <el-menu-item index="3">归档</el-menu-item>
    </el-menu>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'Home'
}
</script>

<style scoped>
.flex-box {
  display: none;
}
@media screen and (min-width: 750px) {
  .flex-box {
    display: block;
  }
}
</style>