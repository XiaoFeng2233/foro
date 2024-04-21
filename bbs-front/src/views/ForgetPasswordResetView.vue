<script setup>
import {ref, reactive} from "vue"
import {getForgetPasswordInfo,resetPassword} from "@/api/auth/forgetpassword.js"
import {useRoute, useRouter} from "vue-router"
import {ElNotification} from "element-plus"


const route = useRoute();
const router = useRouter();
const buttonDisabled = ref(false)
const formData = reactive({
  email: undefined,
  username: undefined,
  password: undefined,
  rePassword: undefined
})
const doGetInfo = async () => {
  const response = await getForgetPasswordInfo(route.params.token, route.params.code)
  if (response.code === 200) {
    formData.email = response.data.email
    formData.username = response.data.username

  } else {
    router.push("/")
  }
}

await doGetInfo()

const doResetPassword = async (e)=>{
  e.preventDefault()
  if(formData.password === undefined || formData.password === ""){
    ElNotification({
      type:"error",
      message:"密码不能为空",
      duration:1500,
      title:"失败"
    })
    return false;
  }
  if(formData.rePassword === undefined || formData.rePassword === ""){
    ElNotification({
      type:"error",
      message:"重复密码不能为空",
      duration:1500,
      title:"失败"
    })
    return false;
  }
  if(formData.rePassword !== formData.password){
    ElNotification({
      type:"error",
      message:"两次密码输入不一致",
      duration:1500,
      title:"失败"
    })
    return false;
  }

  buttonDisabled.value = true
  const response = await resetPassword(route.params.token,route.params.code,formData.password);
  if(response.code === 200){
    ElNotification({
      message:response.data,
      duration:1500,
      title:"密码重置成功",
      type:"success"
    })
    setTimeout(()=>{
      router.push({name:"Login"})
    },1500)
  }else{
    buttonDisabled.value = false

  }
}
</script>

<template>
  <div class="d-flex justify-content-center align-items-center vw-100 vh-100">
    <div class="card card-md" style="width: 500px">
      <div class="card-body">
        <h2 class="h2 text-center mb-4">
          重置您的密码
        </h2>
        <div id="vue-core-sign-login-username">
          <form>
            <div class="mb-3">
              <label class="form-label">
                邮箱
              </label>
              <input type="text" class="form-control" disabled v-model="formData.email" >
            </div>
            <div class="mb-3">
              <label class="form-label">
                用户名
              </label>
              <input type="text" class="form-control" disabled v-model="formData.username">
            </div>
            <div class="mb-2">
              <label class="form-label">
                新的密码
              </label>
              <input type="password" class="form-control" placeholder="请输入登录密码" v-model="formData.password"/>

            </div>
            <div class="mb-2">
              <label class="form-label">
                确认密码
              </label>
              <input type="password" class="form-control" placeholder="请再次输入登录密码" v-model="formData.rePassword"/>

            </div>
            <div class="form-footer">
              <button type="submit" class="btn btn-primary w-100" @click="doResetPassword" :disabled="buttonDisabled">
                立即重置
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>

</template>

<style scoped>

</style>