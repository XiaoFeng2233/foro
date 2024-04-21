<template>
  <div class="layout-header">
    <div class="collapse-btn" @click="model = !model">
      <Icon icon="akar-icons:text-align-right" width="26" v-if="model"></Icon>
      <Icon icon="akar-icons:text-align-left" width="26" v-else></Icon>
    </div>
    <div class="space"></div>
    <el-dropdown>
      <div class="user-band">

        <el-avatar shape="square" :size="35"
                   :src="store.avatar"/>
        <div class="user-info">
          <div class="username">{{store.username}}</div>
          <div class="role">超级管理员</div>
        </div>
      </div>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="router.push('/')">
            <Icon icon="fe:link-external" width="20"></Icon>
            跳转至前台
          </el-dropdown-item>
          <el-dropdown-item @click="logout">
            <Icon icon="material-symbols:logout" width="20"></Icon>
            退出登录
          </el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
</template>
<script setup>
import {Icon} from "@iconify/vue"
import credentialStore from "@/stores/credential.js"
import {useRouter} from "vue-router"
import {defineModel} from "vue"
import {logout as doLogout} from "@/api/user.js";
import localforage from "localforage";
import {ElMessage} from "element-plus";

const store = credentialStore()
const router = useRouter()
const model = defineModel()

const logout = async () => {
  let response = await doLogout()
  if (response.code === 200) {
    await localforage.removeItem("credential")
    credentialStore().clear()
    ElMessage({
      message: '账号退出成功',
      type: 'success',
    })
    router.push("/login")
  }
}
</script>
<style scoped>
.flex-grow {
  flex-grow: 1;
}

.layout-header {
  display: flex;
  background: white;
  padding: 10px 30px;
  justify-content: center;
  align-items: center;
  border-bottom: 1px solid rgb(218, 223, 229);
}

.space {
  flex-grow: 1;
}

.collapse-btn {
  cursor: pointer;
}

.user-band {
  display: flex;
}

.user-band>.user-info {
  display: flex;
  margin-left: 10px;
  flex-direction: column;
  justify-content: center;
  text-align: left;
}

.user-info>.username{
  font-size: 18px;
  font-weight: 700;
}

:focus-visible {
  outline: none;
}
</style>