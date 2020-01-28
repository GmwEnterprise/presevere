<template>
  <v-app>
    <v-app-bar app dense>
      <v-toolbar-title>文章归档</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn text @click="routeTo('')">首页</v-btn>
      <v-btn text>分类</v-btn>
      <v-btn text>关于</v-btn>
    </v-app-bar>

    <v-content>
      <v-container>
        <v-row>
          <v-col>
            <!-- 时间线 -->
            <v-timeline dense>
              <v-timeline-item v-for="n in 20" :key="`key[${n}]`" color="#FFF">
                <template #icon>
                  <v-icon>mdi-calendar-clock</v-icon>
                </template>
                <template #opposite>2020/1</template>
                <template #default>
                  <v-card class="elevation-2">
                    <v-card-title class="headline">2020年1月</v-card-title>
                    <v-card-text>
                      <a
                        class="post"
                        href="#"
                        v-for="i in 20"
                        :key="`post${i}`"
                      >This is post_ {{ `[${i}]` }}This is post_ {{ `[${i}]` }}This is post_ {{ `[${i}]` }}This is post_ {{ `[${i}]` }}</a>
                    </v-card-text>
                  </v-card>
                </template>
              </v-timeline-item>
            </v-timeline>
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
  methods: {
    routeTo(path) {
      this.$router.push(`/${path}`)
    }
  }
}
</script>

<style lang="scss" scoped>
.post {
  font-size: 1em;
  display: inline-block;
  line-height: 1.5;
  padding-right: .5em;
  color: gray;
  width: 18em;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  &:hover {
    color: #000;
    font-weight: bold;
  }
}
.foot {
  display: flex;
  justify-content: center;
  align-items: center;
  color: gray;
}
</style>