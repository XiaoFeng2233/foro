<script setup>
import siteConfigStore from "@/stores/siteconfig.js"
import {useRoute} from "vue-router"
import {Icon} from "@iconify/vue";

const route = useRoute()
const store = siteConfigStore()

</script>

<template>
  <el-scrollbar v-if="store.open || route.path.startsWith('/admin')">
    <suspense>
      <router-view>
      </router-view>
    </suspense>
  </el-scrollbar>

  <div v-if="!store.open && !route.path.startsWith('/admin')">
    <div class="page page-center">
      <div class="container-tight py-4">
        <div class="empty">
          <div class="empty-header">网站已关闭</div>
          <p class="empty-title">
            网站已关闭，暂不可见
          </p>
          <div class="empty-action">
            <router-link to="/" class="btn btn-primary">
              <Icon icon="mingcute:arrow-left-fill" width="20" height="20" class="me-1" :inline="true">

              </Icon>
              返回
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<style scoped></style>
