<script setup>
import {useRoute} from "vue-router"
import {Icon} from "@iconify/vue";

const route = useRoute()
</script>

<template>
  <ul class="nav nav-pills nav-vertical">
    <li class="nav-item">
      <router-link :to="{name:'OtherUserCenterPanel'}" :class="[{active:route.name === 'OtherUserCenterPanel'},'row', 'nav-link']">
        <div class="col">
          <Icon icon="solar:user-linear" :width="20" :height="20" :inline="true"></Icon>
          概览
        </div>
        <div class="col-auto">
          <Icon icon="mingcute:right-fill" width="18" height="18" :inline="true"></Icon>
        </div>
      </router-link>
    </li>

    <li class="nav-item">
      <router-link :to="{name:'OtherUserBanLog'}" :class="[{active:route.name === 'OtherUserBanLog'},'row', 'nav-link']">
        <div class="col">
          <Icon icon="solar:forbidden-circle-line-duotone" :width="20" :height="20" :inline="true"></Icon>
          小黑屋
        </div>
        <div class="col-auto">
          <Icon icon="mingcute:right-fill" width="18" height="18" :inline="true"></Icon>

        </div>
      </router-link>
    </li>
    <li class="nav-item">
      <router-link :to="{name:'OtherUserTopic'}" :class="[{active:route.name === 'OtherUserTopic'},'row', 'nav-link']">
        <div class="col">
          <Icon icon="ph:newspaper" :width="20" :height="20" :inline="true"></Icon>
          帖子
        </div>
        <div class="col-auto">
          <Icon icon="mingcute:right-fill" width="18" height="18" :inline="true"></Icon>
        </div>
      </router-link>
    </li>
    <li class="nav-item">
      <router-link :to="{name:'OtherUserComment'}" :class="[{active:route.name === 'OtherUserComment'},'row', 'nav-link']">
        <div class="col">
          <Icon icon="octicon:comment-16" :width="20" :height="20" :inline="true"></Icon>

          评论
        </div>
        <div class="col-auto">
          <Icon icon="mingcute:right-fill" width="18" height="18" :inline="true"></Icon>
        </div>
      </router-link>
    </li>
    <li class="nav-item">
      <router-link :to="{name:'OtherUserFollow'}" :class="[{active:route.name === 'OtherUserFollow'},'row', 'nav-link']">
        <div class="col">
          <Icon icon="ph:users" :width="20" :height="20" :inline="true"></Icon>

          关注
        </div>
        <div class="col-auto">
          <Icon icon="mingcute:right-fill" width="18" height="18" :inline="true"></Icon>
        </div>
      </router-link>
    </li>
    <li class="nav-item">
      <router-link  :to="{name:'OtherUserFans'}" :class="[{active:route.name === 'OtherUserFans'},'row', 'nav-link']">
        <div class="col">
          <Icon icon="ph:users" :width="20" :height="20" :inline="true"></Icon>
          粉丝
        </div>
        <div class="col-auto">
          <Icon icon="mingcute:right-fill" width="18" height="18" :inline="true"></Icon>
        </div>
      </router-link>
    </li>
    <li class="nav-item">
      <router-link :to="{name:'OtherUserCollect'}" :class="[{active:route.name === 'OtherUserCollect'},'row', 'nav-link']">
        <div class="col">
          <Icon icon="solar:star-linear" :width="20" :height="20" :inline="true"></Icon>
          收藏
        </div>
        <div class="col-auto">
          <Icon icon="mingcute:right-fill" width="18" height="18" :inline="true"></Icon>
        </div>
      </router-link>
    </li>


  </ul>
</template>

<style scoped>

</style>