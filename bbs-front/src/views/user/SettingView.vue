<template>
  <DefaultLayout>
    <div class="page-header d-print-none">
      <div class="row align-items-center">
        <div class="col">
          <div class="page-pretitle"> Setting</div>
          <h2 class="page-title"> 个人设置 </h2></div>
        <div class="col-auto"></div>
      </div>
    </div>
    <div class="row row-cards justify-content-center mt-2">
      <div class="col-md-12">
        <div class="card">
          <div class="col-md-12">
            <div class="p-3">
              <div class="row row-cards" id="vue-user-my-setting" data-v-app="">
                <div class="col-md-4">
                  <div class="row row-cards">
                    <div class="col-md-12">
                      <div class="card card-body">
                        <div class="card-title">基本设置</div>
                        <div class="mb-3">
                          <label class="form-label">用户名</label>
                          <input disabled type="text" class="form-control" :value="userInfo.username">
                        </div>
                        <div class="mb-3">
                          <label class="form-label">邮箱</label>
                          <input disabled type="text" class="form-control" :value="userInfo.email">
                        </div>
                        <div class="mb-3">
                          <label class="form-label">用户组</label>
                          <span class="badge" v-if="hasRole('user')"
                                style="background: rgb(32, 107, 196);">注册会员</span>
                          <span class="badge" v-if="hasRole('admin')" style="background:#20c58e;">超级管理员</span>
                        </div>
                        <div class="mb-3">
                          <label class="form-label">昵称</label>
                          <input type="text" v-model="userInfo.nickname" name="old_pwd" class="form-control"></div>
                        <div class="mb-3">
                          <button class="btn btn-primary" type="submit" @click="doUpdateNickname">提交</button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-md-4">
                  <div class="row row-cards">
                    <div class="col-md-12">
                      <form>
                        <div class="card card-body">
                          <div class="card-title">修改头像</div>
                          <div class="mb-3"><label class="form-label">当前头像
                            <div>
                              <span class="avatar"
                                    :style="{backgroundImage: `url(${userInfo.avatar})`}"></span>
                            </div>
                          </label></div>
                          <div class="mb-3"><label class="form-label">选择头像</label>
                            <input ref="avatarFormRef" name="avatar" type="file"
                                   accept="image/gif, image/png, image/jpeg, image/jpg"
                                   class="form-control"></div>
                          <div class="mb-3">
                            <button class="btn btn-primary" type="submit" @click="doUpdateAvatar">提交</button>
                          </div>
                        </div>
                      </form>
                    </div>

                  </div>
                </div>
                <div class="col-md-4">
                  <form>
                    <div class="card card-body">
                      <div class="card-title">修改背景图片</div>
                      <div class="mb-3"><label class="form-label">当前背景图
                        <div>
                              <span class="avatar"
                                    :style="{backgroundImage: `url(${userInfo.backgroundImage})`}"></span>
                        </div>
                      </label></div>
                      <div class="mb-3"><label class="form-label">选择图片</label>
                        <input ref="backgroundImageFormRef" name="avatar" type="file"
                               accept="image/gif, image/png, image/jpeg, image/jpg"
                               class="form-control"></div>
                      <div class="mb-3">
                        <button class="btn btn-primary" type="submit" @click="doUpdateBackgroundImage">提交</button>
                      </div>
                    </div>
                  </form>
                </div>
                <div class="col-md-4">
                  <div class="row row-cards">
                    <div class="col-md-12">
                      <form>
                        <div class="card card-body">
                          <div class="card-title">修改密码</div>
                          <div class="mb-3">
                            <label class="form-label">旧密码</label>
                            <input v-model="passwordForm.oldPwd" type="password" name="old_pwd" class="form-control">
                          </div>
                          <div class="mb-3">
                            <label class="form-label">新密码</label>
                            <input v-model="passwordForm.newPwd" type="password" name="old_pwd" class="form-control">
                          </div>
                          <div class="mb-3">
                            <label class="form-label">确认密码</label>
                            <input v-model="passwordForm.rePwd" type="password" name="new_pwd" class="form-control">
                          </div>
                          <div class="mb-3">
                            <button class="btn btn-primary" type="submit" @click="doUpdatePassword">提交</button>
                          </div>
                        </div>
                      </form>
                    </div>

                  </div>
                </div>

                <div class="col-md-4">
                  <div class="card card-body">
                    <div class="card-title">其他设置</div>
                    <div class="mb-3"><label class="form-label">个人签名</label>
                      <textarea class="form-control" type="text" v-model="userInfo.description"></textarea>
                    </div>
                    <div class="mb-3">
                      <button class="btn btn-primary" type="submit" @click="doUpdateDescription">提交</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </DefaultLayout>
