<template>
  <footer class="footer footer-transparent d-print-none">
    <div class="container">
      <div v-html="footer">
      </div>
    </div>
  </footer>
</template>
<script setup>
import siteConfigStore from "@/stores/siteconfig.js"


const footer = siteConfigStore().footer
</script>


<style scoped>

</style>