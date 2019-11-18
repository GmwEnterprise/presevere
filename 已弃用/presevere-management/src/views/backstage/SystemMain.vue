<template>
  <div class="container-fluid">
    <div class="row" style="background-color: white;box-shadow: 0 1px 3px rgba(26, 26, 26, 0.1);">
      <div class="col-12" style="display: flex; justify-content: space-between;">
        <router-link to="/sys" :style="logoStyle" class="logo"></router-link>
        <button
          class="btn btn-link current-user-style"
          @click="currentUser()"
        >{{ user.nickname }}</button>
      </div>
    </div>
    <div class="row" style="margin-top: .5rem;">
      <div class="col-2 catalog">
        <a
          class="catalog-link"
          @click="accordion($event, 'c-s-data-manage')"
          href="javascript:void(0)"
        >
          <i class="fa fa-table" aria-hidden="true"></i> 数据管理
        </a>
        <div class="catalog-sub" id="c-s-data-manage" style="display: none;">
          <template v-if="routerList.length > 0">
            <!-- 路径获取 -->
            <router-link
              v-for="(router, idx) of routerList"
              :key="idx"
              :to="`/sys/modules/${router.routerName}`"
            >{{ router.routerTitle }}</router-link>
          </template>
          <a v-else href="javascript:void(0)">没有数据</a>
        </div>
        <a
          class="catalog-link"
          @click="accordion($event, 'c-s-user-manage')"
          href="javascript:void(0)"
        >
          <i class="fa fa-bars" aria-hidden="true"></i> 界面管理
        </a>
        <div class="catalog-sub" id="c-s-user-manage" style="display: none;">
          <router-link to="/sys/modules/sysRouter">添加路由</router-link>
        </div>
      </div>
      <div class="col-10" style="padding: 0;">
        <div class="content-wrapper">
          <router-view />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import logo from '@/assets/persevere.png'
import SysRouterService from './sysRouter/sysRouter.service.js'
export default {
  data() {
    return {
      routerList: [],
      logoStyle: {
        backgroundImage: `url(${logo})`,
        backgroundSize: 'contain',
        display: 'inline-block',
        width: '13rem',
        height: '3rem',
        backgroundRepeat: 'no-repeat',
        margin: '.5rem'
      },
      user: {}
    }
  },
  methods: {
    userInit() {
      if (localStorage.getItem('token') != null) {
        this.user = JSON.parse(localStorage.getItem('currentUser'))
      }
    },
    currentUser() {
      this.$message({
        title: '用户信息',
        detail: this.user,
        btnName: '注销',
        event: async close => {
          localStorage.removeItem('token')
          localStorage.removeItem('currentUser')
          close()
        }
      })
    },
    routerInit() {
      SysRouterService.queryPage().then(response => {
        this.routerList = response.data.list
      })
    },
    accordion(event, targetId) {
      event.target.classList.toggle('active')
      const target = document.getElementById(targetId)
      if (target.style.display === 'none') {
        target.style.display = 'block'
      } else {
        target.style.display = 'none'
      }
      if (target.style.maxHeight) {
        target.style.maxHeight = null
      } else {
        target.style.maxHeight = target.scrollHeight + 'px'
      }
    }
  },
  created() {
    this.routerInit()
    this.userInit()
  }
}
</script>

<style>
div.catalog {
  margin: 0;
  padding: 0;
  height: calc(100vh - 4.5rem);
  background-color: white;
  box-shadow: 0 1px 3px rgba(26, 26, 26, 0.1);
  overflow-y: scroll;
}
div.content-wrapper {
  height: calc(100vh - 4.5rem);
  background-color: white;
  box-shadow: 0 1px 3px rgba(26, 26, 26, 0.1);
  width: calc(100% - 0.6rem);
  margin-left: 0.6rem;
  overflow: scroll;
  padding: 2rem;
}
a.catalog-link {
  display: block;
  height: 3rem;
  line-height: 3rem;
  font-size: 1.1rem;
  text-align: center;
  color: #0072bc;
  transition: 0.4s;
  overflow: hidden;
}
a.catalog-link.active {
  background-color: #e6e6e6;
}
a.catalog-link:hover {
  text-decoration: none;
  background-color: #e6e6e6;
  cursor: pointer;
}
.catalog-link:after {
  content: '+';
  color: #0072bc;
  width: 2rem;
  font-size: 1.1rem;
  float: right;
  margin-right: 0.5rem;
  text-align: center;
}
.active:after {
  content: '-';
}
div.catalog-sub {
  width: 100%;
  overflow: hidden;
  max-height: 0;
  transition: max-height 0.2s ease-out;
  background-color: white;
}
div.catalog-sub > a {
  display: block;
  font-size: 1rem;
  text-align: center;
  line-height: 2.4rem;
  background-color: white;
  margin: 0.4rem 0;
  color: #777;
  transition: 0.4s;
}
div.catalog-sub > a:hover {
  color: #111;
  text-decoration: none;
}
.current-user-style {
  line-height: 3rem;
  height: 3rem;
  margin: 0.5rem 1.5rem;
  color: black;
  margin-right: 300px;
}
</style>