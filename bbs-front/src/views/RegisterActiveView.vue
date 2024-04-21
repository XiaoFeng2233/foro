<template>
  <div class="page page-center">
    <div class="container-tight py-4">
      <div class="empty" id="empty">
        <div class="empty-header">
        <Icon icon="flat-color-icons:ok" width="70" height="70" v-if="status === 'success'"></Icon>
          <Icon icon="flat-color-icons:cancel" width="70" height="70" v-if="status === 'fail'"></Icon>
          <span class="me-2" v-if="status === 'success'">成功</span>
          <span class="me-2" v-if="status === 'fail'">错误</span>
        </div>
        <p class="empty-title">{{msg}}</p>
        <div class="empty-action">
          <router-link to="/login" class="btn btn-primary" v-if="status === 'success'">
            <Icon icon="mingcute:arrow-left-fill" width="20" height="20" class="me-1"></Icon>
            跳转至登录界面
          </router-link>
          <router-link to="/" class="btn btn-primary" v-else>
            <Icon icon="mingcute:arrow-left-fill" width="20" height="20" class="me-1"></Icon>
            跳转至主界面
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {useRoute, useRouter} from "vue-router"
import lodash from "lodash"
import {registerActive} from "@/api/auth/register.js"
import {ref} from "vue"
import {Icon} from "@iconify/vue"


const route = useRoute()
const router = useRouter()
const token = route.params.token
const code = route.params.code

const msg = ref("")
const status = ref("")

const active = async () => {
  if (lodash.isEmpty(token) || lodash.isEmpty(code)) {
    router.push("/404")
    return false;
  }

  let response = await registerActive(token,code)
  if (response.code === 200) {
    status.value = "success"
    msg.value = response.msg
    setTimeout(()=>{
      router.push("/login")
    },3000)
  }else{
    status.value = "fail"
    msg.value = response.msg
  }
}

active()
</script>

<style scoped>

</style>