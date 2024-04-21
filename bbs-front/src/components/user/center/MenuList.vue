<template>
  <ul class="nav nav-pills nav-vertical">
    <li class="nav-item">
      <router-link :to="{name:'UserCenterPanel'}"
                   :class="[{active:route.name === 'UserCenterPanel'},'row', 'nav-link']">
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
      <router-link :to="{name:'UserLoginLog'}" :class="[{active:route.name === 'UserLoginLog'},'row', 'nav-link']">
        <div class="col">
          <Icon icon="mdi:clock-outline" :width="20" :height="20" :inline="true"></Icon>
          登录记录
        </div>
        <div class="col-auto">
          <Icon icon="mingcute:right-fill" width="18" height="18" :inline="true"></Icon>
        </div>
      </router-link>
    </li>


    <li class="nav-item">
      <router-link :to="{name:'UserBanLog'}" :class="[{active:route.name === 'UserBanLog'},'row', 'nav-link']">
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
      <router-link :to="{name:'UserTopicList'}" :class="[{active:route.name === 'UserTopicList'},'row', 'nav-link']">
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
      <router-link :to="{name:'UserCommentList'}"
                   :class="[{active:route.name === 'UserCommentList'},'row', 'nav-link']">
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
      <router-link :to="{name:'UserFollowList'}" :class="[{active:route.name === 'UserFollowList'},'row', 'nav-link']">
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
      <router-link :to="{name:'UserFansList'}" :class="[{active:route.name === 'UserFansList'},'row', 'nav-link']">
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
      <router-link :to="{name:'UserCollectList'}"
                   :class="[{active:route.name === 'UserCollectList'},'row', 'nav-link']">
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

<script setup>
import {Icon} from "@iconify/vue"
import {useRoute} from "vue-router"
import {ref, watch} from "vue";


const menuValue = ref("")
const route = useRoute();

watch(() => route, (newRoute, oldRoute) => {
  menuValue.value = newRoute.name
}, {immediate: true});

</script>

<style scoped>

</style>