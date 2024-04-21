<template>
  <DefaultLayout>
    <div class="row row-cards justify-content-center">
      <div class="col-md-12">
        <div class="row row-cards justify-content-center">
          <div class="col-lg-9">
            <div class="row row-cards justify-content-center" id="topic-page">
              <TopicDetail v-model="topic" :user="user" :tag="tag"></TopicDetail>

              <CommentList :topic-id="topic.id" :topic="topic"></CommentList>

              <div class="col-md-12">
                <div class="border-0 card">
                  <div class="card-header">
                    <div class="card-title">评论</div>
                  </div>
                  <div class="card-body">
                    <div class="mb-1">
                      <PublishComment v-if="topic.isLock === 0" :topic-id="topic.id"></PublishComment>
                      <div v-else>帖子被锁定，无法评论</div>
                    </div>
                  </div>
                </div>
              </div>


            </div>
          </div>
          <div class="col-lg-3">
            <div class="row row-cards ">
              <div class="col-md-12 " style="top: 105px">
                <div class="row row-cards">


                  <div class="col-12 d-none d-lg-inline-flex">
                    <div class="row row-cards">

                      <PublisherDetail :user="user"></PublisherDetail>
                      <TagDetail :tag="tag"></TagDetail>
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
import DefaultLayout from "@/layout/DefaultLayout.vue";
import TopicDetail from "@/components/topic-view/TopicDetail.vue"
import {view} from "@/api/topic.js"
import {useRoute,useRouter} from "vue-router"
import {ref} from "vue"
import TagDetail from "@/components/topic-view/TagDetail.vue";
import PublisherDetail from "@/components/topic-view/PublisherDetail.vue";
import PublishComment from "@/components/topic-view/PublishComment.vue";
import CommentList from "@/components/topic-view/CommentList.vue"

const route = useRoute();
const router = useRouter()
const topic = ref({})
const tag = ref({})
const user = ref({})
const doView = async ()=>{
  const response = await view(route.params.id)
  if(response.code !== 200){
    router.push({name:"Home"})
    return false;
  }
  topic.value = response.data
  tag.value = response.data.tag
  user.value = response.data.user

  if(topic.value.imageList != undefined){
    topic.value.imageList = JSON.parse(topic.value.imageList)
  }
}

await doView()
</script>
<style scoped>

</style>