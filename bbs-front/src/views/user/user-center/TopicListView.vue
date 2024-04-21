<template>
  <div class="border-0 card card-body">
    <h3 class="card-title">{{store.username}} 发布的主题</h3>
    <div class="empty" v-if="pageTotal == 0">
      <div class="empty-header">404</div>
      <p class="empty-title">暂无更多结果</p>
    </div>
    <div class="row row-cards" v-else>
      <article class="col-md-12 p-3 border-bottom hoverable" v-for="item in topicData" :key="item.id">
        <div class="d-flex justify-content-between border-0 card">
          <div class="row">
            <div class="col-auto">
              <span class="avatar"
                 :style="{backgroundImage: `url(${item.user.avatar})`}">
              </span>

            </div>
            <div class="col">
              <div class="row">
                <div class="col-md-12">
                  <div class="row">
                    <div class="col-md-12 markdown home-article">
                      <router-link style="text-decoration: none" :to="{name:'Topic',params:{id:item.id}}" class="text-reset">
                        <h3 class="text-muted">
                          <span class="badge d-none d-lg-inline-block" :style="{backgroundColor: item.tag.color}">{{item.tag.name}}</span>
                          {{item.title}}
                        </h3>
                      </router-link>
                    </div>
                  </div>
                </div>
                <div class="col-md-12" style="margin-top: 5px">
                  <div class="d-flex align-items-center">
                    <div class="text-muted" style="margin-top:1px">
                      <span class="text-muted">
                        <span class="text-red">{{item.user.username}}</span>
                      </span>
                      {{moment(item.createTime).fromNow()}}
                    </div>
                    <div class="ms-auto d-none d-lg-inline-block">
                      <el-tooltip
                          class="box-item"
                          effect="dark"
                          content="浏览量"
                          placement="bottom"
                      >
                      <span class="mx-2">
                          <Icon icon="heroicons-outline:eye" :inline="true"/>
                          <span class="mx-2">{{item.viewCount}}</span>
                      </span>
                      </el-tooltip>

                      <el-tooltip
                          class="box-item"
                          effect="dark"
                          content="评论"
                          placement="bottom"
                      >
                   <span class="mx-2">
                     <Icon icon="heroicons-outline:chat" :inline="true"/>
                      <span class="mx-2">{{item.commentCount}}</span>
                    </span>
                      </el-tooltip>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </article>


    </div>
    <div class="mt-2">
      <el-pagination v-model:current-page="pageNum" layout="prev, pager, next" v-if="pageTotal>pageSize" :page-size="pageSize" :total="pageTotal" style="margin-top: 20px;justify-content: center"/>


    </div>
  </div>
</template>
<script setup>
import {Icon} from "@iconify/vue";
import {queryUserTopic} from "@/api/user.js"
import {ref, watch} from "vue"
import credentialStore from "@/stores/credential.js"
import moment from "moment";


const store = credentialStore()

const pageTotal = ref(0)
const pageSize = 10
const pageNum = ref(1)
const topicData = ref([])
const getUserTopicData = async () => {
  const response = await queryUserTopic(pageNum.value, pageSize)
  pageTotal.value = response.total
  pageNum.value = response.current
  topicData.value = response.data
}

getUserTopicData()

watch(pageNum, () => {
  getUserTopicData()
})

</script>
<style scoped>

</style>