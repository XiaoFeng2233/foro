<script setup>
import {defineProps} from "vue"


const prop = defineProps(["imageList"])
const image = JSON.parse(prop.imageList)
</script>

<template>
  <ul>
    <li v-for="item in image">
      <el-image :src="item" :preview-src-list="image" style="width: 100px; height: 100px"></el-image>
    </li>
  </ul>
</template>

<style scoped>

</style>