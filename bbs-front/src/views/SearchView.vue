<script setup>
import {useRoute} from "vue-router"
import DefaultLayout from "@/layout/DefaultLayout.vue";
import UserCard from "@/components/home-view/UserCard.vue";
import {isAuth} from "@/utils/authUtil.js";
import siteConfigStore from "@/stores/siteconfig.js"
import {search} from "@/api/topic.js"
import {ref, watch} from "vue";


const route = useRoute()
const store = siteConfigStore()
const searchResult = ref([])
const pageTotal = ref(0)
const pageSize = 10
const pageNum = ref(1)
const doSearch = async () => {
  const response = await search(pageNum.value, pageSize, route.params.param)
  if (response.code === 200) {
    pageTotal.value = response.total
    pageNum.value = response.current
    searchResult.value = response.data
  }
}

await doSearch()

watch(pageNum, () => {
  doSearch()
})

watch(() => route.params, () => {
  doSearch()
})
</script>

<template>

  <default-layout>
    <div id="search-page">
      <div class="page-wrapper">
        <div class="container-xl">
          <!-- Page title -->
          <div class="page-header d-print-none">
            <div class="row align-items-center">
              <div class="col">
                <!-- Page pre-title -->
                <div class="page-pretitle">
                  Overview
                </div>
                <h2 class="page-title">
                  「{{ route.params.param }}] 的搜索结果
                </h2>
              </div>


            </div>
          </div>
        </div>
      </div>
      <div class="page-body">
        <div class="container-xl">

          <div class="row row-cards justify-content-center">
            <div class="col-md-12">
              <div class="row row-cards justify-content-center">
                <div class="col-lg-9">
                  <div class="row row-cards" v-if="pageTotal > 0">
                    <article class="col-md-12" v-for="item in searchResult" :key="item.id">
                      <div class="card">
                        <div class="card-header">
                          <router-link :to="{name:'Topic',params:{id:item.id}}" class="card-title link-primary">
                            {{ item.title }}
                          </router-link>
                        </div>
                        <div class="card-body markdown" v-html="item.formattedSummary">

                        </div>
                        <div class="card-footer">
                          <ul style="margin-left: -20px">
                            <li style="float: left;list-style: outside none none;padding: 3px;line-height: 1.6">
                              <router-link :to="{name:'OtherUserCenterPanel',params:{id:item.publisherUserId}}">
                                {{ item.publisherUserNickName }}
                              </router-link>
                            </li>
                            <li style="float: left;list-style: outside none none;padding: 3px;line-height: 1.6">
                              <span class="text-muted">{{ item.publishTime }}</span>
                            </li>
                          </ul>
                        </div>
                      </div>
                    </article>
                  </div v-if>
                  <div class="card card-body empty" v-else>
                    <div class="empty-icon"><!-- Download SVG icon from http://tabler-icons.io/i/mood-sad -->
                      <svg xmlns="http://www.w3.org/2000/svg" class="icon" width="24" height="24" viewBox="0 0 24 24"
                           stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round"
                           stroke-linejoin="round">
                        <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                        <path d="M12 12m-9 0a9 9 0 1 0 18 0a9 9 0 1 0 -18 0"></path>
                        <path d="M9 10l.01 0"></path>
                        <path d="M15 10l.01 0"></path>
                        <path d="M9.5 15.25a3.5 3.5 0 0 1 5 0"></path>
                      </svg>
                    </div>
                    <p class="empty-title">No results found</p>
                    <p class="empty-subtitle text-muted">
                      无所搜结果,建议换一个词重试
                    </p>
                  </div>
                  <div class="mt-3">
                  </div>
                  <el-pagination v-if="pageTotal> pageSize" background layout="prev, pager, next" :total="pageTotal"
                                 v-model:current-page="pageNum" :page-size="pageSize"
                                 style="margin-top: 20px;justify-content: center"/>
                </div>
                <div class="col-lg-3">
                  <div class="row row-cards rd">
                    <div class="col-md-12 sticky" style="top: 105px">
                      <div class="row row-cards" v-if="isAuth()">
                        <UserCard></UserCard>
                      </div>

                      <div class="card" v-else>
                        <div class="card-header">
                          <h3 class="card-title">
                            {{ store.name }}
                          </h3>
                        </div>
                        <div class="card-body ">
                          {{ store.name }}是一个聚集程序员、站长、互联网从业者的在线社区平台，主要分享和讨论与编程、网站建设、SEO、服务器运维、互联网营销等相关的技术和经验。
                        </div>
                        <div class="d-flex">
                          <router-link :to="{name:'Register'}" class="card-btn">注册</router-link>
                          <router-link :to="{name:'Login'}" class="card-btn">登陆</router-link>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

        </div>
      </div>
    </div>
  </default-layout>
</template>

<style scoped>

</style>