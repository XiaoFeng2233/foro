<script setup>
import AuthLayout from "@/layout/AuthLayout.vue"
import {ref, reactive} from "vue"
import {forgetPasswordCaptcha,forgetPassword} from "@/api/auth/forgetpassword.js"
import {ElNotification} from "element-plus"


const formData = reactive({
  email: undefined,
  captcha: undefined
})
//自定义验证码图片基址
const captchaBaseUrl = import.meta.env.VITE_FORGETPASSWORD_CAPTCHA_IMAGE_BASE_URL
//自定义验证码图片URL
const captchaUrl = ref("")
const captchaId = ref("")
const buttonDisabled = ref(false)
const getCaptcha = async () => {
  let response = await forgetPasswordCaptcha();
  let id = response.data
  captchaId.value = id
  captchaUrl.value = captchaBaseUrl + id
}

const changeCaptcha = async () => {
  await getCaptcha()
}
await getCaptcha()

const doForgetPassword = async () => {
  if(formData.captcha === undefined || formData.captcha === ""){
    ElNotification({
      type:"error",
      message:"验证码不能为空",
      duration:1500,
      title:"失败"
    })
    return false;
  }
  if(formData.email === undefined || formData.email === ""){
    ElNotification({
      type:"error",
      message:"邮箱不能为空",
      duration:1500,
      title:"失败"
    })
    return false;
  }
  buttonDisabled.value = true
  const response = await forgetPassword(formData.email,formData.captcha,captchaId.value)
  if(response.code === 200){
    ElNotification({
      type:"success",
      message:response.data,
      duration:1500,
      title:"成功"
    })
    return true;
  }else{
    buttonDisabled.value = false
    await getCaptcha()
  }
}
</script>

<template>
  <auth-layout>
    <div class="card card-md">
      <div class="card-body">
        <div id="vue-core-forgot-password">
          <div>
            <form>
              <h2 class="card-title text-center mb-4">找回密码</h2>
              <div class="mb-3">
                <label class="form-label">邮箱</label>
                <input type="email" class="form-control" placeholder="请输入邮箱地址" v-model="formData.email">
              </div>
              <div class="mb-3">
                <label for="" class="form-label">验证码</label>
                <div class="row">
                  <div class="col-8">
                    <input type="text" class="form-control" placeholder="请输入验证码" v-model="formData.captcha">
                  </div>
                  <div class="col-4">
                    <el-image @click="changeCaptcha" class="cursor-pointer" style="width: 100%;height: 40px"
                              :src="captchaUrl">
                    </el-image>
                  </div>
                </div>
              </div>

              <div class="form-footer">
                <button type="button" class="btn btn-primary w-100" :disabled="buttonDisabled" @click="doForgetPassword">
                  发送找回密码邮件
                </button>
              </div>

            </form>
          </div>
        </div>

      </div>
    </div>

    <div class="text-center text-muted mt-3"> 想起来了?
      <router-link to="/login">立即登陆</router-link>
    </div>
  </auth-layout>
</template>

<style scoped>

</style>