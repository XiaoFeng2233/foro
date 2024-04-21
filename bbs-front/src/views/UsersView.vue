<template>
  <DefaultLayout>
    <div id="users-page">
      <div class="page-wrapper">
        <div class="container-xl">
          <!-- Page title -->
          <div class="page-header d-print-none">
            <div class="row align-items-center">
              <div class="col">
                <!-- Page pre-title -->
                <div class="page-pretitle">
                  Overview
                </div>
                <h2 class="page-title">
                  用户列表
                </h2>
                共有 {{pageTotal}} 位会员
              </div>


            </div>
          </div>
        </div>
      </div>
      <div class="page-body">
        <div class="container-xl">

          <div class="row row-cards">
            <div class="col-md-6 col-lg-3" v-for="item in userData" :key="item.id">
              <div class="border-0 card">
                <div class="card-body p-4 text-center">
                  <router-link :to="{name:'OtherUserCenterPanel',params:{id:item.id}}" class="avatar avatar-xl mb-3 avatar-rounded"
                        :style="{backgroundImage: `url(${item.avatar})`}"></router-link>
                  <h3 class="m-0 mb-1">
                    <router-link :to="{name:'OtherUserCenterPanel',params:{id:item.id}}">{{item.nickname}}</router-link>
                  </h3>
                  <div class="text-muted">本站第 {{item.id}} 位会员</div>
                  <div class="text-muted">加入时间: {{moment(item.createTime).format('YYYY-MM-DD')}}</div>
                  <div class="mt-3">
                    <a href="/users/group/1.html">
                      <span class="badge" style="background:#206bc4">注册会员</span>
                    </a>
                  </div>
                </div>
              </div>
            </div>
            <el-pagination v-model:current-page="pageNum" v-if="pageTotal > 20" background layout="prev, pager, next" :total="pageTotal" :page-size="pageSize" class="mt-4 justify-content-center"/>

          </div>

        </div>
      </div>
    </div>
  </DefaultLayout>

</template>
<script setup>
import DefaultLayout from "@/layout/DefaultLayout.vue";
import {userList} from "@/api/user.js"
import {ref,watch} from "vue"
import moment from "moment"
import {useRouter} from "vue-router"

const pageSize = 20;
const pageTotal = ref(0)
const pageNum = ref(1)
const userData = ref([])
const router = useRouter()
const doUserList = async () => {
  const response = await userList(pageNum.value, pageSize)
  if(response.code !== 200){
    router.push({name:"Home"})
    return false;
  }
  pageTotal.value = response.total
  pageNum.value = response.current
  userData.value = response.data
}


watch(pageNum,()=>{
  doUserList()
})

await doUserList()

</script>
<style scoped>

</style>