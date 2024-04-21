<script setup>
import {Icon}  from "@iconify/vue"
import {defineProps} from "vue"

const prop = defineProps(["userInfo"])
</script>

<template>
  <div class="card">
    <div class="card-cover card-cover-blurred text-center" :style="{backgroundImage: `url(${prop.userInfo.backgroundImage})`}">
      <span class="avatar avatar-xl avatar-thumb avatar-rounded" :style="{backgroundImage: `url(${prop.userInfo.avatar})`}"></span>
    </div>
    <div class="card-body text-center">
      <div class="card-title mb-1"><span style="" class="text-reset">{{prop.userInfo.nickname}}</span></div>
      <div class="text-muted">至今共发布{{prop.userInfo.topicCount}}篇文章</div>

    </div>
    <div class="d-flex">
      <router-link :to="{name:'UserCreateTopic'}" class="card-btn">
        <Icon icon="system-uicons:write" width="20" height="20"></Icon>
        发帖
      </router-link>
      <router-link :to="{name:'UserCenterPanel'}" class="card-btn">
        <Icon icon="lucide:user-round" width="20" height="20"></Icon>个人中心
      </router-link>
    </div>
  </div>
</template>

<style scoped>

</style>