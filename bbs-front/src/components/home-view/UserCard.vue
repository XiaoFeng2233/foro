<script setup>

import SignIn from "@/components/home-view/SignIn.vue";
import UserInfo from "@/components/home-view/UserInfo.vue";
import {ref} from "vue";
import {generalInfo} from "@/api/user.js";

const userInfo = ref({})

const doGetUserGeneralInfo = async ()=>{
  const response = await  generalInfo()
  if(response.code === 200){
    userInfo.value = response.data
  }
}

doGetUserGeneralInfo()
</script>

<template>
  <div class="col-md-12  d-none d-lg-block">
    <UserInfo :user-info="userInfo"></UserInfo>
  </div>
  <div class="col-12">
    <SignIn :user-info="userInfo"></SignIn>
  </div>
</template>

<style scoped>

</style>