<template>
  <div class="card">
    <div class="card-header">
      <ul class="nav nav-pills card-header-pills">
        <li class="nav-item">
          <button @click="sort = 'latest'" :class="[sort === 'latest'?'active':'','nav-link','fw-bold']">
            <Icon icon="fluent-emoji-high-contrast:five-oclock" :width="16" :height="16" :inline="true"/>
            <span class="mx-1">最新</span>
          </button>
        </li>
        <li class="nav-item">
          <button @click="sort = 'recommend'" :class="[sort === 'recommend'?'active':'','nav-link','fw-bold']">
            <Icon icon="heroicons-outline:heart" :width="16" :height="16" :inline="true"/>
            <span class="mx-1">精华</span>
          </button>
        </li>
        <li class="nav-item">
          <button @click="sort = 'hot'" :class="[sort === 'hot'?'active':'','nav-link','fw-bold']">
            <Icon icon="heroicons-outline:fire" :width="16" :height="16" :inline="true"/>
            <span class="mx-1">热门</span>
          </button>
        </li>

      </ul>
    </div>

    <article class="col-md-12 p-3 pt-2 pb-2 border-bottom" v-for="item in topicData" :key="item.id">
      <div class="d-flex border-0 card">
        <div class="row">
          <div class="col-auto align-self-center">
            <router-link :to="{name:'OtherUserCenterPanel',params:{id:item.user.id}}" class="avatar"
               :style="{backgroundImage: `url(${item.user.avatar})`}"></router-link>
          </div>
          <div class="col">
            <div class="row">
              <div class="col-md-12">
                <div class="row">
                  <div class="col-md-12 markdown home-article">
                    <h3 class="text-muted">
                      <span class="badge" :style="{backgroundColor:item.tag.color}"> {{item.tag.name}} </span>

                      <router-link :to="{name:'Topic',params:{id:item.id}}" class="text-reset ms-2">
                        {{item.title}}
                      </router-link>
                      <el-tooltip
                          class="box-item"
                          effect="dark"
                          content="帖子置顶"
                          placement="top"
                          v-if="item.sticky === 1"
                      >
                        <div style="display: inline-block" class="text-reset bg-transparent">
                          <Icon icon="pajamas:thumbtack-solid" :inline="true"/>
                        </div>
                      </el-tooltip>

                      <el-tooltip
                          class="box-item"
                          effect="dark"
                          content="精华帖"
                          placement="top"
                          v-if="item.recommend === 1"
                      >
                        <div style="display: inline-block" class="text-reset bg-transparent">
                          <Icon icon="heroicons-outline:heart" :inline="true"/>
                        </div>
                      </el-tooltip>

                      <el-tooltip
                          class="box-item"
                          effect="dark"
                          content="帖子锁定"
                          placement="top"
                          v-if="item.isLock === 1"
                      >
                        <div style="display: inline-block" class="text-reset bg-transparent">
                          <Icon icon="fluent-emoji-high-contrast:locked" :inline="true"/>
                        </div>
                      </el-tooltip>


                    </h3>
                  </div>
                </div>
              </div>
              <div class="col-md-12">
                <div class="d-flex align-items-center">
                  <div class="text-muted-sm" style="margin-top: 5px">

                    <router-link :to="{name:'OtherUserCenterPanel',params:{id:item.user.id}}" class="text-muted-sm">{{item.user.nickname}}</router-link>
                    <span class="mx-2">{{moment(item.createTime).fromNow()}}</span>
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


    <el-pagination v-if="pageTotal> pageSize" layout="prev, pager, next" :total="pageTotal"
                   v-model:current-page="pageNum" :page-size="pageSize"
                   style="margin-top: 20px;margin-bottom: 20px;justify-content: center"/>

  </div>

</template>
<script setup>
import {Icon} from '@iconify/vue';
import {ref, watch} from "vue"
import {queryLatestTopic, queryHotTopic, queryRecommendTopic} from "@/api/topic.js"
import moment from "moment"


const topicData = ref([])
const pageTotal = ref(0)
const pageSize = 10
const pageNum = ref(1)
// latest hot recommend
const sort = ref("latest")
const queryTopic = async () => {
  let response = undefined
  if (sort.value === "latest") {
    response = await queryLatestTopic(pageNum.value, pageSize)
  }
  if (sort.value === "hot") {
    response = await queryHotTopic(pageNum.value, pageSize)
  }
  if (sort.value === "recommend") {
    response = await queryRecommendTopic(pageNum.value, pageSize)
  }

  if (response != null && response.code === 200) {
    pageTotal.value = response.total
    pageNum.value = response.current
    topicData.value = response.data
  }
}
watch(sort, () => {
  queryTopic()
})

watch(pageNum, () => {
  queryTopic()
})

queryTopic()
</script>
<style scoped>

</style>