<template>
  <div class="nav-wrapper">
    <el-row class="hidden-xs-only">
      <!-- pc only -->
      <el-col :span="7">
        <div class="logo-wrapper nav-height">
          <router-link class="logo-pc" to="/"></router-link>
        </div>
      </el-col>
      <el-col :span="17" class="nav-router-wrapper">
        <router-link to="/archives" class="nav-router nav-height plain-link">归档</router-link>
        <router-link to="/catalog" class="nav-router nav-height plain-link">目录</router-link>
        <router-link to="/about" class="nav-router nav-height plain-link">关于</router-link>
        <a
          href="javascript:void(0)"
          @click="drawerSubscribe = true"
          class="nav-router nav-height plain-link"
        >订阅</a>
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
          <div class="flex-wrapper" style="margin-top:2rem">
            <el-button type="text" @click="mobileSubscribe">订阅本站</el-button>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <div id="global-catalog-element-mounter-mobile"></div>
        </el-col>
      </el-row>
    </el-drawer>
    <el-drawer direction="btt" :visible.sync="drawerSubscribe" size="40%" :show-close="false">
      <template #title>
        <span style="font-weight:bold;line-height:1.5;font-size:1.5rem;display:block">订阅我的邮件</span>
      </template>
      <template #default>
        <div class="flex-wrapper">
          <el-input
            placeholder="输入你的常用邮箱"
            v-model="subscribeEmail"
            style="width:80%;max-width:500px"
          >
            <template #append>
              <el-button @click="subscribe">订阅</el-button>
            </template>
          </el-input>
        </div>
      </template>
    </el-drawer>
  </div>
</template>

<script>
import qs from 'qs'
export default {
  name: 'CommonNav',
  data() {
    return {
      subscribeEmail: '',
      drawerSubscribe: false,
      drawerDisplay: false
    }
  },
  methods: {
    routeTo(path) {
      this.$refs['mobileDrawer'].closeDrawer()
      this.$router.push({
        path
      })
    },
    subscribe() {
      const reg = /^([a-zA-Z0-9]+[-_.]?)+@[a-zA-Z0-9]+\.[a-z]+$/
      if (reg.test(this.subscribeEmail)) {
        // 通过验证
        this.axios
          .post(
            '/article/subscribe',
            qs.stringify({
              email: this.subscribeEmail
            })
          )
          .then(() => {
            // console.log(res)
            alert('订阅成功!')
            this.drawerSubscribe = false
          })
          .catch(errRes => {
            // console.log(errRes)
            alert(errRes.data)
            this.drawerSubscribe = false
          })
      } else {
        alert('请输入正确的邮箱.')
      }
    },
    mobileSubscribe() {
      this.drawerDisplay = false
      setTimeout(() => (this.drawerSubscribe = true), 300)
    }
  }
}
</script>

<style scoped>
@import './common-nav.css';
</style>