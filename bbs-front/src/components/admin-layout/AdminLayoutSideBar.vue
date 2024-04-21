

<template>
  <el-menu
      style="height: 100%"
      active-text-color="#ffffff"
      background-color="#001529"
      :default-active="menuValue"
      text-color="rgba(255, 255, 255, 0.7)"
      :collapse="model"
  >
    <el-menu-item index="AdminIndex" @click="router.push({name:'AdminIndex'})">
      <img
          src="/image/LOGO.png"
          alt="Element logo"
      />
    </el-menu-item>

    <el-menu-item index="AdminIndex" @click="router.push({name:'AdminIndex'})">
      <el-icon>
        <Icon icon="clarity:dashboard-line"/>
      </el-icon>
      <span>主页</span>
    </el-menu-item>

    <el-sub-menu index="config-sub-menu">
      <template #title>
        <el-icon>
          <Icon icon="uil:setting"/>
        </el-icon>
        <span>网站设置</span>
      </template>

      <el-menu-item index="AdminMailConfig" @click="router.push({name:'AdminMailConfig'})">
        <el-icon>
          <Icon icon="uil:message"/>
        </el-icon>
        <span>邮箱配置</span>
      </el-menu-item>

      <el-menu-item index="AdminSiteConfig" @click="router.push({name:'AdminSiteConfig'})">
        <el-icon>
          <Icon icon="uil:setting"/>
        </el-icon>
        <span>站点设置</span>
      </el-menu-item>

      <el-menu-item index="AdminStorageConfig" @click="router.push({name:'AdminStorageConfig'})">
        <el-icon>
          <Icon icon="icon-park-outline:cloud-storage"/>
        </el-icon>
        <span>存储设置</span>
      </el-menu-item>
    </el-sub-menu>

    <el-sub-menu index="sub-menu-user">
      <template #title>
        <el-icon>
          <Icon icon="la:users-cog"/>
        </el-icon>
        <span>用户管理</span>
      </template>

      <el-menu-item index="AdminUserList" @click="router.push({name:'AdminUserList'})">
        <el-icon>
          <Icon icon="mdi:users-outline"/>
        </el-icon>
        <span>用户列表</span>
      </el-menu-item>

      <el-menu-item index="AdminManagerList" @click="router.push({name:'AdminManagerList'})">
        <el-icon>
          <Icon icon="wpf:administrator"/>
        </el-icon>
        <span>版主管理</span>
      </el-menu-item>
    </el-sub-menu>


    <el-sub-menu index="444">
      <template #title>
        <el-icon>
          <Icon icon="mdi:tag-outline"/>
        </el-icon>
        <span>板块管理</span>
      </template>

      <el-menu-item  index="AdminTagList" @click="router.push({name:'AdminTagList'})">
        <el-icon>
          <Icon icon="f7:tag"/>
        </el-icon>
        <span>管理</span>
      </el-menu-item>

      <el-menu-item index="AdminAddTag" @click="router.push({name:'AdminAddTag'})">
        <el-icon>
          <Icon icon="gg:add"/>
        </el-icon>
        <span>添加</span>
      </el-menu-item>
    </el-sub-menu>
    <el-menu-item  index="AdminTopicManage" @click="router.push({name:'AdminTopicManage'})">
      <el-icon>
        <Icon icon="iconoir:post"/>
      </el-icon>
      <span>帖子管理</span>
    </el-menu-item>


<!--    <el-menu-item index="3">-->
<!--      <el-icon>-->
<!--        <Icon icon="material-symbols:security"></Icon>-->
<!--      </el-icon>-->
<!--      <span>权限管理</span>-->
<!--    </el-menu-item>-->

  </el-menu>
</template>
<script setup>
import {Icon} from "@iconify/vue"
import {useRoute,useRouter} from "vue-router"
import {watch,ref,defineModel} from "vue"


const route = useRoute()
const router = useRouter()
const menuValue = ref("")
const model = defineModel();

watch(() => route, (newRoute, oldRoute) => {
  menuValue.value = newRoute.name
}, {immediate: true});



</script>
<style scoped>

</style>