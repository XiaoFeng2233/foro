<template>
  <div class="card">
    <div class="card-body">
      <h3 class="card-title">登录记录</h3>
      <div class="table-responsive">
        <table class="table card-table table-vcenter text-nowrap">
          <thead>
          <tr>
            <th>ID</th>
            <th>登录地点</th>
            <th>登录IP</th>
            <th>登录时间</th>
          </tr>
          </thead>
          <tbody v-if="pageTotal == 0">
          <tr>
            <td colspan="6" class="text-center">暂无登录记录</td>
          </tr>
          </tbody>
          <tbody v-else>
          <tr v-for="(item,index) in logData">
            <td>{{index+1}}</td>
            <td>{{item.location}}</td>
            <td>{{item.ip}}</td>
            <td>{{moment(item.createTime).format("YYYY-MM-DD hh:mm:ss")}}</td>
          </tr>
          </tbody>
        </table>

        <el-pagination v-model:current-page="pageNum" layout="prev, pager, next" v-if="pageTotal>pageSize" :page-size="pageSize" :total="pageTotal" style="margin-top: 20px;justify-content: center"/>

      </div>
    </div>
  </div>
</template>
<script setup>
import {loginLog} from "@/api/user.js"
import {ref, watch} from "vue"
import moment from "moment"

const logData = ref([])
const pageTotal = ref(0)
const pageSize = 10
const pageNum = ref(1)

const getLoginLog = async () => {
  const response = await loginLog(pageNum.value, pageSize)
  pageTotal.value = response.total
  pageNum.value = response.current
  logData.value = response.data
}

watch(pageNum, () => {
  getLoginLog()
})

getLoginLog()
</script>

<style scoped>

</style>