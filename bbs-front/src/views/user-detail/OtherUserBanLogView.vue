<script setup>
import {queryUserForbiddenLogByUserId} from "@/api/forbiddenLog.js"
import {ref, watch} from "vue";
import moment from "moment"
import {useRoute} from "vue-router"


const route = useRoute();
const logData = ref([])
const pageTotal = ref(0)
const pageSize = 10
const pageNum = ref(1)
const getUserForbiddenLogData = async () => {
  const response = await queryUserForbiddenLogByUserId(pageNum.value, pageSize,route.params.id)
  if(response.code === 200){
    pageTotal.value = response.total
    pageNum.value = response.current
    logData.value = response.data
  }

}

watch(pageNum, () => {
  getUserForbiddenLogData()
})

await getUserForbiddenLogData()
</script>

<template>
  <div class="card">
    <div class="card-body">
      <h3 class="card-title">
        小黑屋记录
      </h3>
      <div class="table-responsive">
        <table class="table card-table table-vcenter text-nowrap">
          <thead>
          <tr>
            <th>ID</th>
            <th>封禁时间</th>
            <th>截止时间</th>
            <th>操作人</th>
            <th>是否到期</th>
          </tr>
          </thead>
          <tbody v-if="pageTotal == 0">
          <tr>
            <td colspan="6" class="text-center">暂无封禁记录</td>
          </tr>
          </tbody>
          <tbody v-else>
          <tr v-for="(item,index) in logData" :key="item.id">
            <td>{{index+1}}</td>
            <td>{{moment(item.startTime).format("YYYY-MM-DD HH:mm:ss")}}</td>
            <td>{{moment(item.finishTime).format("YYYY-MM-DD HH:mm:ss")}}</td>
            <td>{{item.createUsername}}</td>
            <td>
              <span class="badge bg-red text-red-fg" v-if="moment(item.finishTime).isAfter(new Date())">封禁中</span>
              <span class="badge bg-green text-green-fg" v-else>已解封</span>
            </td>
          </tr>
          </tbody>
        </table>

        <el-pagination v-if="pageTotal> pageSize" layout="prev, pager, next" :total="pageTotal"
                       v-model:current-page="pageNum" :page-size="pageSize"
                       style="margin-top: 20px;justify-content: center"/>

      </div>
    </div>
  </div>
</template>

<style scoped>

</style>