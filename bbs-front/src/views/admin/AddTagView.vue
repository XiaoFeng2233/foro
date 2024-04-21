<template>
  <AdminLayout>
    <el-card header="添加板块">
      <el-form :rules="rules" :model="data" ref="formRef" label-position="top" size="large">
        <el-form-item label="板块名称" prop="name">
          <el-input v-model="data.name" placeholder="请填写板块名称" clearable :maxlength="10" show-word-limit/>
        </el-form-item>
        <el-form-item label="板块描述" prop="description">
          <el-input v-model="data.description" placeholder="请填写板块描述" type="textarea"/>
        </el-form-item>
        <el-form-item label="板块颜色" prop="color">
          <el-color-picker v-model="data.color" show-alpha />
        </el-form-item>
        <el-form-item>
          <el-button @click="doAddTag" type="primary">添加</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </AdminLayout>
</template>
<script setup>
import {addTag} from "@/api/admin.js"
import AdminLayout from "@/layout/AdminLayout.vue";
import {reactive, ref} from "vue";
import {ElNotification} from "element-plus";



const formRef = ref()
const data = reactive({
  name:"",
  description:"",
  color:""
})

const rules = {
  name: {required: true, message: '板块名称不能为空', trigger: 'blur'},
  description: {required: true, message: '板块描述不能为空', trigger: 'blur'},
  color: {required: true, message: '板块颜色不能为空', trigger: 'blur'},
}

const doAddTag = async ()=>{
  formRef.value.validate(async valid => {
    if (valid) {
      const response = await addTag(data.name,data.description,data.color)
      if(response.code === 200){
        ElNotification({
          message:response.data,
          title:"成功",
          type:"success",
          duration:1500
        })
        formRef.value.resetFields()
      }
    }
  }
    )
}
</script>

<style scoped>

</style>