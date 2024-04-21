<template>
  <div class="col-md-12">
    <router-link :to="{name:'Tag',params:{id:prop.tag.id}}" class="card card-link text-primary-fg"
       :style="{backgroundColor: prop.tag.color}">
      <div class="card-body">
        <h3 class="card-title">{{prop.tag.name}}</h3>
        <p v-if="prop.tag.description == undefined">暂无描述</p>
        <p v-else>{{prop.tag.description}}</p>
      </div>
    </router-link>
  </div>
</template>
<script setup>
import {defineProps} from "vue"

const prop = defineProps(["tag"])


</script>
<style scoped>

</style>