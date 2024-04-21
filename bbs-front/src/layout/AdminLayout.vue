<template>
  <div class="admin-layout">
    <el-container style="height: 100%">
      <el-aside width="auto" class="sidebar-container">
        <AdminLayoutSideBar v-model="collapse"/>
      </el-aside>
      <el-container>
        <el-header class="header-container">
          <AdminLayoutHeader v-model="collapse"/>
        </el-header>
        <el-main>
          <slot></slot>
        </el-main>
        <el-footer class="footer-container">
          <AdminLayoutFooter/>
        </el-footer>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>

import AdminLayoutSideBar from "@/components/admin-layout/AdminLayoutSideBar.vue";
import AdminLayoutHeader from "@/components/admin-layout/AdminLayoutHeader.vue";
import AdminLayoutFooter from "@/components/admin-layout/AdminLayoutFooter.vue";

import {ref} from "vue"

const collapse = ref(false)
</script>

<style scoped>
.admin-layout{
  width: 100vw;
  height: 100vh;
}

.sidebar-container{
  max-width: 200px;

  height: 100vh;

}

.header-container{
  padding: 0;
}

.footer-container{
  padding: 0;
}
</style>