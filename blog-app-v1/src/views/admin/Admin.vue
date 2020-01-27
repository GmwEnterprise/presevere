<template>
  <v-app id="inspire">
    <v-content>
      <v-container class="fill-height" fluid>
        <v-row align="center" justify="center">
          <v-col cols="12" sm="8" md="4">
            <v-card class="elevation-12">
              <v-toolbar color="primary" dark flat>
                <v-toolbar-title>Login as Admin</v-toolbar-title>
              </v-toolbar>
              <v-card-text>
                <v-form>
                  <v-text-field
                    v-model="username"
                    label="Username"
                    prepend-icon="person"
                    type="text"
                  />
                  <v-text-field
                    v-model="password"
                    label="Password"
                    prepend-icon="lock"
                    type="password"
                  />
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-spacer />
                <v-btn color="primary" @click="submit()">Login</v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
export default {
  name: 'admin',
  data: () => ({
    key: '',
    username: '',
    password: ''
  }),
  props: {
    source: String
  },
  created() {
    this.key = this.$route.params.key
    if (this.key !== '909498022@qq.com') {
      this.$router.go(-1)
    }
  },
  methods: {
    submit() {
      if (this.username !== 'root' || this.password !== 'root') {
        // Login failed
        return
      }
      // Success
    }
  }
}
</script>