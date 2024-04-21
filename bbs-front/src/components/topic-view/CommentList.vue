<script setup>
import {defineProps, ref,watch} from "vue"
import {queryTopicComment, deleteComment} from "@/api/comment.js"
import moment from "moment"
import {Icon} from "@iconify/vue"
import {hasRole, isManager} from "@/utils/authUtil.js"
import {ElMessage} from "element-plus";

const prop = defineProps(["topicId", "topic"])

const commentData = ref([])
const pageTotal = ref(0)
const pageSize = 10
const pageNum = ref(1)
const subCommentDialog = ref(false)
const subCommentParentId = ref(undefined)

const doQueryCommentData = async () => {
  if (prop.topicId != null) {
    const response = await queryTopicComment(pageNum.value, pageSize, prop.topicId)
    if (response.code === 200) {
      pageTotal.value = response.total
      pageNum.value = response.current
      commentData.value = response.data
    }
  }

}

await doQueryCommentData()

const openSubCommentDialog = (id) => {
  subCommentParentId.value = id
  subCommentDialog.value = true
}

const doDeleteComment = async (commentId) => {
  const response = await deleteComment(commentId)
  if(response.code === 200){
    ElMessage.success({
      message: response.data,
      duration: 1500
    })

    //1.5秒后刷新网页
    setTimeout(() => {
      location.reload()
    }, 1500)
  }
}

watch(pageNum,()=>{doQueryCommentData()})
</script>

<template>
  <div class="col-md-12">
    <div class="card">

      <div class="card-header">
        <h3 class="card-title">全部评论</h3>
      </div>

      <div class="card-body">
        <div class="row row-cards" v-if="pageTotal > 0">
          <div v-for="(item,index) in commentData" :key="item.id">
            <div class="col-md-12 mt-1" v-if="item.parentId === null">
              <div class="">
                <div class="mt-2">
                  <div class="row">
                    <div class="col-md-12">
                      <div class="row">
                        <div class="col-auto">
                          <router-link :to="{name:'OtherUserCenterPanel',params:{id:item.userId}}">
                            <span class="avatar avatar-rounded"
                                  :style="{backgroundImage: `url(${item.user.avatar})`}">

                                                        </span></router-link>
                        </div>

                        <div class="col text-truncate my-0">
                          <router-link :to="{name:'OtherUserCenterPanel',params:{id:item.userId}}"
                                       style="white-space:nowrap;"
                                       class="text-body text-truncate">
                            {{ item.user.nickname }}
                            <div class="badge badge-pill badge-sm mx-1" style="background:#20cba0;">
                              <Icon icon="solar:user-bold" width="14" height="14"></Icon>
                            </div>
                          </router-link>
                          <br>
                          <small class="text-muted text-truncate mt-n1">发表 :{{
                              moment(item.createTime).fromNow()
                            }}</small>
                        </div>


                        <div class="col-auto">
                          <el-button type="primary" text @click="openSubCommentDialog(item.id)">回复</el-button>
                        </div>
                        <el-popconfirm
                            width="220"
                            confirm-button-text="确定"
                            cancel-button-text="取消"
                            title="确定要删除本条评论吗？"
                            @confirm="doDeleteComment(item.id)"
                        >
                          <template #reference>
                            <div class="col-auto" v-if="hasRole('admin') || isManager(prop.topic.tagId)">
                              <el-button text type="danger">删除</el-button>
                            </div>
                          </template>
                        </el-popconfirm>
                      </div>
                    </div>


                    <div class="col-md-12 markdown mt-3 mb-2"
                         style="font-size: 15px">
                      {{ item.content }}
                    </div>


                  </div>
                </div>

              </div>
            </div>
            <div class="col-md-12 mt-1" v-else>
              <div>
                <div class="mt-2">
                  <div class="row">

                    <div class="col-md-12">
                      <div class="row">

                        <div class="col-auto">
                          <router-link :to="{name:'OtherUserCenterPanel',params:{id:item.userId}}">
                            <span class="avatar avatar-rounded"
                                  :style="{backgroundImage: `url(${item.user.avatar})`}">

                                                        </span></router-link>
                        </div>

                        <div class="col text-truncate my-0">
                          <router-link :to="{name:'OtherUserCenterPanel',params:{id:item.userId}}"
                                       style="white-space:nowrap;"
                                       class="text-body text-truncate">
                            {{ item.user.nickname }}
                            <div class="badge badge-pill badge-sm mx-1" style="background:#20cba0;">
                              <Icon icon="solar:user-bold" width="14" height="14"></Icon>
                            </div>
                          </router-link>
                          <br>
                          <small class="text-muted text-truncate mt-n1">发表 :{{
                              moment(item.createTime).fromNow()
                            }}</small>
                        </div>

                        <div class="col-auto">
                          <el-button text type="primary" @click="openSubCommentDialog(item.id)">回复</el-button>
                        </div>
                        <el-popconfirm
                            width="220"
                            confirm-button-text="确定"
                            cancel-button-text="取消"
                            title="确定要删除本条评论吗？"
                            @confirm="doDeleteComment(item.id)"
                        >
                          <template #reference>
                            <div class="col-auto" v-if="hasRole('admin') || isManager(prop.topic.tagId)">
                              <el-button text type="danger">删除</el-button>
                            </div>
                          </template>
                        </el-popconfirm>
                      </div>
                    </div>


                    <div style="font-size: 15px" class="mt-2">
                      <div class="quote">
                        <blockquote v-if="item.parentId != null && item.parent != null">
                          <a style="font-size:13px;">
                            <span style="color:#999999">{{ item.parent.user.nickname }} 发表 {{
                                moment(item.parent.createTime).fromNow()
                              }}</span>
                          </a>
                          <br>
                          {{ item.parent.content }}
                        </blockquote>
                        <blockquote v-else>
                          原评论已被删除
                        </blockquote>
                      </div>
                      {{ item.content }}
                    </div>
                  </div>
                </div>

              </div>
            </div>
            <div class="hr-text mt-1 mb-1" v-if="index+1 < commentData.length">next</div>
          </div>


        </div>
        <div class="text-secondary" v-else>没有更多评论了...</div>

      </div>

      <el-pagination v-if="pageTotal> pageSize" layout="prev, pager, next" :total="pageTotal"
                     v-model:current-page="pageNum" :page-size="pageSize"
                     style="margin-top: 20px;margin-bottom: 20px;justify-content: center"/>


    </div>

    <ElDialog align-center destroy-on-close v-model="subCommentDialog" title="发布评论" width="400">
      <PublishSubComment :topic-id="prop.topicId" :parent-id="subCommentParentId"/>
    </ElDialog>
  </div>
</template>

<style scoped>

</style>