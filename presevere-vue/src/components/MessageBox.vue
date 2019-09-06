<template>
  <div id="customize-message-box">
    <!-- 删除提示框 -->
    <!-- Button trigger modal -->
    <!-- <button
      type="button"
      class="btn btn-primary"
      data-toggle="modal"
      data-target="#delete-message-box"
    >Launch demo modal</button>-->
    <!-- Modal -->
    <div
      class="modal fade"
      id="warning-message-box"
      tabindex="-1"
      role="dialog"
      aria-labelledby="warningMessageLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-body">
            <span class="message-icon warning-icon"></span>
            <h3 class="customize-message-box-title">{{ message.title }}</h3>
            <span class="customize-message-box-detail">{{ message.detail }}</span>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-danger" @click="doEvent">{{ message.btnName }}</button>
            <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
          </div>
        </div>
      </div>
    </div>

    <div
      class="modal fade"
      id="success-message-box"
      tabindex="-1"
      role="dialog"
      aria-labelledby="successMessageLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-body">
            <span class="message-icon success-icon"></span>
            <h3 class="customize-message-box-title">{{ message.title }}</h3>
            <span class="customize-message-box-detail">{{ message.detail }}</span>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-success" @click="doEvent">{{ message.btnName }}</button>
            <!-- <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button> -->
          </div>
        </div>
      </div>
    </div>

    <div
      class="modal fade"
      id="info-message-box"
      tabindex="-1"
      role="dialog"
      aria-labelledby="infoMessageLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-body">
            <h3 class="customize-message-box-title">{{ message.title }}</h3>
            <span class="customize-message-box-detail">{{ message.detail }}</span>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" @click="doEvent">{{ message.btnName }}</button>
            <!-- <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button> -->
          </div>
        </div>
      </div>
    </div>

    <div
      class="modal fade"
      id="error-message-box"
      tabindex="-1"
      role="dialog"
      aria-labelledby="errorMessageLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-body">
            <span class="message-icon error-icon"></span>
            <h3 class="customize-message-box-title">{{ message.title }}</h3>
            <span class="customize-message-box-detail">{{ message.detail }}</span>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-danger" @click="doEvent">{{ message.btnName }}</button>
            <!-- <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button> -->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import $ from 'jquery'
import { setTimeout } from 'timers'
export default {
  name: 'CustomizeMessageBox',
  data() {
    return {
      message: {
        type: 'info/warning/error/success',
        title: '确定删除吗？',
        detail: '该操作将不可逆！',
        btnName: '删除',
        event: null
      },
      currentEvent: null
    }
  },
  computed: {
    storeMessage() {
      return this.$store.state.messageBox
    }
  },
  watch: {
    storeMessage() {
      this.message = this.storeMessage
      switch (this.message.type) {
        case 'warning':
          $('#warning-message-box').modal('show')
          break
        case 'success':
          $('#success-message-box').modal('show')
          break
        case 'error':
          $('#error-message-box').modal('show')
          break
        default:
          $('#info-message-box').modal('show')
          break
      }
    }
  },
  methods: {
    /**
     * 执行事件
     */
    doEvent() {
      if (!this.currentEvent) {
        this.currentEvent = new Promise(resolve => {
          resolve(this.message.event && this.message.event())
        }).finally(() => {
          $(`#${this.message.type}-message-box`).modal('hide')
          setTimeout(() => {
            this.currentEvent = null
          }, 300)
        })
      }
    }
  }
}
</script>

<style>
.modal-content {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.3);
  border-radius: 0.4rem;
  border: 0;
}
.modal-footer {
  border-top: 0;
  justify-content: center;
}
.modal-body {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.customize-message-box-title {
  margin: 2rem 3rem 1rem;
  width: calc(100% - 6rem);
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  color: #555;
  font-weight: bold;
  text-align: center;
}
.customize-message-box-detail {
  text-align: center;
  margin: 0 2rem;
  color: #666;
}
.message-icon {
  width: 5em;
  height: 5em;
  border-radius: 50%;
  margin-top: 2rem;
}
.message-icon::before {
  display: flex;
  justify-content: center;
  height: 100%;
  align-items: center;
  font-size: 2.4em;
}
.warning-icon {
  border: 0.2rem solid #f8bb86;
}
.warning-icon::before {
  content: '!';
  color: #f8bb86;
}
.success-icon {
  border: 0.2rem solid #5fb513;
}
.success-icon::before {
  content: '√';
  color: #5fb513;
}
.error-icon {
  border: 0.2rem solid #ff7c7c;
}
.error-icon::before {
  content: '×';
  color: #ff7c7c;
}
</style>