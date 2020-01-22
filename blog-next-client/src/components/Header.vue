<template>
  <md-toolbar class="md-dense">
    <h1 :class="(linkStyle ? 'link' : '')" @click="click" style="flex: 1">{{ title }}</h1>
    <md-button>Archives</md-button>
  </md-toolbar>
</template>

<script>
export default {
  name: "myHeader",
  props: {
    title: {
      type: String,
      default: ""
    },
    linkStyle: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    click(e) {
      this.$emit("click", e);
    }
  }
};
</script>

<style scoped>
.link:hover {
  cursor: pointer;
}
</style>