<template>
  <auth-layout>
    <div class="card card-md">
      <div class="card-body">
        <h2 class="h2 text-center mb-4">
          登录到您的帐户
        </h2>
        <div id="vue-core-sign-login-username">
          <form>
            <div class="mb-3">
              <label class="form-label">
                用户名
              </label>
              <input type="text" class="form-control" placeholder="请输入用户名" v-model="loginData.username">
            </div>
            <div class="mb-2">
              <label class="form-label">
                密码
                <span class="form-label-description">
													<router-link tabindex="-1" to="/forget-password">
														忘记密码?
													</router-link>
												</span>
              </label>
              <input type="password" class="form-control" placeholder="请输入登录密码"
                     v-model="loginData.password">
            </div>
            <div class="mb-3">
              <label class="form-label">
                验证码
              </label>
              <div class="row d-flex flex-row justify-content-center align-items-center">
                <div class="col-8">
                  <input type="text" class="form-control" placeholder="请输入验证码"
                         v-model="loginData.captcha">
                </div>
                <div class="col-4">
                  <el-image @click="changeCaptcha" class="cursor-pointer" style="width: 100%;height: 40px"
                            :src="captchaUrl">
                  </el-image>
                </div>
              </div>

            </div>
            <div class="form-footer">
              <button type="submit" class="btn btn-primary w-100" @click="doLogin" :disabled="buttonDisabled">
                登陆
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
    <div class="text-center text-muted mt-3">
      还没有账号?
      <router-link to="/register" tabindex="-1">
        立即注册
      </router-link>
    </div>
  </auth-layout>

</template>

<script setup>
import {reactive, ref} from "vue"
import AuthLayout from "@/layout/AuthLayout.vue";
import lodash from "lodash";
import {ElMessage, ElNotification} from "element-plus";
import {login, loginCaptcha} from "@/api/auth/login.js"
import credentialStore from "@/stores/credential.js";
import localforage from "localforage";
import {useRouter} from "vue-router"

const router = useRouter();

//自定义验证码图片基址
const captchaBaseUrl = import.meta.env.VITE_LOGIN_CAPTCHA_IMAGE_BASE_URL

//自定义验证码图片URL
const captchaUrl = ref("")

const buttonDisabled = ref(false)

const captchaId = ref("")

//登录数据
const loginData = reactive({
  username: "",
  password: "",
  captcha: "",
})


const verifyLoginParam = (username, password, captcha, captchaId) => {
  if (lodash.isEmpty(username)) {
    ElMessage({
      message: '用户名不能为空',
      type: 'warning',
    })
    return false;
  }
  if (lodash.isEmpty(password)) {
    ElMessage({
      message: '密码不能为空',
      type: 'warning',
    })
    return false;
  }
  if (lodash.isEmpty(captcha)) {
    ElMessage({
      message: '验证码不能为空',
      type: 'warning',
    })
    return false;
  }
  if (lodash.isEmpty(captchaId)) {
    return false;
  }
  return true;
}

const getCaptcha = async () => {
  let response = await loginCaptcha();
  let id = response.data
  captchaId.value = id
  captchaUrl.value = captchaBaseUrl + id
}

const changeCaptcha = () => {
  getCaptcha()
}

const doLogin = async (e) => {
  e.preventDefault()
  const store = credentialStore()
  buttonDisabled.value = true

  if (verifyLoginParam(loginData.username, loginData.password, loginData.captcha, captchaId.value)) {
    let response = await login(loginData.username, loginData.password, loginData.captcha, captchaId.value)
    if (response.code === 500) {
      await getCaptcha()
      buttonDisabled.value = false
      return;
    }

    if (response.code === 200) {

      //将token和用户名保存到本地
      localforage.setItem("credential", {
        ...response.data
      }).then(() => {

        //将token和用户名保存到store中
        store.set(response.data.id,response.data.token,response.data.username,response.data.avatar,response.data.roles,response.data.permissions,response.data.mangedTagIds)
        ElNotification({
          title: "登录成功",
          message: "即将跳转至主页",
          type: 'success',
        })

        setTimeout(() => {
          router.push("/")
        }, 1500)
      })

      return;
    }

    buttonDisabled.value = false
    await getCaptcha()
  } else {
    buttonDisabled.value = false
  }

}


getCaptcha()


</script>

<style scoped>

</style>