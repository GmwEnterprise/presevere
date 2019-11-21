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
import Security from '../../services/security.js'
export default {
  name: 'Login',
  data() {
    return {
      // 重定向url
      redirectUrl: '',
      // 表单数据
      loginForm: {
        loginName: '',
        password: ''
      },
      // 表单验证规则
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
      const options = {
        path: '/register'
      }
      if (this.redirectUrl) {
        options.query = {
          redirectUrl: this.redirectUrl
        }
      }
      this.$router.replace(options)
    },
    resetForm(refName) {
      this.$refs[refName].resetFields()
    },
    submitForm(refName) {
      this.$refs[refName].validate(valid => {
        if (valid) {
          this.login()
        } else {
          return false
        }
      })
    },
    async login() {
      // 验证用户名，验证成功获取盐值
      const res = await this.axios.get(
        `/sign/validUsername/${this.loginForm.loginName}`
      )
      // 判断是否需要前端加密
      let password
      if (res.data.frontEncoded) {
        password = Security.encode(this.loginForm.password, res.data.salt)
      } else {
        password = this.loginForm.password
      }
      // 登录
      const result = await this.axios.post('/sign/login', {
        loginName: this.loginForm.loginName,
        password
      })
      // 保存登录凭据
      localStorage.setItem('token', result.data.token)
      // 登录成功后跳转并提示信息
      this.$message({
        message: '登录成功',
        type: 'success',
        center: true
      })
      this.$router.push({
        path: this.redirectUrl || '/'
      })
    }
  },
  mounted() {
    localStorage.removeItem('token')
    if (this.$route.query.redirectUrl) {
      this.redirectUrl = this.$route.query.redirectUrl
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