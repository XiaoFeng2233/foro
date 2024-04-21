<template>
  <div class="page page-center">
    <div class="container-tight py-4">
      <div class="empty" id="empty">
        <div class="empty-header">403</div>
        <p class="empty-title"><Icon icon="material-symbols:lock" width="24" height="24">&nbsp;123</Icon>无权访问</p>
        <p class="empty-subtitle text-muted">Sorry, You do not have permission to access this page</p>
        <div class="empty-action">
          <router-link to="/" class="btn btn-primary">
            <Icon icon="mingcute:arrow-left-fill" width="20" height="20" class="me-1" :inline="true"></Icon>
            返回
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import {Icon} from "@iconify/vue"
</script>
<style scoped>

</style>