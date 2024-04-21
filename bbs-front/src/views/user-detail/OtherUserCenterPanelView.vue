<template>
  <div class="row row-cards">
    <div class="col-12">
      <div class="border-0 card card-body">
        <h3 class="card-title">个人信息</h3>
        <div class="row row-cards">
          <UserInfoCard v-if="isAdmin()" title="超级管理员" value="用户组" icon="ph:users-three" bg="bg-orange-lt"></UserInfoCard>
          <UserInfoCard v-else-if="isManager()" title="版主" value="用户组" icon="ph:users-three" bg="bg-orange-lt"></UserInfoCard>
          <UserInfoCard v-else title="注册用户" value="用户组" icon="ph:users-three" bg="bg-orange-lt"></UserInfoCard>
          <UserInfoCard title="积分" :value="userData.score" icon="mingcute:coin-line" bg="bg-orange-lt"></UserInfoCard>
        </div>
      </div>
    </div>

    <div class="col-12">
      <div class="border-0 card card-body">
        <h3 class="card-title">账户数据</h3>
        <div class="row row-cards">
          <UserInfoCard title="收藏数量" :value="userData.collectCount" icon="solar:star-linear" bg="bg-indigo-lt"></UserInfoCard>
          <UserInfoCard title="主题数量" :value="userData.topicCount" icon="ph:newspaper" bg="bg-indigo-lt"></UserInfoCard>
          <UserInfoCard title="评论数量" :value="userData.commentCount" icon="mdi:comment-outline" bg="bg-indigo-lt"></UserInfoCard>
          <UserInfoCard title="粉丝数量" :value="userData.followCount" icon="ph:users" bg="bg-indigo-lt"></UserInfoCard>
        </div>
      </div>
    </div>


    <div class="col-12">
      <div class="border-0 card card-body">
        <h3 class="card-title">登录信息</h3>
        <div class="row row-cards">
          <UserInfoCard title="注册时间" :value="userData.createTime !== null ? moment(userData.createTime).format('YYYY-MM-DD') : undefined" icon="mingcute:time-line" bg="bg-azure-lt"></UserInfoCard>
          <UserInfoCard title="最后登录时间" :value="lastLoginData !== null ? moment(lastLoginData.createTime).format('YYYY-MM-DD') : undefined" icon="mingcute:time-line"
                        bg="bg-azure-lt"></UserInfoCard>
          <UserInfoCard title="最后登录位置" :value="lastLoginData !== null ? lastLoginData.location : undefined" icon="fluent:location-12-regular"
                        bg="bg-azure-lt"></UserInfoCard>
          <UserInfoCard title="最后登录IP" :value="lastLoginData !== null ? lastLoginData.ip : undefined" icon="mdi:ip-outline" bg="bg-azure-lt"></UserInfoCard>
        </div>
      </div>
    </div>

  </div>
</template>

<script setup>
import UserInfoCard from "@/components/user/center/InfoCard.vue"
import {getInfoByUserId} from "@/api/user.js"
import {ref} from "vue"
import {hasRole} from "@/utils/authUtil.js";
import moment from "moment"
import {useRoute, useRouter} from "vue-router";


const route = useRoute();
const router = useRouter();
const userData = ref({})
const lastLoginData = ref(undefined)
const doGetUserInfo = async () => {
  const response = await getInfoByUserId(route.params.id)
  if(response.code === 200){
    userData.value = response.data
    lastLoginData.value = response.data.lastLoginLog
  }else{
    router.push("/")
  }


}

await doGetUserInfo()



const isAdmin = ()=>{
  return userData.value.roles !== null && userData.value.roles.includes("admin")
}

const isManager = ()=>{
  return userData.value.roles !== null && userData.value.roles.includes("topic_manager")
}
</script>

<style scoped>

</style>