<template>
  <Editor @selectionChange="handlerFunction" v-model="content" :init="editorInit"></Editor>
</template>
<script setup>
import {defineModel} from "vue"
import Editor from "@tinymce/tinymce-vue"
import 'tinymce/tinymce'
// models
import 'tinymce/models/dom'
// Theme
import 'tinymce/themes/silver/theme'
// icons
import 'tinymce/icons/default/icons'


const editorInit = {
  promotion: false, // 这个要加上，不然富文本会出现更新按钮的
  skin_url: "/skin/oxide", // skin路径，具体路径看自己的项目
  language_url: "/tinymce/zh-Hans.js", // 语言包的路径，具体路径看自己的项目，文档后面附上中文js文件
  language: 'zh-Hans',
  automatic_uploads: false,
  paste_data_images: false,
  branding: false // 去掉编辑器右下角的广告
}
const content = defineModel("content")
const summary = defineModel("summary")

const handlerFunction = (event,editor)=>{
  summary.value = editor.getContent({ format: 'text' })
}
</script>

<style scoped>

</style>