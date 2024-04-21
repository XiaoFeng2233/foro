<script setup>
import {defineProps, ref} from "vue";
import {publishComment} from "@/api/comment.js"
import {ElMessage} from "element-plus";

const prop = defineProps(["topicId", "parentId"])
const content = ref("")

const doPublishComment = async () => {
  if (content.value === "" || content.value === null || content.value === undefined) {
    ElMessage.error({
      message: "评论的内容不能为空",
      duration: 1500
    })
    return false;
  }

  const response = await publishComment(prop.topicId, content.value, prop.parentId)

  if (response.code === 200) {
    ElMessage.success({
      message: response.data,
      duration: 1500
    })
    content.value = ""

    //1.5秒后刷新网页
    setTimeout(() => {
      window.location.reload()
    }, 1500)
  } else {
    ElMessage.error({
      message: "评论发布失败"
    })
  }
}
</script>

<template>
  <form>
    <div class="row">
      <div class="col-md-12 mb-3">
                            <textarea v-model="content" placeholder="说点什么..." class="form-control OwO-textarea"
                                      style="overflow: hidden; overflow-wrap: break-word; resize: none; text-align: start; height: 56px;"></textarea>
        <el-button type="primary" class="mt-2" @click="doPublishComment">发布评论</el-button>
      </div>
    </div>
  </form>
</template>

<style scoped>

</style>