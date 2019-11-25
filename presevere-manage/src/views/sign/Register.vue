<template>
  <div id="login">
    <el-card class="login-card">
      <div slot="header">
        <span>注册</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="login">切换到登录</el-button>
      </div>
      <el-form :model="loginForm" :rules="rules" ref="loginRuleForm">
        <el-form-item label="输入账号" prop="loginName">
          <el-input v-model="loginForm.loginName"></el-input>
        </el-form-item>
        <el-form-item label="输入密码" prop="password">
          <el-input v-model="loginForm.password" type="password"></el-input>
        </el-form-item>
        <el-form-item label="再次输入密码" prop="rePassword">
          <el-input v-model="loginForm.rePassword" type="password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="loginForm.keep">保持登录状态</el-checkbox>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('loginRuleForm')">注册新账号</el-button>
          <el-button @click="resetForm('loginRuleForm')">重新输入</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { validateCode, validatePsdReg1 } from '@/services/validator.js'
import Security from '../../services/security.js'
export default {
  name: 'Register',
  data() {
    return {
      // 重定向url
      redirectUrl: '',
      // 表单数据
      loginForm: {
        loginName: '',
        password: '',
        rePassword: '',
        keep: false
      },
      rules: {
        loginName: [
          { validator: validateCode, trigger: 'blur' }
        ],
        password: [
          { validator: validatePsdReg1, trigger: 'blur' }
        ],
        rePassword: [
          {
            validator: (rule, value, callback) => {
              if (value !== this.loginForm.password) {
                callback(new Error('两次输入密码不一致'))
              } else {
                callback()
              }
            },
            trigger: 'blur'
          }
        ]
      }
    }
  },
  methods: {
    login() {
      const options = {
        path: '/login'
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
          this.register()
        } else {
          return false
        }
      })
    },
    async register() {
      // 1. 发送loginName到后台，创建账户，生成数据，初始化密码盐，返回盐值
      const res = await this.axios.get(
        `/sign/randomSalt/${this.loginForm.loginName}`
      )
      // 2. 拿到返回的盐对密码进行加密，再次发送加密密码到后台，返回成功信息
      const encoded = Security.encode(this.loginForm.password, res.data.salt)
      const result = await this.axios.post('/sign/register', {
        loginName: res.data.username,
        password: encoded,
        keepLogin: this.loginForm.keep
      })
      // 保存登录凭据
      localStorage.setItem('token', result.data.token)
      // 注册成功后跳转并提示信息
      this.$message({
        message: '注册成功',
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