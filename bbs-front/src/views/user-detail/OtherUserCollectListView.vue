<template>
  <div class="row row-cards">

    <div class="col-md-12">
      <div class="border-0 card card-body">
        <h3 class="card-title">{{ userInfo.username }} 的收藏</h3>
        <div class="row row-cards">
          <div class="empty" v-if="pageTotal === 0">
            <div class="empty-header">404</div>
            <p class="empty-title">暂无更多结果</p>
          </div>

          <div v-else>
            <div class="col-md-12 mt-2" v-for="item in collectData" :key="item.id">
              <div class="card">
                <div class="card-header">
                  <h3 class="card-title">
                    <div v-if="item.topic === null">原贴已被删除</div>
                    <router-link v-else :to="{name:'Topic',params:{id:item.topic.id}}">{{item.topic.title}}</router-link>
                  </h3>
                </div>
                <div class="card-body" v-if="item.topic !== null">
                  <div class="row">
                    <div class="col-md-12">
                      <div class="card-text topic-content">
                        {{item.topic.summary}}
                      </div>
                    </div>
                  </div>
                </div>
                <div class="card-footer" v-if="item.topic !== null">
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
import {getInfoByUserId, queryUserCollectByUserId} from "@/api/user.js"
import {useRoute,useRouter} from "vue-router"

const collectData = ref([])
const pageTotal = ref(0)
const pageSize = 10
const pageNum = ref(1)
const route = useRoute();
const router = useRouter();
const queryUserCollectList = async () => {
  const response = await queryUserCollectByUserId(pageNum.value, pageSize,route.params.id);
  if (response.code === 200) {
    pageTotal.value = response.total
    pageNum.value = response.current
    collectData.value = response.data
  }
}

await queryUserCollectList()
watch(pageNum, () => {
  queryUserCollectList()
})


const userInfo = ref({})
const doGetUserInfo = async () => {
  const response = await getInfoByUserId(route.params.id)
  if(response.code === 200){
    userInfo.value = response.data

  }else{
    router.push("/")
  }
}
await doGetUserInfo()
</script>

<style scoped>
.topic-content{
  display: inline-block;
  white-space: nowrap;
  width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>