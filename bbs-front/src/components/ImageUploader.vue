<template>
  <el-upload
      :accept="props.fileType"
      list-type="picture-card"
      :limit="10"
      v-model:file-list="fileList"
      :action="actionUrl"
      :headers="headers"
      :on-preview="previewImage"
      :before-upload="checkFile"
      :on-success="onSuccess"
      :on-remove="onRemove"
  >
    <el-icon>
      <Plus/>
    </el-icon>
  </el-upload>

  <el-dialog v-model="imagePreviewDialogVisible">
    <img w-full :src="imagePreviewDialogUrl" alt="预览图片"/>
  </el-dialog>
</template>
<script setup>
import {Plus} from "@element-plus/icons-vue";
import {defineProps, ref,defineModel} from "vue"
import siteConfigStore from "@/stores/siteconfig.js";
import {ElMessage} from "element-plus";
import credentialStore from "@/stores/credential.js"

const actionUrl = import.meta.env.VITE_API_BASE_URL + "/upload/image"
const headers = {
  Authorization:credentialStore().token
}
const fileList = ref([])
const imagePreviewDialogVisible = ref(false)
const imagePreviewDialogUrl = ref("")

const props = defineProps({
  fileType: {
    type: String,
    default: "*"
  }
})
const fileUploadList = defineModel("fileUploadList")
fileUploadList.value = []


const checkFile = (ops) => {
  if (ops.size / 1024 > siteConfigStore().maxFileUploadSize) {
    ElMessage({
      type: "error",
      message: "文件大小不得超过" + siteConfigStore().maxFileUploadSize + "KB",
      duration: 1500
    })
    return false;
  }
}

const onSuccess = (response, uploadFile, uploadFiles)=>{
  if(response.code === 200){
    fileUploadList.value.push(response.data.fileId)
  }else{
    ElMessage({
      type: "error",
      message: "文件上传失败,后台发生未知错误",
      duration: 1500
    })
  }
}

const onRemove = (uploadFile, uploadFiles)=>{
  fileUploadList.value = fileUploadList.value.filter(item => item.uid !== uploadFile.uid)
}


const previewImage = (ops) => {
  imagePreviewDialogUrl.value = ops.url
  imagePreviewDialogVisible.value = true
}
</script>
<style scoped>

</style>