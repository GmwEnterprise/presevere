<template>
  <div class="form-wrapper">
    <form class="form" @submit="submitForm" novalidate>
      <div class="form-group">
        <input
          class="form-control"
          type="text"
          v-model="formData.username.value"
          required
          placeholder="请输入用户名"
          @change="validUsername"
        />
        <div class="invalid-feedback">用户名由8-20位数字、字母组成</div>
      </div>
      <div class="form-group">
        <input
          class="form-control"
          type="password"
          v-model="formData.password.value"
          required
          placeholder="请输入密码"
          @change="validPassword"
        />
        <div class="invalid-feedback">密码由6-20位数字、字母组成</div>
      </div>
      <div class="form-group" v-if="isReg">
        <input
          class="form-control"
          type="password"
          v-model="formData.repeatPassword.value"
          required
          placeholder="再次输入密码"
          @change="validRepeatPassword"
        />
        <div class="invalid-feedback">两次输入密码不一致</div>
      </div>
      <div class="form-group" v-if="isReg">
        <input
          class="form-control"
          type="text"
          v-model="formData.nickname.value"
          required
          placeholder="您的昵称"
          @change="validNickname"
        />
        <div class="invalid-feedback">昵称由1-18位字节组成，首位无空白符</div>
      </div>
      <button class="btn btn-primary" type="submit">{{ submitTitle }}</button>
      <div></div>
      <a class="reg-link" href="javascript:void(0)" @click="stateChange()">点此{{ changeTitle }}</a>
    </form>
  </div>
</template>

<script>
import SignState from './SignState'
import StringUtil from '../tools/string-utils'
import Sign from './sign.service'
export default {
  name: 'Sign',
  data() {
    return {
      redirectPath: '/sys',
      formData: {
        username: {
          value: '',
          valid: false
        },
        password: {
          value: '',
          valid: false
        },
        repeatPassword: {
          value: '',
          valid: false
        },
        nickname: {
          value: '',
          valid: false
        }
      },
      state: SignState.LOGIN
    }
  },
  computed: {
    isReg() {
      return this.state === SignState.REGISTER
    },
    submitTitle() {
      return this.state === SignState.REGISTER ? '注册' : '登录'
    },
    changeTitle() {
      return this.state !== SignState.REGISTER ? '注册' : '登录'
    }
  },
  created() {
    const redirectPath = this.$route.query.redirect
    if (redirectPath) {
      this.redirectPath = redirectPath
    }
  },
  methods: {
    stateChange() {
      if (this.state === SignState.LOGIN) {
        this.state = SignState.REGISTER
      } else {
        this.state = SignState.LOGIN
      }
    },
    submitForm(e) {
      e.preventDefault()
      const regValid =
        this.formData.username.valid &&
        this.formData.password.valid &&
        this.formData.repeatPassword.valid &&
        this.formData.nickname.valid
      const logValid =
        this.formData.username.valid && this.formData.password.valid
      if (this.state === SignState.LOGIN) {
        // 登录
        if (logValid) {
          Sign.login(
            this.formData.username.value,
            this.formData.password.value
          ).then(response => {
            localStorage.setItem('token', response.data.token)
            localStorage.setItem(
              'currentUser',
              JSON.stringify(response.data.userMessage)
            )
            this.$toast.success(
              `您好, ${response.data.userMessage.nickname}`,
              '登录成功'
            )
            this.$router.push(this.redirectPath)
          })
        }
      } else {
        // 注册
        if (regValid) {
          Sign.register(
            this.formData.username.value,
            this.formData.password.value,
            this.formData.nickname.value
          ).then(response => {
            localStorage.setItem('token', response.data.token)
            localStorage.setItem(
              'currentUser',
              JSON.stringify(response.data.userMessage)
            )
            this.$toast.success(
              `欢饮您, ${response.data.userMessage.nickname}`,
              '注册成功，自动登录'
            )
            this.$router.push(this.redirectPath)
          })
        } else {
          console.log('invalid')
        }
      }
    },
    validUsername(e) {
      console.log(e.target.classList)
      console.log(typeof e.target.classList)
      if (/^[a-zA-Z0-9_-]{8,20}$/.test(this.formData.username.value)) {
        this.formData.username.valid = true
        this.replaceClass(e.target, 'is-invalid', 'is-valid')
      } else {
        this.formData.username.valid = false
        this.replaceClass(e.target, 'is-valid', 'is-invalid')
      }
    },
    validPassword(e) {
      if (/^[a-zA-Z0-9_-]{6,20}$/.test(this.formData.password.value)) {
        this.formData.password.valid = true
        this.replaceClass(e.target, 'is-invalid', 'is-valid')
      } else {
        this.formData.password.valid = false
        this.replaceClass(e.target, 'is-valid', 'is-invalid')
      }
    },
    validRepeatPassword(e) {
      if (this.formData.password.value === this.formData.repeatPassword.value) {
        this.formData.repeatPassword.valid = true
        this.replaceClass(e.target, 'is-invalid', 'is-valid')
      } else {
        this.formData.repeatPassword.valid = false
        this.replaceClass(e.target, 'is-valid', 'is-invalid')
      }
    },
    validNickname(e) {
      const len = StringUtil.strlen(this.formData.nickname.value)
      if (len > 18 || len < 1) {
        this.formData.nickname.valid = false
        this.replaceClass(e.target, 'is-valid', 'is-invalid')
      } else {
        this.formData.nickname.valid = true
        this.replaceClass(e.target, 'is-invalid', 'is-valid')
      }
    },
    replaceClass(dom, oldClass, newClass) {
      dom.classList.remove(oldClass)
      dom.classList.add(newClass)
    }
  }
}
</script>

<style scoped>
.form-wrapper {
  height: 100vh;
  width: 100vw;
  display: flex;
  align-items: center;
  justify-content: center;
}
.form {
  width: 400px;
  height: auto;
  background-color: white;
  padding: 50px;
  box-shadow: 0 0px 10px lightgrey;
}
.reg-link {
  font-size: 0.8em;
  display: inline-block;
  margin-top: 10px;
}
</style>