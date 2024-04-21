<template>
  <AdminLayout>
    <el-card header="邮箱配置">
      <el-form ref="formRef" label-position="top" size="large" :model="data" :rules="rules">
        <el-form-item label="发信服务器地址" prop="host">
          <el-input v-model="data.host" placeholder="请输入发信服务器地址 例:smtp.163.com" clearable/>
        </el-form-item>
        <el-form-item label="发信服务器端口" prop="port">
          <el-input-number v-model="data.port" placeholder="请输入发信服务器端口 例:465" :min="1" :max="65535"
                           style="width: 20%"/>
        </el-form-item>
        <el-form-item label="邮箱账号" prop="username">
          <el-input v-model="data.username" placeholder="请输入发信邮箱账号" clearable/>
        </el-form-item>
        <el-form-item label="邮箱密码" prop="password">
          <el-input v-model="data.password" placeholder="请输入发信邮箱密码" show-password clearable type="password"/>
        </el-form-item>
        <el-form-item label="邮箱完整地址" prop="address">
          <el-input v-model="data.address" placeholder="请输入发信邮箱完整地址 例:xxx@163.com" clearable/>
        </el-form-item>
        <el-form-item label="邮箱开启SSL" prop="useSSL">
          <el-switch
              v-model="data.useSSL"
              active-text="开启"
              inactive-text="关闭"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="doUpdateEmailConfig">设置</el-button>
        </el-form-item>
      </el-form>

    </el-card>
  </AdminLayout>
</template>
<script setup>
import {reactive, ref} from "vue"
import AdminLayout from "@/layout/AdminLayout.vue";
import {getEmailConfig, updateEmailConfig} from "@/api/admin.js"
import {ElNotification} from "element-plus"

const formRef = ref()
const data = reactive({
  host: "",
  port: undefined,
  username: "",
  password: "",
  address: "",
  useSSL: false
})

const rules = {
  host: {required: true, message: '请输入发信服务器地址', trigger: 'blur'},
  port: {required: true, message: '请输入发信服务器端口', trigger: 'blur'},
  username: {required: true, message: '请输入邮箱账号', trigger: 'blur'},
  password: {required: true, message: '请输入邮箱密码', trigger: 'blur'},
  address: {required: true, message: '请输入邮箱完整地址', trigger: 'blur'},
  useSSL: {required: true, message: '请配置是否开启SSL', trigger: 'blur'},
}

const doUpdateEmailConfig = async () => {
  formRef.value.validate(async valid => {
    if (valid) {
      const useSSL = data.useSSL ? 1 : 0
      const response = await updateEmailConfig(data.host, data.port, data.username, data.password, data.address, useSSL)
      if (response.code === 200) {
        ElNotification({
          type:"success",
          title: '成功',
          message: "更新成功"
        })
        getEmailConfigData()

        //1.5秒刷新页面
        setTimeout(() => {
          window.location.reload()
        }, 1500)
      }
    }
  })
}


const getEmailConfigData = async () => {
  const response = await getEmailConfig()
  if (response.code === 200) {
    data.host = response.data.host
    data.port = response.data.port
    data.username = response.data.username
    data.password = response.data.password
    data.address = response.data.address
    data.useSSL = response.data.useSSL
  }
}

getEmailConfigData()

</script>
<style scoped>

</style>