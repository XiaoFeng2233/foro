<template>
  <div class="full-page">
    <LayoutHeader/>
    <div id="main-page">
      <div class="page-body">
        <div class="container-xl">
          <slot />
        </div>
      </div>
    </div>

    <LayoutFooter />
  </div>
</template>
<script setup>
import "@/assets/app-v2.7.21.css"
import "@/assets/core.css"
import "@/assets/tabler-vendors.min.css"
import "@/assets/tabler.min.css"
import LayoutFooter from  "@/components/default-layout/LayoutFooter.vue"
import LayoutHeader from  "@/components/default-layout/LayoutHeader.vue"
</script>
<style scoped>
.full-page{
  display: flex;
  flex-direction: column;
  height: 100vh;
}
</style>