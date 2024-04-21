<script setup>
import {defineProps} from "vue"
import {signIn} from "@/api/user.js"
import {ElMessage} from "element-plus"


const prop = defineProps(["userInfo"])
const doSignIn = async ()=>{
  const response = await signIn()
  if (response.code === 200) {
    ElMessage.success({
      message:"签到成功，获得" + response.data +"积分",
      duration:1500
    })

    //1.5秒后刷新网页
    setTimeout(function () {
      window.location.reload()
    }, 1500)
  }
}

</script>

<template>
  <div class="card">
    <div class="card-header" style="padding: 10px 20px 10px 10px">
      <h3 class="card-title">签到</h3>
    </div>
    <div class="card-body text-center" style="font-size: 18px">
      <div class="text-secondary" v-if="prop.userInfo.signed">您今日已经签到过了</div>
      <button id="home-right-widget-task-checkin-button" class="btn btn-primary" v-else @click="doSignIn">立即签到</button>
    </div>
  </div>
</template>

<style scoped>

</style>