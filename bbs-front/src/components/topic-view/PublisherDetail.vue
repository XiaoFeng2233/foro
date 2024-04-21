<template>
  <div class="col-md-12">
    <router-link class="card card-link" :to="{name:'OtherUserCenterPanel',params:{id:prop.user.id}}">
      <div class="card-cover card-cover-blurred text-center"
           :style="{backgroundImage: `url(${prop.user.backgroundImage})`}">
                              <span class="avatar avatar-xl avatar-thumb avatar-rounded"
                                    :style="{backgroundImage: `url(${prop.user.avatar})`}"></span>
      </div>
      <div class="card-body text-center">
        <div class="card-title mb-1">
          <span class="text-reset">{{prop.user.nickname}}</span>
        </div>
        <div class="text-muted">本文作者，至今共发布{{prop.user.topicCount}}篇文章</div>
      </div>
    </router-link>
  </div>
</template>
<script setup>
import {defineProps} from "vue"

const prop = defineProps(["user"])
</script>
<style scoped>

</style>