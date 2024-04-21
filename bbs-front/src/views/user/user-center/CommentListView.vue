<template>
  <div class="row row-cards">

    <div class="col-md-12">
      <div class="border-0 card card-body">
        <h3 class="card-title">{{store.username}} 发布的评论</h3>
        <div class="row row-cards">
          <div class="empty" v-if="pageTotal == 0">
            <div class="empty-header">404</div>
            <p class="empty-title">暂无更多结果</p>
          </div>
          <div class="col-md-12" v-else v-for="(item,index) in commentData" :key="item.id">
            <div class="card">
              <div class="col-md-12">
                <div class="card  border-0">
                  <div class="card-body">
                    <div class="row">
                      <div class="col-md-12">
                        <div class="row">
                          <div class="col-auto">
                            <span>
                              <span class="avatar" :style="{backgroundImage: `url(${item.user.avatar})`}">

                              </span>
                            </span>
                          </div>

                          <div class="col text-truncate">
                            <span style="white-space:nowrap;" class="text-body text-truncate">{{item.user.username}}</span>
                            <br>
                            <small class="text-muted text-truncate mt-n1">发表:{{moment(item.createTime).fromNow()}}</small>
                          </div>

                          <div class="col-auto">
                            <router-link class="badge badge-pill bg-teal" :to="{name:'Topic',params:{id:item.topicId}}">查看评论</router-link>
                          </div>

                        </div>
                      </div>


                      <div class="col-md-12 mt-2 mb-2 markdown">
                        <p>{{item.content}}</p>
                      </div>


                    </div>
                  </div>

                </div>
              </div>
            </div>
          </div>

        </div>
        <div class="mt-2">
          <el-pagination v-if="pageTotal> pageSize" layout="prev, pager, next" :total="pageTotal"
                         v-model:current-page="pageNum" :page-size="pageSize"
                         style="margin-top: 20px;justify-content: center"/>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import {ref, watch} from "vue";
import {queryUserCommentList} from "@/api/comment.js"
import credentialStore from "@/stores/credential.js"
import moment from "moment"


const commentData = ref([])
const pageTotal = ref(0)
const pageSize = 10
const pageNum = ref(1)
const store = credentialStore()

const getCommentDataList = async () => {
  const response = await queryUserCommentList(pageNum.value, pageSize)
  if (response.code === 200) {
    pageTotal.value = response.total
    pageNum.value = response.current
    commentData.value = response.data
  }
}

getCommentDataList()

watch(pageNum, () => {
  getCommentDataList()
})
</script>
<style scoped>

</style>