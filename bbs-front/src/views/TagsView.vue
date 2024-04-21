<template>
  <DefaultLayout>
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
                板块列表
              </h2>
            </div>

            <div class="col-auto">
            </div>
          </div>
        </div>
      </div>
    </div>


    <div class="page-body">
      <div class="container-xl">
        <div style="text-align: center" v-if="tagList.length === 0">暂无数据</div>
        <div class="row row-cards" v-else>
          <div class="col-md-6 col-lg-4" v-for="tag in tagList" :key="tag.id">
            <router-link :to="'/tag/' + tag.id" class="card card-link text-primary-fg"
                         :style="{'backgroundColor':tag.color}">
              <div class="card-body">
                <h3 class="card-title">{{ tag.name }}</h3>
                <p v-if="!lodash.isEmpty(tag.description)">
                  {{ tag.description }}
                </p>
                <p v-else>暂无描述</p>
              </div>
            </router-link>
          </div>
          <el-pagination v-model:current-page="pageNum" v-if="pageTotal > 20" background layout="prev, pager, next"
                         :page-size="pageSize" :total="pageTotal" class="mt-6 justify-content-center"/>
        </div>

      </div>
    </div>
  </DefaultLayout>
</template>
<script setup>
import DefaultLayout from "@/layout/DefaultLayout.vue";
import {queryTagList} from "@/api/tag.js"
import {ref, watch} from "vue"
import lodash from "lodash"


const pageNum = ref(1)
const pageSize = 20
const pageTotal = ref(0)
const tagList = ref([])
const getData = async () => {
  let response = await queryTagList(pageNum.value, pageSize);
  if (response.code === 200) {
    pageNum.value = response.current
    tagList.value = response.data
    pageTotal.value = response.total
  }
}

await getData()

watch(pageNum, () => {
  getData()
})


</script>

<style scoped>

</style>