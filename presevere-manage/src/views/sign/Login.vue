<template>
  <div id="login">
    <el-card class="login-card">
      <div slot="header">
        <span>登录</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="register">切换为注册</el-button>
      </div>
      <el-form :model="loginForm" :rules="rules" ref="loginRuleForm">
        <el-form-item label="登录账户" prop="loginName">
          <el-input v-model="loginForm.loginName"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="loginForm.password" type="password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('loginRuleForm')">登录</el-button>
          <el-button @click="resetForm('loginRuleForm')">清除</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data() {
    return {
      loginForm: {
        loginName: '',
        password: ''
      },
      rules: {
        loginName: [
          { required: true, message: '请输入登录账户', trigger: 'blur' },
          {
            min: 5,
            max: 20,
            message: '登陆账户长度在5到20个字符之间',
            trigger: 'blur'
          }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          {
            min: 5,
            max: 20,
            message: '密码长度在5到20个字符之间',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  methods: {
    register() {
      this.$router.replace({
        path: '/register'
      })
    },
    resetForm(refName) {
      this.$refs[refName].resetFields()
    },
    submitForm(refName) {
      this.$refs[refName].validate(valid => {
        if (valid) {
          // TODO
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style scoped>
.login-card {
  width: 25rem;
}
div#login {
  height: 100vh;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>