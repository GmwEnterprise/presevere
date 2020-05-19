<script>
export default {
  name: 'system-wrapper',
  methods: {
    // eslint-disable-next-line no-unused-vars
    routeTo({ item, key, keyPath }) {
      // console.log(`${item}---${key}---${keyPath}`)
      this.$router.push({
        path: `/admin/${keyPath}`
      })
    }
  }
}
</script>

<template>
  <a-layout id="components-layout-demo-fixed-sider">
    <a-layout-sider :style="{ overflow: 'auto', height: '100vh', position: 'fixed', left: 0 }">
      <div class="logo" />
      <a-menu theme="dark" mode="inline" :default-selected-keys="['products']" @select="routeTo">
        <a-menu-item key="products">
          <a-icon type="user" />
          <span class="nav-text">产品信息管理</span>
        </a-menu-item>
        <a-menu-item key="users">
          <a-icon type="video-camera" />
          <span class="nav-text">管理员信息</span>
        </a-menu-item>
      </a-menu>
    </a-layout-sider>
    <a-layout :style="{ marginLeft: '200px' }">
      <a-layout-header :style="{ background: '#fff', padding: 0 }">
        <!-- <div
          style="width: 100%; height: 100%; display: flex; align-items: center; margin-left: 2em;"
        >
          <a-breadcrumb separator=">">
            <a-breadcrumb-item @click="routeToAdmin">主页</a-breadcrumb-item>
            <a-breadcrumb-item href>产品信息管理</a-breadcrumb-item>
          </a-breadcrumb>
        </div> -->
      </a-layout-header>
      <a-layout-content :style="{ margin: '24px 16px 0', overflow: 'initial' }">
        <div :style="{ padding: '24px', background: '#fff', textAlign: 'center' }">
          <!-- ...
          <br />
          Really
          <br />...<br />...<br />...<br />
          long
          <br />...<br />...<br />...<br />...<br />...<br />...
          <br />...<br />...<br />...<br />...<br />...<br />...
          <br />...<br />...<br />...<br />...<br />...<br />...
          <br />...<br />...<br />...<br />...<br />...<br />...
          <br />...<br />...<br />...<br />...<br />...<br />...
          <br />...<br />...<br />...<br />...<br />...<br />...
          <br />...<br />...<br />...<br />...<br />...<br />
          content-->
          <router-view></router-view>
        </div>
      </a-layout-content>
      <a-layout-footer :style="{ textAlign: 'center' }">Ant Design ©2018 Created by Ant UED</a-layout-footer>
    </a-layout>
  </a-layout>
</template>
<style>
#components-layout-demo-fixed-sider .logo {
  height: 32px;
  background: rgba(255, 255, 255, 0.2);
  margin: 16px;
}
</style>
