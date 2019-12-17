<template>
  <div class="user-msg-wrapper">
    <el-tabs v-model="activeTab" @tab-click="switchingTabs">
      <el-tab-pane label="基本信息" name="basic">
        <el-form
          :ref="basicRef"
          :model="user"
          :rules="userBasicRule"
          label-width="6rem"
          label-position="left"
        >
          <el-form-item label="昵称：" prop="nickname">
            <el-input v-model="user.nickname" type="text"></el-input>
          </el-form-item>
          <el-form-item label="手机号：" prop="phone">
            <el-input v-model="user.phone" type="text"></el-input>
          </el-form-item>
          <el-form-item label="邮箱：" prop="email">
            <el-input v-model="user.email" type="text"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              @click="onSubmit"
              v-loading.fullscreen.lock="fullscreenLoading"
            >保存修改</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="头像" name="avatar"></el-tab-pane>
      <el-tab-pane label="密码管理" name="password"></el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { validatePhone, validateEmail } from '@/utils/validator.util.js'
import tokenService from '@/services/token.service.js'
const currentUserId = tokenService.currentUserId()
export default {
  name: 'UserMessage',
  data() {
    return {
      fullscreenLoading: false,
      basicRef: 'basicRef',
      activeTab: 'basic',
      user: {
        id: 0,
        nickname: '',
        phone: '',
        email: ''
      },
      userBasicRule: {
        nickname: [
          {
            min: 2,
            max: 18,
            message: '昵称长度在2-18位字符之间',
            trigger: 'blur'
          },
          {
            validator(rule, value, callback) {
              if (value.trim().length < 1) {
                callback(new Error('昵称不能为空!'))
              } else {
                window.axios
                  .get('/user/nicknameUsed', {
                    params: {
                      userId: currentUserId,
                      nickname: value
                    }
                  })
                  .then(res =>
                    res.data ? callback(new Error('昵称已被使用!')) : callback()
                  )
                  .catch(() => callback(new Error('网络错误, 请稍后尝试')))
              }
            },
            trigger: 'blur'
          }
        ],
        phone: [
          {
            validator: validatePhone,
            trigger: 'blur'
          },
          {
            validator(rule, value, callback) {
              if (value) {
                window.axios
                  .get('/user/phoneUsed', {
                    params: {
                      userId: currentUserId,
                      phone: value
                    }
                  })
                  .then(res =>
                    res.data
                      ? callback(new Error('手机号已被使用!'))
                      : callback()
                  )
                  .catch(() => callback(new Error('网络错误, 请稍后尝试')))
              } else {
                callback()
              }
            },
            trigger: 'blur'
          }
        ],
        email: [
          {
            validator: validateEmail,
            trigger: 'blur'
          },
          {
            validator(rule, value, callback) {
              if (value) {
                window.axios
                  .get('/user/emailUsed', {
                    params: {
                      userId: currentUserId,
                      email: value
                    }
                  })
                  .then(res =>
                    res.data ? callback(new Error('邮箱已被使用!')) : callback()
                  )
                  .catch(() => callback(new Error('网络错误, 请稍后尝试')))
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
  mounted() {
    this.axios.get('/user/currentUser').then(res => {
      this.user.id = res.data.id
      this.user.nickname = res.data.nickname
      this.user.phone = res.data.phone
      this.user.email = res.data.email
    })
  },
  methods: {
    onSubmit() {
      // console.log(e)
      this.$refs[this.basicRef].validate(valid => {
        if (valid) {
          // 提交
          this.fullscreenLoading = true
          this.axios
            .post('/user/modify', this.user)
            .then(() =>
              this.$message({
                message: '保存成功!',
                type: 'success',
                center: true
              })
            )
            .finally(() => (this.fullscreenLoading = false))
        } else {
          return false
        }
      })
    },
    switchingTabs(/*tab, event*/) {
      // console.log(tab, event)
    }
  }
}
</script>

<style scoped>
.user-msg-wrapper {
  max-width: 30rem;
  margin: 0 auto;
}
</style>