<template>
  <DefaultLayout>
    <div class="row row-cards justify-content-center">
      <div class="col-md-12">
        <div class="row row-cards justify-content-center">
          <div class="col-lg-9">
            <div class="row row-cards justify-content-center">
              <div class="col-12">
                <TopicList></TopicList>
              </div>
            </div>
          </div>
          <div class="col-lg-3">
            <div class="row row-cards ">
              <div class="col-md-12 " style="top: 105px">
                <div class="row row-cards">


                  <div class="col-md-12">
                    <div class="card card-link text-primary-fg"
                       :style="{backgroundColor:tagInfo.color}">
                      <div class="card-body">
                        <h3 class="card-title">{{tagInfo.name}}</h3>
                        <p v-if="tagInfo.description != undefined">{{tagInfo.description}}</p>
                        <p v-else>暂无描述</p>
                      </div>
                    </div>
                  </div>
                  <div class="col-md-12">
                    <div class="card">

                      <div class="card-status-top" :style="{backgroundColor:tagInfo.color}"></div>

                      <div class="card-body">
                        <b class="text-h3">板块创建者</b>:
                        <span class="text-red">{{tagInfo.createUser.nickname}}</span>
                        <div class="mt-3">
                          <b class="text-h3">版主：</b>
                          <div data-bs-toggle="modal" data-bs-target="#modal-moderator-list"
                               class="avatar-list avatar-list-stacked mt-2">
                            <span class="avatar avatar-sm rounded-circle" v-for="item in tagInfo.managerList"
                                  :style="{backgroundImage: `url('${item.avatar}')`}"></span>

                          </div>
                        </div>
                      </div>

                      <div class="card-footer">
                        <router-link :to="{name:'UserCreateTopic'}"  class="btn btn-dark">发帖</router-link>
                      </div>

                    </div>
                  </div>


                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </DefaultLayout>
</template>
<script setup>
import TopicList from "@/components/tag/TopicList.vue"
import DefaultLayout from "@/layout/DefaultLayout.vue";
import {ref} from "vue"
import {info} from "@/api/tag.js"
import {useRoute,useRouter} from "vue-router"

const tagInfo = ref({})
const route = useRoute()
const router = useRouter()
const doGetTagInfo = async ()=>{
  const response = await info(route.params.id)
  if(response.code === 200){
    tagInfo.value = response.data
  }else{
    router.push("/")
  }
}

await doGetTagInfo()
</script>

<style scoped>

</style>