<script setup>
import {getTagList} from "@/api/tag.js"
import {userList,addTagManager} from "@/api/admin.js"
import {ref} from "vue"
import {ElMessage} from "element-plus"


const tagList = ref([])
const userData = ref([])
const selectedTag = ref(undefined)
const selectedUser = ref(undefined)
const doGetTagList = async () => {
  const response = await getTagList()
  if(response.code === 200){
    tagList.value = response.data
  }
}

await doGetTagList()

const doUserList = async ()=>{
  const response = await userList()
  if(response.code === 200){
    userData.value = response.data
  }
}

await doUserList()

const doAddManager = async ()=>{
  if(selectedUser.value === undefined || selectedTag.value === undefined){
    ElMessage.error({
      message:"请选择用户和板块",
      duration:1500
    })
    return false;
  }
  const response = await addTagManager(selectedUser.value, selectedTag.value)
  if(response.code === 200){
    ElMessage.success({
      message:response.data,
      duration:1500
    })
    selectedUser.value = undefined
    selectedTag.value = undefined

    //1.5秒后刷新网页
    setTimeout(()=>{
      window.location.reload()
    }, 1500)
  }
}

</script>

<template>
  <el-form>
    <el-form-item label="选择用户">
      <el-select
          v-model="selectedUser"
          placeholder="请选择用户"
          style="width: 240px"
          filterable
      >
        <el-option
            v-for="item in userData"
            :key="item.username"
            :label="item.username"
            :value="item.id"
        />
      </el-select>
    </el-form-item>
    <el-form-item label="选择板块">
      <el-select
          v-model="selectedTag"
          placeholder="请选择板块"
          style="width: 240px"
      >
        <el-option
            v-for="item in tagList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
        >
          <el-tag :color="item.color" effect="dark">
            {{item.name}}
          </el-tag>
        </el-option>
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="doAddManager">添加</el-button>
    </el-form-item>
  </el-form>
</template>

<style scoped>

</style>