<template>
  <div class="row row-cards">

    <div class="col-md-12">
      <div class="border-0 card card-body">
        <h3 class="card-title">{{ store.username }} 的收藏</h3>
        <div class="row row-cards">
          <div class="col-md-12" v-for="item in collectData" :key="item.id">
            <div class="card" v-if="item.topic != null">
              <div class="card-header">
                <h3 class="card-title">
                  <router-link :to="{name:'Topic',params:{id:item.topic.id}}">{{item.topic.title}}</router-link>
                </h3>
              </div>
              <div class="card-body">
                <div class="row">
                  <div class="col-md-12">
                    <div class="card-text">
                      {{item.topic.summary}}
                    </div>
                  </div>
                </div>
              </div>
              <div class="card-footer">
                <div class="row">
                  <div class="col">
                    <router-link :to="{name:'OtherUserCenterPanel',params:{id:item.topic.user.id}}">
                      <span class="avatar" :style="{backgroundImage: `url(${item.topic.user.avatar})`}">
                      </span>
                    </router-link>
                  </div>
                </div>
              </div>
            </div>
            <div class="card" v-else>
              <div class="card-body">
                原帖子已被删除
              </div>

            </div>


          </div>
        </div>
        <div class="mt-2">
          <el-pagination v-if="pageTotal> pageSize" layout="prev, pager, next" :total="pageTotal"
                         v-model:current-page="pageNum" :page-size="pageSize"
                         style="margin-top: 20px;justify-content: center"/>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import credentialStore from "@/stores/credential.js"
import {ref, watch} from "vue";
import {queryUserCollect} from "@/api/user.js"


const store = credentialStore()
const collectData = ref([])
const pageTotal = ref(0)
const pageSize = 10
const pageNum = ref(1)

const queryUserCollectList = async () => {
  const response = await queryUserCollect(pageNum.value, pageSize);
  if (response.code === 200) {
    pageTotal.value = response.total
    pageNum.value = response.current
    collectData.value = response.data
  }
}

queryUserCollectList()
watch(pageNum, () => {
  queryUserCollectList()
})
</script>
<style scoped>

</style>