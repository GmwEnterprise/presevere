<template>
  <div>
    <h2>我的简历</h2>
    <el-form ref="resumeForm" :model="resume"></el-form>
  </div>
</template>

<script>
export default {
  name: 'Resume',
  data() {
    return {
      resume: {
        // 基本信息
        name: 'MC阿甘', // 姓名
        age: 24, // 年龄
        startWorkTime: '2018年7月', // 开始工作时间
        sex: '男', // 性别
        educationBackground: '本科', // 教育背景
        professionalSkill: '', // 专业技能描述

        // 工作经历
        workExperience: [
          {
            from: '', // 开始时间
            to: '', // 结束时间
            enterpriseName: '', // 企业名称
            position: '', // 职位
            primaryTask: '', // 主要任务
          }
        ],

        // 参与项目经验
        projectExperience: [
          {
            from: '', // 开始时间
            to: '', // 结束时间
            projectName: '', // 项目名称,
            projectDescription: '', // 项目描述
            myTask: '', // 我的工作
          }
        ],

        // 其他信息
        otherMessage: ''
      }
    }
  },
  methods: {},
  mounted() {}
}
</script>

<style scoped>
</style>