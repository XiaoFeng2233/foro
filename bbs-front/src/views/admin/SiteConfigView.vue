<template>
  <AdminLayout>
    <el-card header="站点设置">
      <el-form :rules="rules" :model="data" ref="formRef" label-position="top" size="large">
        <el-form-item label="网站名称" prop="name">
          <el-input placeholder="请输入网站名称" clearable v-model="data.name"/>
        </el-form-item>
        <el-form-item label="网站地址" prop="url">
          <el-input placeholder="请输入网站地址" clearable v-model="data.url"/>
        </el-form-item>
        <el-form-item label="网站LOGO" prop="logo">
          <el-input placeholder="请输入网站LOGO URL" clearable v-model="data.logo"/>
        </el-form-item>
        <el-form-item label="网站ICON" prop="favicon">
          <el-input placeholder="请输入网站ICON URL" clearable v-model="data.favicon"/>
        </el-form-item>
        <el-form-item label="文件最大上传大小  单位:KB" prop="maxFileUploadSize">
          <el-input-number v-model="data.maxFileUploadSize" :min="1"/>
        </el-form-item>
        <el-form-item label="网站开启" prop="open">
          <el-switch
              inline-prompt
              v-model="data.open"
              active-text="是"
              inactive-text="否"/>

        </el-form-item>
        <el-form-item label="是否开启注册" prop="openRegister">
          <el-switch
              v-model="data.openRegister"
              inline-prompt

              active-text="是"
              inactive-text="否"/>

        </el-form-item>
        <el-form-item label="网站底部HTML代码" prop="footer">
          <el-input type="textarea" v-model="data.footer">

          </el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="doUpdateSiteConfig">更新设置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

  </AdminLayout>
</template>
<script setup>
import AdminLayout from "@/layout/AdminLayout.vue"
import {ref, reactive} from "vue"
import {updateSiteConfig, getSiteConfig} from "@/api/admin.js"
import {ElNotification} from "element-plus";

const formRef = ref()
const data = reactive({
  name: "",
  logo: "",
  favicon: "",
  url: "",
  openRegister: false,
  open: false,
  footer: "",
  maxFileUploadSize: undefined
})

const rules = {
  name: {required: true, message: '请输入网站名称', trigger: 'blur'},
  logo: {required: true, message: '请输入网站LOGO地址', trigger: 'blur'},
  favicon: {required: true, message: '请输入网站FAVICON地址', trigger: 'blur'},
  url: {required: true, message: '请输入网站地址', trigger: 'blur'},
  openRegister: {required: true, message: '请选择网站是否开启注册', trigger: 'blur'},
  open: {required: true, message: '请选择网站是否开启', trigger: 'blur'},
  footer: {required: true, message: '请输入网站底部HTML代码', trigger: 'blur'},
  maxFileUploadSize: {required: true, message: '请输入文件最大上传大小', trigger: 'blur'}
}


const doUpdateSiteConfig = async () => {
  formRef.value.validate(async valid => {
    if (valid) {
      const response = await updateSiteConfig(data.name, data.logo, data.favicon, data.url, data.openRegister ? 1 : 0, data.open ? 1 : 0, data.footer, data.maxFileUploadSize)
      if (response.code === 200) {
        ElNotification({
          type: "success",
          title: '成功',
          message: "更新成功"
        })
        doGetSiteConfig()
      }
    }
  })
}

const doGetSiteConfig = async () => {
  const response = await getSiteConfig()
  if (response.code === 200) {
    data.name = response.data.name
    data.logo = response.data.logo
    data.favicon = response.data.favicon
    data.url = response.data.url
    data.openRegister = response.data.openRegister
    data.open = response.data.open
    data.footer = response.data.footer
    data.maxFileUploadSize = response.data.maxFileUploadSize
  }
}

doGetSiteConfig()
</script>
<style scoped>

</style>