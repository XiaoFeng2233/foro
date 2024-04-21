<script setup>
import {defineProps} from "vue"

const prop = defineProps(["content"])

</script>

<template>
<div v-html="prop.content"></div>
</template>

<style scoped>

</style>