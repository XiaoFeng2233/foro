<script setup>
import {defineProps, reactive} from "vue"
import {getInfoByUserId} from "@/api/user.js"
import {forbiddenUser} from "@/api/admin.js"
import {ElMessage} from "element-plus"
import moment from "moment";


const prop = defineProps(["id"])

const form = reactive({
  id: '',
  username: '',
  permanent: false,
  endTime: '',
  reason: ""
})

const getUserInfo = async () => {
  const response = await getInfoByUserId(prop.id)
  form.id = response.data.id
  form.username = response.data.username
}

await getUserInfo()

const doForbiddenUser = async () => {
  if (form.reason === "" || form.reason === undefined || form.reason === null) {
    ElMessage.error({
      message: "原因不能为空",
      duration: 1500
    })
    return false;
  }
  if (form.permanent === true) {
    form.endTime = "3000-01-01 12:00:00"
  } else {
    if (form.endTime === undefined || form.endTime === null || form.endTime === "") {
      ElMessage.error({
        message: "截止日期不能为空",
        duration: 1500
      })
      return false
    }
  }

  if (moment().isAfter(moment(form.endTime))) {
    ElMessage.error({
      message: "截止日期不能早于当前时间",
      duration: 1500
    })
    return false;
  }


  const response = await forbiddenUser(form.id,  moment(form.endTime).format("YYYY-MM-DD HH:mm:ss"),form.reason)
  if(response.code === 200){
    form.endTime = ""
    form.username = ""
    form.reason = ""
    form.permanent = false
    form.id = ""

    ElMessage.success({
      message: response.data,
      duration: 1500
    })

    //1.5秒后刷新网页
    setTimeout(() => {
      window.location.reload()
    }, 1500)
  }
}
</script>

<template>
  <el-form size="large" label-position="top">
    <el-form-item label="用户ID">
      <el-input v-model="form.id" disabled/>
    </el-form-item>
    <el-form-item label="用户名">
      <el-input v-model="form.username" disabled/>
    </el-form-item>
    <el-form-item label="封禁原因">
      <el-input v-model="form.reason"/>
    </el-form-item>
    <el-form-item label="永久封禁">
      <el-switch
          v-model="form.permanent"
          inline-prompt
          active-text="是"
          inactive-text="否"
      />
    </el-form-item>
    <el-form-item label="封禁截止时间" v-if="!form.permanent">
      <el-date-picker
          v-model="form.endTime"
          type="datetime"
          placeholder="选择截止时间"
      />
    </el-form-item>

    <el-form-item>
      <el-button @click="doForbiddenUser" type="primary">封禁</el-button>
    </el-form-item>
  </el-form>
</template>

<style scoped>

</style>