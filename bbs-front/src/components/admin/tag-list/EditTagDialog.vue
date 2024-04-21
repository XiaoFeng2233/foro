<script setup>
import {ref,reactive,defineProps} from "vue"
import {info} from "@/api/tag.js"
import {updateTag} from "@/api/admin.js";
import {ElNotification} from "element-plus";

const prop = defineProps(["id"])
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

const doGetTagInfo = async ()=>{
  const response = await info(prop.id)
  if(response.code === 200){
    data.name = response.data.name
    data.description = response.data.description
    data.color = response.data.color
  }
}

await doGetTagInfo()

const doUpdateTag = async ()=>{
  formRef.value.validate(async valid => {
        if (valid) {
          const response = await updateTag(data.name,data.description,data.color,prop.id)
          if(response.code === 200){
            ElNotification({
              message:response.data,
              title:"成功",
              type:"success",
              duration:1500
            })
            formRef.value.resetFields()

            //1.5秒后刷新网页
            setTimeout(()=>{
              window.location.reload()
            },1500)
          }
        }
      }
  )
}
</script>

<template>
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
      <el-button @click="doUpdateTag" type="primary">修改</el-button>
    </el-form-item>
  </el-form>
</template>

<style scoped>

</style>