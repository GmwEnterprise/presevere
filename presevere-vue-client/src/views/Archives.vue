<template>
  <div>
    <fix-button @click="drawer = !drawer" class="mobile-show">
      <i class="el-icon-more"></i>
    </fix-button>
    <el-drawer direction="btt" :visible.sync="drawer" size="40%" :show-close="false">
      <template #title>
        <span style="font-weight:bold;line-height:1.5;font-size:1.5rem;display:block">选择年份</span>
      </template>
      <template #default>
        <div class="flex-wrapper">
          <div
            v-for="year of archive.historyYears"
            :key="year"
            :class="`year-item link-style ${archive.currentYear === year ? 'year-item-current' : ''}`"
            @click="chooseYear(year, archive.currentYear !== year)"
          >
            <span>{{ year }}</span>
          </div>
        </div>
      </template>
    </el-drawer>
    <el-row>
      <el-col :sm="16" :xs="24">
        <template v-for="(metadataList, month) in archive.byMonth">
          <div class="block-title" :key="month">
            <span>{{ `${archive.currentYear} / ${month}` }}</span>
          </div>
          <div
            v-for="metadata of metadataList"
            :key="metadata.id"
            class="archive-item link-style"
            @click="routeToPost(metadata.urlNumber)"
          >
            <el-card shadow="never" style="border-radius:0">
              <div class="archive-item-title">
                <span>{{ metadata.title }}</span>
              </div>
              <p class="archive-item-introduction">{{ metadata.introduction }}</p>
            </el-card>
          </div>
        </template>
      </el-col>
      <el-col :sm="8" class="hidden-xs-only side-bar">
        <div
          v-for="year of archive.historyYears"
          :key="year"
          :class="`sidebar-item${year === archive.currentYear ? ' current-sidebar-item' : ''}`"
        >
          <span
            :class="year !== archive.currentYear ? `link-style link-style-underline` : ''"
            @click="chooseYear(year, archive.currentYear !== year)"
          >{{ year }}</span>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'Archives',
  data() {
    return {
      drawer: false,
      archive: {}
    }
  },
  mounted() {
    this.chooseYear(null, true)
  },
  methods: {
    chooseYear(year, canIuse) {
      this.drawer = false
      if (canIuse) {
        // alert('choose year: ' + year)
        this.axios
          .get(`/article/archive/${year ? year : new Date().getFullYear()}`)
          .then(res => (this.archive = res.data))
          .catch(() => {
            // console.log(err)
            alert('网络繁忙，请稍后再试.')
          })
      }
    },
    routeToPost(url) {
      // alert(`the post url is ${url}`)
      this.$router.push({
        path: `/post/${url}`
      })
    }
  }
}
</script>

<style scoped>
.flex-wrapper {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  width: calc(100% - 3rem);
  height: calc(100% - 3rem);
  align-content: flex-start;
  margin: 1.5rem;
}
.year-item {
  width: calc(100% / 5);
  height: calc(100% / 4);
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
}
.year-item:hover {
  background-color: #43c2cb;
}
.year-item-current:hover {
  background-color: #fff;
  border: 1px solid #43c2cb;
}
.year-item::before {
  content: '';
  position: absolute;
  top: 0.5rem;
  bottom: 0.5rem;
  left: 0.5rem;
  right: 0.5rem;
  background-color: #43c2cb;
  z-index: 0;
}
.year-item-current::before {
  background-color: #fff;
}
.year-item > span {
  z-index: 1;
  color: #fff;
}
.year-item-current > span {
  color: #43c2cb;
}
@media screen and (min-width: 768px) {
  .mobile-show {
    display: none;
  }
}
.link-style:hover {
  cursor: pointer;
}
.link-style-underline:hover {
  text-decoration: underline;
}
.block-title {
  margin-top: 1.2rem;
  margin-bottom: 1.2rem;
  font-size: 1.1rem;
  font-weight: bold;
  color: #444;
  line-height: 1.5;
}
.sidebar-item {
  margin-top: 1.2rem;
  margin-bottom: 1.2rem;
  font-size: 1rem;
  color: #444;
  line-height: 1.5;
}
.current-sidebar-item {
  font-weight: bold;
  font-size: 1.1rem;
}
.archive-item {
  margin-bottom: 1.2rem;
}
.archive-item-title {
  font-size: 1.15rem;
  font-weight: bold;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.archive-item-introduction {
  margin-bottom: 0;
  line-height: 1.5rem;
  font-size: 1rem;
  max-height: 3rem;
  overflow: hidden;
}
.side-bar {
  padding-left: 1.2rem;
}
</style>