<script setup>
import {defineProps, ref} from "vue"
import {queryTopicComment} from "@/api/comment.js"
import {deleteComment} from "@/api/admin.js"


const prop = defineProps(["id"])

const commentData = ref([])
const pageTotal = ref(0)
const pageSize = 10
const pageNum = ref(1)

const getTopicComment = async ()=>{
  const response = await queryTopicComment(pageNum.value, pageSize, prop.id)
  commentData.value = response.data
  pageTotal.value = response.total
  pageNum.value = response.current
}

await getTopicComment()

const doDeleteComment = async id=>{
  const response = await deleteComment(id);
  if(response.code === 200){
    await getTopicComment()
  }
}
</script>

<template>
  <el-table :data="commentData">
    <el-table-column prop="id" label="ID"></el-table-column>
    <el-table-column prop="content" label="评论内容">
      <template #default="scope">
        <el-tooltip
            effect="dark"
            :content="scope.row.content"
            placement="top"
        >
          <div class="comment-box">
            {{scope.row.content}}
          </div>
        </el-tooltip>
      </template>
    </el-table-column>
    <el-table-column prop="createTime" label="评论时间"></el-table-column>
    <el-table-column prop="user.username" label="评论用户"></el-table-column>
    <el-table-column prop="ip" label="IP"></el-table-column>
    <el-table-column prop="ipLocation" label="地点"></el-table-column>
    <el-table-column prop="operation" label="操作">
      <template #default="scope">
        <el-button type="danger" size="small" @click="doDeleteComment(scope.row.id)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<style scoped>
.comment-box{
  width: 100%;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>