</template>
<script setup>
import DefaultLayout from "@/layout/DefaultLayout.vue";
import {info, updateNickname, updateDescription, updatePassword, updateAvatar,updateBackgroundImage} from "@/api/user.js"
import {reactive, ref} from "vue"
import {hasRole} from "@/utils/authUtil.js"
import {ElMessage} from "element-plus"
import localforage from "localforage";
import credentialStore from "@/stores/credential.js";
import {useRouter} from "vue-router"


const router = useRouter()
const userInfo = ref({})
const avatarFormRef = ref()
const backgroundImageFormRef = ref()
const passwordForm = reactive({
  oldPwd: "",
  newPwd: "",
  rePwd: ""
})
const getInfo = async () => {
  const response = await info()
  userInfo.value = response.data
}

const doUpdateNickname = async () => {
  if (userInfo.value.nickname == undefined || userInfo.value.nickname == null || userInfo.value.nickname == "") {
    ElMessage({
      message: '新的昵称不能为空',
      type: 'error',
    })
    return false
  }

  const response = await updateNickname(userInfo.value.nickname)
  if (response.code === 200) {
    ElMessage({
      message: response.data,
      type: 'success'
    })

    //1.5秒跳转至登录界面
    setTimeout(async () => {
      await localforage.removeItem("credential")
      credentialStore().clear()
      router.push("/login")
    }, 1500)
  }

}

const doUpdateDescription = async () => {
  if (userInfo.value.description == undefined || userInfo.value.description == null || userInfo.value.description == "") {
    ElMessage({
      message: '新的签名不能为空',
      type: 'error',
    })
    return false
  }

  const response = await updateDescription(userInfo.value.description)
  if (response.code === 200) {
    ElMessage({
      message: response.data,
      type: 'success'
    })

    //1.5秒跳转至登录界面
    setTimeout(async () => {
      await localforage.removeItem("credential")
      credentialStore().clear()
      router.push("/login")
    }, 1500)
  }

}

const doUpdatePassword = async (event) => {
  event.preventDefault()
  if (passwordForm.oldPwd == undefined || passwordForm.oldPwd == "") {
    ElMessage({
      message: '旧的密码不能为空',
      type: 'error',
    })
    return false
  }
  if (passwordForm.newPwd == undefined || passwordForm.newPwd == "") {
    ElMessage({
      message: '新的密码不能为空',
      type: 'error',
    })
    return false
  }

  if (passwordForm.rePwd == undefined || passwordForm.rePwd == "") {
    ElMessage({
      message: '确认密码不能为空',
      type: 'error',
    })
    return false
  }


  if (passwordForm.rePwd != passwordForm.newPwd) {
    ElMessage({
      message: '两次密码输入不一致',
      type: 'error',
    })
    return false
  }

  const response = await updatePassword(passwordForm.oldPwd, passwordForm.newPwd)
  if (response.code === 200) {
    ElMessage({
      message: response.data,
      type: 'success'
    })

    //1.5秒跳转至登录界面
    setTimeout(async () => {
      await localforage.removeItem("credential")
      credentialStore().clear()
      router.push("/login")
    }, 1500)
  }

}

const doUpdateAvatar = async (event) => {
  event.preventDefault()
  const file = avatarFormRef.value.files[0]
  if (file == undefined) {
    ElMessage({
      message: "请选择要上传的图片文件",
      type: 'error'
    })
    return false;
  }

  const response = await updateAvatar(file)
  if(response.code === 200){
    ElMessage({
      message:"头像修改成功",
      type:"success"
    })

    userInfo.value.avatar = response.data.fileUrl

    //1.5秒跳转至登录界面
    setTimeout(async () => {
      await localforage.removeItem("credential")
      credentialStore().clear()
      router.push("/login")
    }, 1500)
  }

}

const doUpdateBackgroundImage = async (event) => {
  event.preventDefault()
  const file = backgroundImageFormRef.value.files[0]
  if (file == undefined) {
    ElMessage({
      message: "请选择要上传的图片文件",
      type: 'error'
    })
    return false;
  }

  const response = await updateBackgroundImage(file)
  if(response.code === 200){
    ElMessage({
      message:"背景修改成功",
      type:"success"
    })

    userInfo.value.backgroundImage = response.data.fileUrl

    //1.5秒跳转至登录界面
    setTimeout(async () => {
      await localforage.removeItem("credential")
      credentialStore().clear()
      router.push("/login")
    }, 1500)
  }

}
getInfo()
</script>
<style scoped>

</style>