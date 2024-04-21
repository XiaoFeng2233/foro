<template>
  <div class="col-12 col-md-6 col-lg-3">
    <router-link :to="prop.url" class="card card-sm">
      <div class="card-body">
        <div class="row align-items-center">
          <div class="col-auto">
            <span :class="[prop.bg, 'text-white', 'avatar']">
              <Icon :icon="prop.icon" width="26" height="26"></Icon>
            </span>
          </div>
          <div class="col">
            <div class="font-weight-medium">
              {{prop.value}}
            </div>
            <div class="text-muted">
              {{prop.title}}
            </div>
          </div>
          <div class="col-auto">
          </div>
        </div>
      </div>
    </router-link>
  </div>
</template>
<script setup>
import {defineProps} from "vue"
import {Icon} from "@iconify/vue";

const prop = defineProps({
  url:{
    type:String,
    default:""
  },
  icon:{
    type:String,
    default:"",
  },
  title:{
    type:String,
    default:"",
  },
  value:{
    default:"",
  },
  bg:{
    type:String,
    default:"",
  }
})
</script>
<style scoped>

</style>