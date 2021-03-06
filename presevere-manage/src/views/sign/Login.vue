<template>
  <div id="login">
    <el-card class="login-card">
      <div slot="header">
        <span>登录</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="register">切换为注册</el-button>
      </div>
      <el-form :model="loginForm" :rules="rules" ref="loginRuleForm">
        <el-form-item label="账号" prop="loginName">
          <el-input v-model="loginForm.loginName"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="loginForm.password" type="password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="loginForm.keep">保持登录状态</el-checkbox>
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
import { validateCode, validatePsdReg1 } from '@/utils/validator.util.js'
import { symmetricEncryptionEncode } from '@/utils/security.util.js'
import tokenService from '@/services/token.service.js'
export default {
  name: 'Login',
  data() {
    return {
      // 重定向url
      redirectUrl: '',
      // 表单数据
      loginForm: {
        loginName: '',
        password: '',
        keep: false
      },
      // 表单验证规则
      rules: {
        loginName: [
          // { required: true, message: '请输入登录账户', trigger: 'blur' },
          {
            // element表单自定义校验
            // 若校验不通过则返回callback(new Error('错误原因'))
            // 通过则返回callback()
            validator: (rules, value, callback) => {
              if (value && value === 'admin') {
                callback()
              } else {
                validateCode(rules, value, callback)
              }
            },
            trigger: 'blur'
          }
        ],
        password: [
          {
            validator: (rules, value, callback) => {
              if (value && value === 'admin') {
                callback()
              } else {
                validatePsdReg1(rules, value, callback)
              }
            },
            trigger: 'blur'
          }
        ]
      },
      submitting: false
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
      if (this.submitting) return
      this.submitting = true
      this.$refs[refName].validate(valid => {
        if (valid) {
          this.login()
        } else {
          return false
        }
      })
    },
    async login() {
      this.startLoading()
      try {
        // 验证用户名
        await this.axios.get(`/sign/verifyUsername/1/${this.loginForm.loginName}`)
        // 登录
        const result = await this.axios.post('/sign/login', {
          loginName: this.loginForm.loginName,
          password: symmetricEncryptionEncode(this.loginForm.password),
          keepLogin: this.loginForm.keep
        })
        // 保存登录凭据
        tokenService.setToken(result.data.token, result.data.userId)
        // 登录成功后跳转并提示信息
        this.$message({
          message: '登录成功',
          type: 'success',
          center: true
        })
        this.$router.push({
          path: this.redirectUrl || '/'
        })
      } catch (error) {
        // console.log('catch exp !')
        // console.error(error)
        this.submitting = false
      } finally {
        this.stopLoading()
      }
    }
  },
  mounted() {
    tokenService.removeToken()
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