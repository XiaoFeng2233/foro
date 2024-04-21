<template>
  <AdminLayout>
    <AdminInfoStatistic></AdminInfoStatistic>
    <AdminUserChart></AdminUserChart>
    <AdminTopicChart></AdminTopicChart>
  </AdminLayout>
</template>

<script setup>

import AdminLayout from "@/layout/AdminLayout.vue";
import AdminInfoStatistic from "@/components/admin/index/AdminInfoStatistic.vue";
import AdminUserChart from "@/components/admin/index/UserChart.vue";
import AdminTopicChart from "@/components/admin/index/TopicChart.vue";
</script>

<style scoped>

</style>