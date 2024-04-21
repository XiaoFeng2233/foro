<template>
  <div class="page page-center">
    <div class="container-tight py-4">
      <div class="empty" id="empty">
        <div class="empty-header">404</div>
        <p class="empty-title">页面不存在</p>
        <p class="empty-subtitle text-muted">
          Sorry, you are temporarily unable to access this page
        </p>
        <div class="empty-action">
          <router-link to="/" class="btn btn-primary">
            <Icon icon="mingcute:arrow-left-fill" width="20" height="20" class="me-1"></Icon>
            返回
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {Icon} from "@iconify/vue";

document.title = "404 - 页面未找到"

</script>

<style scoped>

</style>