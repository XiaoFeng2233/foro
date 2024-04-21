<template>
  <auth-layout>
    <div class="card card-md">
      <div class="card-body">
        <h2 class="h2 text-center mb-4">注册新的账号</h2>
        <div id="vue-core-sign-register">
          <form autocomplete="off">
            <div class="mb-3">
              <label class="form-label">邮箱</label>
              <input v-model="registerData.email" autocomplete="off" type="email" class="form-control"
                     placeholder="请输入邮箱地址">
            </div>
            <div class="mb-3">
              <label class="form-label">用户名</label>
              <input v-model="registerData.username" autocomplete="off" type="text" class="form-control"
                     placeholder="请输入用户名">
            </div>
            <div class="mb-3">
              <label class="form-label">
                密码
              </label>
              <input v-model="registerData.password" type="password" class="form-control"
                     placeholder="请输入登录密码">
            </div>
            <div class="mb-2">
              <label class="form-label">
                重复密码
              </label>
              <input v-model="registerData.rePassword" type="password" class="form-control"
                     placeholder="请再次输入密码">
            </div>


            <div class="mb-3">
              <label for="" class="form-label">验证码</label>
              <div class="row">
                <div class="col-8">
                  <input v-model="registerData.captcha" type="text" class="form-control" placeholder="请输入验证码">
                </div>
                <div class="col-4">
                  <el-image @click="changeCaptcha" class="cursor-pointer" style="width: 100%;height: 40px"
                            :src="captchaUrl">
                  </el-image>
                </div>
              </div>
            </div>

            <div class="form-footer">
              <button type="button" class="btn btn-primary w-100" :disabled="buttonDisabled" @click="doRegister">
                立即注册
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
    <div class="text-center text-muted mt-3">
      已有账号?
      <router-link to="/login">
        立即登陆
      </router-link>
    </div>
  </auth-layout>
</template>

<script setup lang="ts">
import {reactive, ref} from "vue"
import {ElMessage, ElNotification} from "element-plus";
import AuthLayout from "@/layout/AuthLayout.vue"
import lodash from "lodash"
import validator from "validator"
import {register, registerCaptcha} from "@/api/auth/register"
import {useRouter,useRoute} from "vue-router";
import siteConfigStore from "@/stores/siteconfig"



const router = useRouter()
const route = useRoute()
const store = siteConfigStore()
if(store.openRegister === false ){
  router.push({name:"CloseRegister"})
}

//自定义验证码图片基址
const captchaBaseUrl = import.meta.env.VITE_LOGIN_CAPTCHA_IMAGE_BASE_URL

//自定义验证码图片URL
const captchaUrl = ref<String>("")
const buttonDisabled = ref(false)
const registerData = reactive({
  username: "",
  password: "",
  rePassword: "",
  email: "",
  captcha: "",
  captchaId: ""
})

const getCaptcha = async () => {
  let response = await registerCaptcha();
  let id = response.data
  registerData.captchaId = id
  captchaUrl.value = captchaBaseUrl + id
}

//定义更换验证码函数
const changeCaptcha = () => {
  getCaptcha()
}

const verifyRegisterParam = () => {
  if (lodash.isEmpty(registerData.email)) {
    ElMessage({
      message: '请填写邮箱地址',
      type: 'warning',
    })
    buttonDisabled.value = false
    return false;
  }

  if (lodash.isEmpty(registerData.username)) {
    ElMessage({
      message: '请填写用户名',
      type: 'warning',
    })
    buttonDisabled.value = false
    return false;
  }

  if (lodash.isEmpty(registerData.password)) {
    ElMessage({
      message: '请填写密码',
      type: 'warning',
    })
    buttonDisabled.value = false
    return false;
  }

  if (lodash.isEmpty(registerData.rePassword)) {
    ElMessage({
      message: '请填写确认密码',
      type: 'warning',
    })
    buttonDisabled.value = false
    return false;
  }

  if (lodash.isEmpty(registerData.captcha)) {
    ElMessage({
      message: '请填写验证码',
      type: 'warning',
    })
    buttonDisabled.value = false
    return false;
  }

  if (registerData.password !== registerData.rePassword) {
    ElMessage({
      message: '两次密码输入不一致',
      type: 'warning',
    })
    buttonDisabled.value = false
    return false;
  }

  if (!validator.isEmail(registerData.email)) {
    ElMessage({
      message: '邮箱格式输入有误',
      type: 'warning',
    })
    buttonDisabled.value = false
    return false;
  }

  return true;

}

const doRegister = async (e) => {
  e.preventDefault()

  buttonDisabled.value = true

  if(verifyRegisterParam()){
    let response = await register(registerData.email, registerData.username, registerData.password, registerData.captcha, registerData.captchaId)
    if (response.code === 500) {
      await getCaptcha()
      buttonDisabled.value = false
      return;
    }

    if(response.code === 200){
      ElNotification({
        title: "注册成功",
        message: "请在15分钟内打开邮箱中的验证链接进行账户验证",
        type: 'success',
      })

      setTimeout(()=>{
        router.push("/login")
      },2000)
      return;
    }

    buttonDisabled.value = false
    await getCaptcha()
  }else{
    buttonDisabled.value = false
  }


}

getCaptcha()

</script>

<style scoped>

</style>