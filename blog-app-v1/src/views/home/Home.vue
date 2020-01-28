<template>
  <v-app>
    <v-navigation-drawer app v-model="drawerAboutMe" bottom></v-navigation-drawer>

    <v-app-bar app dense>
      <v-btn icon @click="drawerAboutMe = !drawerAboutMe">
        <v-icon>mdi-information-variant</v-icon>
      </v-btn>
      <v-toolbar-title class="d-none d-sm-flex">来了就别跑了</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn text @click="routeTo('archives')">归档</v-btn>
      <v-btn text>分类</v-btn>
      <v-btn text>关于</v-btn>
    </v-app-bar>

    <v-content>
      <v-container fluid>
        <v-row>
          <v-col md="8">
            <v-row>
              <v-col v-for="n in 16" :key="`item-${n}`" xs="12" sm="6">
                <v-card>
                  <v-card-title>
                    <router-link
                      class="title"
                      :to="`/post/${125}`"
                    >This is title of a post_[{{ n }}].</router-link>
                  </v-card-title>
                  <v-card-text class="outline">
                    <template
                      v-for="i in n"
                    >[{{ i }}]Here is the outline of this post, it maybe a long text block, but it has limits.</template>
                  </v-card-text>
                  <v-card-subtitle style="display: flex">
                    <span>
                      <v-icon small>mdi-eye</v-icon>&nbsp;150
                      <i class="separator"></i>
                      <v-icon small>mdi-voice</v-icon>&nbsp;23
                    </span>
                    <v-spacer></v-spacer>
                    <span>
                      <v-icon small>mdi-calendar-range</v-icon>&nbsp;2020-01-01
                    </span>
                  </v-card-subtitle>
                </v-card>
              </v-col>
            </v-row>
          </v-col>
          <v-col md="4" class="d-none d-md-flex">
            <div style="width: 100%; padding: 1em;">
              <h3>标签</h3>
              <div class="right-block tags">
                <a class="link-style" href="#" v-for="a in 40" :key="`tag${a}`" :style="`font-size:${1 + a * 0.02}em`">java[{{ a }}]</a>
              </div>
              <h3>归档</h3>
              <div class="right-block archives">
                <a class="archive-item link-style" href="#" v-for="a in 40" :key="`time${a}`">2020年1月【{{ a * 5 }}】</a>
              </div>
            </div>
          </v-col>
        </v-row>
        <v-row>
          <v-col class="paging">
            <v-pagination
              v-model="page"
              :length="4"
              circle
              prev-icon="mdi-menu-left"
              next-icon="mdi-menu-right"
            ></v-pagination>
          </v-col>
        </v-row>
        <v-row>
          <v-col class="foot">This is the foot of content block.</v-col>
        </v-row>
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
export default {
  data: () => ({
    drawer: null,
    page: 1,
    drawerAboutMe: false
  }),
  methods: {
    routeTo(path) {
      this.$router.push(`/${path}`)
    }
  }
}
</script>

<style lang="scss" scoped>
.archives {
  display: flex;
  flex-direction: column;
}
.archive-item {
  display: block;
}
.right-block {
  padding: 2.5em 0;
}
.tags > a {
  display: inline-block;
  padding: 2px;
}
.link-style {
  color: gray;
  text-decoration: none;
  &:hover {
    color: #000;
    font-weight: bold;
  }
}
.title {
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
  color: inherit;
  text-decoration: none;
}
.separator {
  display: inline-block;
  width: 1em;
}
.foot {
  display: flex;
  justify-content: center;
  align-items: center;
  color: gray;
}
</style>