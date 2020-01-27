<template>
  <v-app>
    <v-navigation-drawer app v-model="drawerAboutMe" bottom></v-navigation-drawer>

    <v-app-bar app dense>
      <!-- <v-app-bar-nav-icon></v-app-bar-nav-icon> -->
      <v-btn icon @click="drawerAboutMe = !drawerAboutMe">
        <v-icon>mdi-information-variant</v-icon>
      </v-btn>
      <v-toolbar-title>Mrag's Blogs</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn text>ARCHIVES</v-btn>
      <v-btn text>CATEGORIES</v-btn>
      <v-btn text>CONTACT</v-btn>
    </v-app-bar>

    <v-content>
      <v-container fluid>
        <v-row>
          <v-col>
            <v-row>
              <v-col v-for="n in 15" :key="`item-${n}`" cols="4">
                <v-card>
                  <v-card-title>
                    <router-link class="title" :to="`/post/${125}`">This is title of a post_[{{ n }}].</router-link>
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
          </v-col>
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
  })
}
</script>

<style lang="scss" scoped>
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