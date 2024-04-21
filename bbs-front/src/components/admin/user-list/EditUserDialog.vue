<script setup>
import {defineProps, reactive, ref} from "vue"
import {getInfoByUserId} from "@/api/user.js"
import {ElMessage} from "element-plus"
import {updateUser} from "@/api/admin.js"


const prop = defineProps(["id"])
const formRef = ref()
const formData = reactive({
  id: "",
  username: "",
  email: undefined,
  nickname: undefined,
  description: undefined,
  score: undefined,
  password: undefined,
  rePassword: undefined
})

const copyFormData = reactive({
  id: "",
  username: "",
  email: undefined,
  nickname: undefined,
  description: undefined,
  score: undefined,
  password: undefined,
  rePassword: undefined
})

const rules = {
  email: [{required: true, message: '邮箱不能为空', trigger: 'blur'}, {
    type: "email",
    message: '邮箱格式有误',
    trigger: 'blur'
  }],
  nickname: [{required: true, message: '昵称不能为空', trigger: 'blur'}],
  description: [{required: true, message: '个性签名不能为空', trigger: 'blur'}],
  score: [{required: true, message: '积分数量不能为空', trigger: 'blur'}],
}

const doGetUser = async () => {
  const response = await getInfoByUserId(prop.id)
  if (response.code === 200) {
    formData.id = response.data.id
    formData.username = response.data.username
    formData.email = response.data.email
    formData.nickname = response.data.nickname
    formData.description = response.data.description
    formData.score = response.data.score

    copyFormData.id = response.data.id
    copyFormData.username = response.data.username
    copyFormData.email = response.data.email
    copyFormData.nickname = response.data.nickname
    copyFormData.description = response.data.description
    copyFormData.score = response.data.score
  }
}
await doGetUser()

const doUpdateUser = async () => {
  formRef.value.validate(async valid => {
    if (valid) {
      if (formData.password !== undefined || formData.password !== "") {
        if (formData.password !== formData.rePassword) {
          ElMessage.error("两次密码不一致")
          return false;
        }
      }
      const response = await updateUser(formData.id,
          formData.email !== copyFormData.email ? formData.email : undefined,
          formData.nickname !== copyFormData.nickname ? formData.nickname : undefined,
          formData.description !== copyFormData.description ? formData.description : undefined,
          formData.score !== copyFormData.score ? formData.score : undefined,
          formData.password)
      if (response.code === 200) {
        ElMessage.success(response.data)
        //清空form中的数据
        formData.id = undefined
        formData.username = undefined
        formData.email = undefined
        formData.nickname = undefined
        formData.description = undefined
        formData.score = undefined

        //1.5秒后刷新网页
        setTimeout(() => {
          window.location.reload()
        }, 1500)
      }
    }
  })
}
</script>

<template>
  <el-form label-position="top" ref="formRef" :rules="rules" :model="formData">
    <el-form-item label="id">
      <el-input disabled v-model="formData.id"></el-input>
    </el-form-item>
    <el-form-item label="用户名">
      <el-input disabled v-model="formData.username"></el-input>
    </el-form-item>
    <el-form-item label="邮箱" prop="email">
      <el-input v-model="formData.email"></el-input>
    </el-form-item>
    <el-form-item label="昵称" prop="nickname">
      <el-input v-model="formData.nickname"></el-input>
    </el-form-item>
    <el-form-item label="个性签名" prop="description">
      <el-input v-model="formData.description"></el-input>
    </el-form-item>
    <el-form-item label="积分数量" prop="score">
      <el-input-number :min="0" v-model="formData.score"/>
    </el-form-item>
    <el-form-item label="修改密码">
      <el-input type="password" clearable v-model="formData.password"></el-input>
    </el-form-item>
    <el-form-item label="确认密码">
      <el-input type="password" clearable v-model="formData.rePassword"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="doUpdateUser">修改</el-button>
    </el-form-item>
  </el-form>

</template>

<style scoped>

</style>