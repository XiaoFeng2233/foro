<template>
  <div class="wrapper">
    <!--      头部菜单-->
    <header class="navbar navbar-expand-md navbar-light d-print-none">
      <div class="container-xl">
        <h1
            class="navbar-brand navbar-brand-autodark d-none-navbar-horizontal pe-0 pe-md-3"
        >
          <router-link to="/">
            <img
                class="navbar-brand-image"
                :src="logoUrl"
                alt="LOGO"/>
          </router-link>
        </h1>
        <div class="flex-row navbar-nav order-md-last" v-if="isAuth()">

          <div class="nav-item d-none d-md-flex me-3">
            <router-link :to="{name:'UserNotice'}" class="px-0 nav-link">
              <el-badge :value="3" class="item" is-dot>
                <Icon icon="mdi:bell-outline" width="22" height="22" :inline="true"></Icon>
              </el-badge>
            </router-link>
          </div>

          <el-dropdown class="without-outline">
            <div class="nav-item">
              <div class="p-0 nav-link d-flex lh-1 text-reset">
                <el-avatar :size="35" :src="avatar"/>
                <div class="d-none d-xl-block ps-2">
                  <div>{{ username }}</div>
                  <div class="mt-1 small text-muted">本站第 {{ id }} 位会员</div>
                </div>
              </div>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="router.push({name:'UserCenterPanel'})">
                  <Icon icon="mingcute:user-4-line" width="18" height="18" :inline="true" class="me-1"></Icon>
                  个人中心
                </el-dropdown-item>
                <el-dropdown-item @click="router.push({name:'UserCollectList'})">
                  <Icon icon="material-symbols:kid-star-outline" width="18" height="18" :inline="true"
                        class="me-1"></Icon>
                  我的收藏
                </el-dropdown-item>
                <el-dropdown-item @click="router.push({name:'UserNotice'})">
                  <Icon icon="mdi:bell-outline" width="18" height="18" :inline="true" class="me-1"></Icon>
                  我的通知
                </el-dropdown-item>
                <el-dropdown-item divided @click="router.push({name:'UserSetting'})">
                  <Icon icon="uil:setting" width="18" height="18" :inline="true" class="me-1"></Icon>
                  个人设置
                </el-dropdown-item>
                <el-dropdown-item @click="router.push({name:'AdminIndex'})" v-if="hasRole('admin')">
                  <Icon icon="eos-icons:admin-outlined" width="18" height="18" :inline="true" class="me-1"></Icon>
                  管理员后台
                </el-dropdown-item>
                <el-dropdown-item @click="logout">
                  <Icon icon="material-symbols:logout" width="18" height="18" :inline="true" class="me-1"></Icon>
                  退出
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>

        </div>
        <div class="flex-row navbar-nav" v-else>
          <div class="nav-item">
              <span class="d-md-block">
                <router-link to="/register" class="btn btn-light mx-4">注册</router-link>
                <router-link to="/login" class="btn btn-dark">登陆</router-link>
              </span>
          </div>
        </div>
      </div>
    </header>

    <!--拓展菜单-->
    <div class="navbar-expand-md">
      <div class="collapse navbar-collapse" id="navbar-menu">
        <div class="navbar navbar-light">
          <div class="container-xl">
            <ul class="navbar-nav">
              <li class="nav-item" :class="{'active':route.name === 'Home'}">
                <router-link class="nav-link" to="/">
                    <span class="nav-link-icon d-md-none d-lg-inline-block">
                      <Icon icon="mdi:home" :width="24" :height="24" :inline="true"/>
                    </span>
                  <span class="nav-link-title"> 首页 </span>
                </router-link>
              </li>

              <li class="nav-item" :class="{'active':route.name === 'Tags'}">
                <router-link class="nav-link" to="/tags">
                    <span class="nav-link-icon d-md-none d-lg-inline-block">
                      <Icon icon="mdi:tag" :width="24" :height="24" :inline="true"/>
                    </span>
                  <span class="nav-link-title"> 板块 </span>
                </router-link>
              </li>
              <li class="nav-item" :class="{'active':route.name === 'Users'}">
                <router-link class="nav-link" to="/users">
                    <span class="nav-link-icon d-md-none d-lg-inline-block">
                      <Icon icon="mdi:users" :width="24" :height="24" :inline="true"/>
                    </span>
                  <span class="nav-link-title"> 用户列表 </span>
                </router-link>
              </li>
            </ul>
            <div class="order-first my-2 my-md-0 flex-grow-1 flex-md-grow-0 order-md-last d-flex flex-row">
              <div class="col mx-2">
                <div>
                  <div class="row g-2">
                    <div class="col">
                      <div class="input-icon">
                        <input v-model="searchParam" type="text" class="form-control"
                               placeholder="输入要搜索的内容..."/>
                      </div>
                    </div>
                    <div class="col-auto">
                      <button class="btn btn-icon" @click="search">
                        <Icon icon="mdi:search" :width="24" :height="24" :inline="true"/>
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import {Icon} from "@iconify/vue"
import siteConfigStore from "@/stores/siteconfig.js"
import credentialStore from "@/stores/credential.js"
import {useRoute, useRouter} from "vue-router"
import {isAuth, hasRole} from "@/utils/authUtil.js"
import {ref} from "vue"
import {logout as doLogout} from "@/api/user.js"
import localforage from "localforage"
import {ElMessage, ElNotification} from "element-plus"


const route = useRoute();
const router = useRouter();
const config = siteConfigStore()
const logoUrl = config.logo
const username = ref(credentialStore().username)
const avatar = ref(credentialStore().avatar)
const id = ref(credentialStore().id)
const searchParam = ref("")

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

const search = () => {
  if (searchParam.value != null && searchParam.value != undefined && searchParam.value != "") {
    router.push({
      name: "Search",
      params: {
        param: searchParam.value
      }
    })
  } else {
    ElNotification({
      message: "搜索内容不能为空",
      type: "error",
      title: "失败",
      duration: 1500
    })
  }
}
</script>


<style scoped>
:focus-visible {
  outline: none;
}
</style>