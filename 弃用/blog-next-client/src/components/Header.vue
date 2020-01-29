<template>
  <md-toolbar class="md-dense">
    <h1 :class="titleClass" @click="click" style="flex: 1">{{ title }}</h1>
    <md-button v-if="!home" class="md-xsmall-hide" @click="routeTo('home')">Home</md-button>
    <md-button class="md-xsmall-hide" @click="routeTo('archives')">Archives</md-button>
    <md-button class="md-xsmall-hide" @click="routeTo('topics')">Topics</md-button>
    <md-button class="md-xsmall-hide" @click="routeTo('contact')">Contact</md-button>
  </md-toolbar>
</template>

<script>
export default {
  name: 'myHeader',
  props: {
    title: {
      type: String,
      default: ''
    },
    linkStyle: {
      type: Boolean,
      default: false
    },
    home: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      titleClass: 'oneLine ' + (this.linkStyle ? 'link' : '')
    }
  },
  methods: {
    click(e) {
      this.$emit('click', e)
    },
    routeTo(routerName) {
      this.$router.push({
        name: routerName
      })
    }
  }
}
</script>

<style scoped>
.link:hover {
  cursor: pointer;
}
.oneLine {
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
  line-height: 1.2;
}
</style>