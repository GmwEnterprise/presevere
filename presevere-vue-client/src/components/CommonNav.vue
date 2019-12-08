<template>
  <div class="nav-wrapper">
    <el-row class="hidden-xs-only">
      <el-col :span="7">
        <div class="logo-wrapper nav-height">
          <router-link class="logo-pc" to="/"></router-link>
        </div>
      </el-col>
      <el-col :span="17" class="nav-router-wrapper">
        <router-link to="/archives" class="nav-router nav-height plain-link">归档</router-link>
        <router-link to="/catalog" class="nav-router nav-height plain-link">目录</router-link>
        <router-link to="/about" class="nav-router nav-height plain-link">关于</router-link>
        <a href="javascript:void(0)" class="nav-router nav-height plain-link">订阅</a>
      </el-col>
    </el-row>

    <!-- mobile only -->
    <el-row class="mobile-only">
      <el-col :span="5" class="nav-height">
        <i @click="drawerDisplay = true" class="el-icon-s-operation nav-icon link-hover-style"></i>
      </el-col>
      <el-col :span="14">
        <div class="logo-wrapper nav-height flex-wrapper">
          <router-link class="logo-mobile" to="/"></router-link>
        </div>
      </el-col>
    </el-row>

    <!-- mobile drawer -->
    <el-drawer
      ref="mobileDrawer"
      size="70%"
      direction="ltr"
      :visible.sync="drawerDisplay"
      :modal="true"
      :show-close="false"
    >
      <el-row>
        <el-col :offset="6" :span="12">
          <img src="@/assets/images/presevere-thin.png" class="drawer-logo" />
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <div class="flex-wrapper" style="margin-top:2rem">
            <el-breadcrumb separator="/">
              <el-breadcrumb-item>
                <span class="link-hover-style" @click="routeTo('/archives')">归档</span>
              </el-breadcrumb-item>
              <el-breadcrumb-item>
                <span class="link-hover-style" @click="routeTo('/catalog')">目录</span>
              </el-breadcrumb-item>
              <el-breadcrumb-item>
                <span class="link-hover-style" @click="routeTo('/about')">关于</span>
              </el-breadcrumb-item>
            </el-breadcrumb>
          </div>
        </el-col>
      </el-row>
    </el-drawer>
  </div>
</template>

<script>
export default {
  name: 'CommonNav',
  data() {
    return {
      drawerDisplay: false
    }
  },
  methods: {
    routeTo(path) {
      this.$refs['mobileDrawer'].closeDrawer()
      this.$router.push({
        path
      })
    }
  }
}
</script>

<style scoped>
@import './common-nav.css';
</style>