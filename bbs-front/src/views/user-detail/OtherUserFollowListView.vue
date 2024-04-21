<template>
  <div class="row row-cards">

    <div class="col-md-12">
      <div class="border-0 card card-body">
        <h3 class="card-title">{{ userInfo.username }} 关注的人</h3>
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
import {queryUserFollowListByUserId} from "@/api/follow.js"
import FollowUserInfoCard from "@/components/FollowUserInfoCard.vue";
import {useRoute,useRouter} from "vue-router"
import {getInfoByUserId} from "@/api/user.js";

const followData = ref([])
const pageTotal = ref(0)
const pageSize = 10
const pageNum = ref(1)
const route = useRoute()
const router = useRouter()
const getFollowDataList = async () => {
  const response = await queryUserFollowListByUserId(pageNum.value, pageSize, route.params.id)
  if (response.code === 200) {
    pageTotal.value = response.total
    pageNum.value = response.current
    followData.value = response.data
  }
}

await getFollowDataList()

watch(pageNum, () => {
  getFollowDataList()
})

const userInfo = ref({})
const doGetUserInfo = async () => {
  const response = await getInfoByUserId(route.params.id)
  if(response.code === 200){
    userInfo.value = response.data
  }else{
    router.push("/")
  }
}
await doGetUserInfo()
</script>
<style scoped>

</style>