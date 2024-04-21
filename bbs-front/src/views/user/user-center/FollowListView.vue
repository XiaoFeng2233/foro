<template>
  <div class="row row-cards">

    <div class="col-md-12">
      <div class="border-0 card card-body">
        <h3 class="card-title">{{ store.username }} 关注的人</h3>
        <div class="row row-cards">

          <div class="empty" v-if="pageTotal == 0">
            <div class="empty-header">404</div>
            <p class="empty-title">暂无更多结果</p>
          </div>

          <div v-else>
            <FollowUserInfoCard :info="followData"></FollowUserInfoCard>
          </div>
        </div>
        <div class="mt-2">

        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import credentialStore from "@/stores/credential.js"
import {ref, watch} from "vue";
import {queryUserFollowList} from "@/api/follow.js"
import FollowUserInfoCard from "@/components/FollowUserInfoCard.vue";


const store = credentialStore()
const followData = ref([])
const pageTotal = ref(0)
const pageSize = 10
const pageNum = ref(1)

const getFollowDataList = async () => {
  const response = await queryUserFollowList(pageNum.value, pageSize)
  if (response.code === 200) {
    pageTotal.value = response.total
    pageNum.value = response.current
    followData.value = response.data
  }
}

getFollowDataList()

watch(pageNum, () => {
  getFollowDataList()
})
</script>
<style scoped>

</style>