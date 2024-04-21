<template>
  <div class="d-flex flex-column align-items-center justify-content-center min-vh-100">
    <div class="page page-center">
      <div class="container container-normal py-4">
        <div class="row align-items-center g-4">
          <div class="col-lg">
            <div class="container-tight">
              <div class="text-center mb-4">
                <router-link to="/">
                  <img class="d-block mx-auto" alt="LOGO"
                       :src="config.logo"
                       height="80"/>
                </router-link>
              </div>
              <div>
                <slot/>
              </div>
            </div>
          </div>
          <div class="col-lg d-none d-lg-block">
            <img src="/image/user-login.svg"
                 height="500" class="d-block mx-auto" alt="">
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import "@/assets/tabler.min.css"
import "@/assets/tabler-vendors.min.css"
import "@/assets/core.css"
import "@/assets/app-v2.7.21.css"
import siteConfigStore from "@/stores/siteconfig.js";
const config = siteConfigStore()

</script>

<style scoped>

</style>
