<script setup>

import {Icon} from "@iconify/vue";
</script>

<template>
  <div class="page page-center">
    <div class="container-tight py-4">
      <div class="empty">
        <div class="empty-header">网站已关闭注册</div>
        <p class="empty-title">
          网站已关闭注册，暂不可见
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
</template>

<style scoped>

</style>