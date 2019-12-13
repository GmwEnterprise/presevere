<template>
  <div>
    <div
      id="markdown-editing"
      ref="mdEditor"
      contenteditable="true"
      @paste.prevent="paste"
      @keypress="keypress"
      @keyup="keyup"
    ></div>
    <button @click="showMsg" id="test">show message</button>
  </div>
</template>

<script>
export default {
  name: 'PresevereEditor',
  methods: {
    showMsg() {
      // 获取光标位置
      const selection = window.getSelection()
      // const range = selection.getRangeAt(0)
      console.log(selection.anchorNode)
      console.log(selection.anchorOffset)
      console.log(selection.focusNode)
      console.log(selection.focusOffset)
      console.log(selection.rangeCount)
    },
    /**
     * 保证粘贴的内容一定是纯文本，不能有任何其他元素
     */
    paste(e) {
      let pasteText = e.clipboardData.getData('text/plain')
      const lines = pasteText.split('\n')
      const length = lines.length
      for (let i = 0; i < length; i++) {
        document.execCommand('insertText', false, lines[i])
        if (i < length - 1) {
          document.execCommand('insertHTML', false, '<br/>')
        }
      }
    },
    keypress(e) {
      // 1. 修改Enter的动作
      if (e.key === 'Enter') {
        e.preventDefault()
        document.execCommand('insertHTML', false, '<br/>')
      }
    },
    keyup() {
      console.log(window.getSelection())
    }
  },
  mounted() {
    console.log(
      'insertBrOnReturn: ' + document.queryCommandSupported('insertBrOnReturn')
    )
  }
}
</script>

<style scoped>
#test {
  margin: 0 auto;
  display: block;
}
#markdown-editing {
  width: 400px;
  height: 200px;
  margin: 0 auto;
  background-color: #eee;
  white-space: nowrap;
  overflow-x: auto;
  overflow-y: auto;
}
#markdown-editing:focus {
  outline: none;
}
</style>