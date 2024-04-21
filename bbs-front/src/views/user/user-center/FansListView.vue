<script setup>

import FansUserInfoCard from "@/components/FansUserInfoCard.vue";
import credentialStore from "@/stores/credential.js";
import {ref, watch} from "vue";
import {queryUserFansList} from "@/api/follow.js";

const store = credentialStore()
const fansData = ref([])
const pageTotal = ref(0)
const pageSize = 10
const pageNum = ref(1)

const getFansDataList = async () => {
  const response = await queryUserFansList(pageNum.value, pageSize)
  if (response.code === 200) {
    pageTotal.value = response.total
    pageNum.value = response.current
    fansData.value = response.data
  }
}

getFansDataList()

watch(pageNum, () => {
  getFansDataList()
})
</script>

<template>
  <div class="row row-cards">

    <div class="col-md-12">
      <div class="border-0 card card-body">
        <h3 class="card-title">{{store.username}} 的粉丝</h3>
        <div class="row row-cards">

          <div class="empty" v-if="pageTotal == 0">
            <div class="empty-header">404</div>
            <p class="empty-title">暂无更多结果</p>
          </div>

          <div v-else>
            <FansUserInfoCard :info="fansData"></FansUserInfoCard>
          </div>
        </div>
        <div class="mt-2">

        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>