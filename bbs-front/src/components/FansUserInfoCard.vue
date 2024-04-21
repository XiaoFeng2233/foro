<script setup>
import {defineProps} from "vue"
import {Icon} from "@iconify/vue";
import {ElMessage} from "element-plus"
import {followUser, unFollowUser} from "@/api/follow.js"


const prop = defineProps(["info"])

const doFollow = async (item) => {
  const response = await followUser(item.followUserId)
  if(response.code === 200){
    ElMessage({
      message:response.data,
      type:"success",
      duration:1500
    })
    item.followed = true
  }
}

const doUnFollow = async (item) => {
  const response = await unFollowUser(item.followUserId)
  if(response.code === 200){
    ElMessage({
      message:response.data,
      type:"success",
      duration:1500
    })
    item.followed = false
  }
}
</script>

<template>
  <div class="col-md-6 col-lg-4" v-for="item in prop.info" :key="item.id">
    <div class="card">
      <div class="card-body p-4 text-center">
                <span class="avatar avatar-xl mb-3 avatar-rounded"
                      :style="{backgroundImage: `url(${item.followUser.avatar})`}"></span>
        <h3 class="m-0 mb-1">
          <router-link :to="{name:'OtherUserCenterPanel',params:{id:item.followUserId}}" class="text-reset">
            {{ item.followUser.nickname }}
          </router-link>
        </h3>

        <div class="mt-3">
          <span class="badge badge-outline" style="color: #206bc4">注册会员</span>
        </div>
      </div>
      <div class="d-flex">
        <span class="card-btn cursor-pointer" v-if="item.followed" @click="doUnFollow(item)">
          <Icon icon="ri:user-add-line" width="18"></Icon>
          <span>已关注</span>
        </span>
        <span class="card-btn cursor-pointer" v-else @click="doFollow(item)">
          <Icon icon="ri:user-add-line" width="18"></Icon>
          <span>关注</span>
        </span>
        <router-link :to="{name:'OtherUserCenterPanel',params:{id:item.followUserId}}" class="card-btn">
          <Icon icon="tabler:eye" width="18"></Icon>
          查看
        </router-link>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>