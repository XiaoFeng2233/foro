<script setup>
import {reactive, ref} from 'vue'
import AdminLayout from "@/layout/AdminLayout.vue";
import {getStorageConfig, updateStorageConfig} from "@/api/admin.js"
import {ElNotification} from "element-plus"


const formRef = ref()
const data = reactive({
  endpoint: "",
  accessKey: "",
  secretKey: "",
})

const rules = {
  endpoint: {required: true, message: '请输入endpoint', trigger: 'blur'},
  accessKey: {required: true, message: '请输入accessKey', trigger: 'blur'},
  secretKey: {required: true, message: '请输入secretKey', trigger: 'blur'}
}

const doUpdateStorageConfig = async () => {
  formRef.value.validate(async valid => {
    if (valid) {
      const response = await updateStorageConfig(data.endpoint, data.accessKey, data.secretKey)
      if (response.code === 200) {
        ElNotification({
          type:"success",
          title: '成功',
          message: "更新成功"
        })
        doGetStorageConfig()
      }
    }
  })
}

const doGetStorageConfig = async () => {
  const response = await getStorageConfig();
  if (response.code === 200) {
    data.endpoint = response.data.endpoint
    data.accessKey = response.data.accessKey
    data.secretKey = response.data.secretKey
  }
}

doGetStorageConfig()
</script>

<template>
  <AdminLayout>
    <el-card header="MINIO存储配置" style="margin-top: 20px">
      <el-form ref="formRef" :model="data" :rules="rules" size="large">
        <el-form-item label="Endpoint" prop="endpoint">
          <el-input v-model="data.endpoint" placeholder="请输入Endpoint 例:http://my.minio.com:9001" clearable/>
        </el-form-item>
        <el-form-item label="accessKey" prop="accessKey">
          <el-input v-model="data.accessKey" placeholder="请输入accessKey" clearable/>
        </el-form-item>
        <el-form-item label="secretKey" prop="secretKey">
          <el-input v-model="data.secretKey" placeholder="请输入secretKey" clearable/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="doUpdateStorageConfig">更新设置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </AdminLayout>
</template>

<style scoped>

</style>