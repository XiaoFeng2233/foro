<script setup>
import {defineProps, ref, reactive} from "vue"
import {view} from "@/api/topic.js"
import {updateTopic} from "@/api/admin.js"
import {ElMessage} from "element-plus"


const formRef = ref()
const rules = {
  title: {required: true, message: '标题不能为空', trigger: 'blur'},
  content: {required: true, message: '内容不能为空', trigger: 'blur'},
  sticky: {required: true, message: '是否置顶不能为空', trigger: 'blur'},
  recommend: {required: true, message: '是否推荐不能为空', trigger: 'blur'},
  isLock: {required: true, message: '是否锁定不能为空', trigger: 'blur'},
}
const prop = defineProps(["id"])
const topic = reactive({
  id: undefined,
  title: undefined,
  content: undefined,
  sticky: undefined,
  recommend: undefined,
  isLock: undefined
})
const doView = async () => {
  const response = await view(prop.id)
  if (response.code === 200) {
    topic.id = response.data.id
    topic.title = response.data.title
    topic.content = response.data.content
    topic.sticky = (response.data.sticky === 1)
    topic.recommend = (response.data.recommend === 1)
    topic.isLock = (response.data.isLock === 1)

  }
}

await doView()

const doUpdateTopic = async () => {
  formRef.value.validate(async valid => {
        if (valid) {
          const response = await updateTopic(topic.id,
              topic.title,
              topic.content,
              topic.sticky ? 1 : 0, topic.recommend ? 1 : 0, topic.isLock ? 1 : 0)
          if (response.code === 200) {
            ElMessage.success({
              message: response.data,
              duration: 1500
            })
            //清空表单
            topic.id = undefined
            topic.title = undefined
            topic.content = undefined
            topic.sticky = undefined
            topic.recommend = undefined
            topic.isLock = undefined

            //1.5秒后刷新网页
            setTimeout(() => {
              window.location.reload()
            }, 1500)
          }
        }
      }
  )
}
</script>

<template>
  <el-form ref="formRef" :model="topic" label-position="top" :rules="rules">
    <el-form-item label="ID" prop="id">
      <el-input disabled v-model="topic.id"/>
    </el-form-item>
    <el-form-item label="标题" prop="title">
      <el-input placeholder="请输入标题" v-model="topic.title"/>
    </el-form-item>
    <el-form-item label="内容" prop="content">
      <el-input type="textarea" placeholder="请输入内容（HTML版）" :input-style="{height:'200px'}"
                v-model="topic.content"/>
    </el-form-item>
    <el-form-item label="置顶" prop="sticky">
      <el-switch
          v-model="topic.sticky"
          size="large"
          active-text="是"
          inactive-text="否"
      />
    </el-form-item>
    <el-form-item label="推荐" prop="recommend">
      <el-switch
          v-model="topic.recommend"
          size="large"
          active-text="是"
          inactive-text="否"
      />
    </el-form-item>
    <el-form-item label="锁定" prop="isLock">
      <el-switch
          v-model="topic.isLock"
          size="large"
          active-text="是"
          inactive-text="否"
      />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="doUpdateTopic">更新</el-button>
    </el-form-item>
  </el-form>
</template>

<style scoped>

</style>