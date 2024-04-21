<template>
  <DefaultLayout>

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
                发帖
              </h2>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="page-body">
      <div class="container-xl">

        <div class="row row-cards">
          <div class="col-lg-9">
            <div class="row row-cards">
              <div class="col-lg-12" id="topic-create">
                <form method="POST">
                  <div class="row row-cards">
                    <div class="col-lg-12">

                      <div class="card">
                        <div class="card-header">
                          <h3 class="card-title">发布帖子</h3>
                        </div>
                        <div class="card-body">
                          <div class="mb-3">
                            <label class="form-label">标题</label>
                            <el-input placeholder="请输入标题" v-model="formData.title"></el-input>
                          </div>
                          <div class="mb-3">
                            <label class="form-label">选择板块</label>
                            <el-select v-model="formData.tag" placeholder="请选择板块">
                              <el-option :label="item.name" :value="item.id" :key="item.id" v-for="item in tagList">
                                <el-tag effect="dark" :hit="false" :color="item.color">{{ item.name }}</el-tag>
                              </el-option>
                            </el-select>
                          </div>
                          <div class="mb-3">
                            <label class="form-label">内容正文</label>
                            <div class="border-1 border-dark border">
                              <WebEditor v-model:summary="formData.summary"
                                         v-model:content="formData.content"></WebEditor>
                            </div>
                          </div>

                          <div class="mb-3">
                            <label class="form-label">图片上传</label>
                            <ImageUploader v-model:file-upload-list="formData.imageList"
                                           file-type="image/*"></ImageUploader>
                          </div>
                        </div>

                      </div>

                    </div>
                  </div>


                </form>
              </div>
              <div class="col-lg-12">
                <button @click="publishTopic" class="btn btn-primary" type="submit">提交</button>
              </div>
            </div>

          </div>


          <!--          附加信息-->
          <div class="col-lg-3">
            <div class="row">
              <div class="col-12">
                <div class="card">
                  <div class="card-header">
                    <h3 class="card-title">附加信息</h3>
                  </div>
                  <div class="card-body">
                    <div class="row row-cards">
                      <div class="col-12">
                        <div class="card">
                          <div class="card-body">
                            <label class="form-check">
                              <input type="checkbox" v-model="formData.lock" class="form-check-input">
                              <span class="form-check-label">关闭评论</span>
                            </label>
                          </div>
                        </div>
                      </div>

                      <div class="col-12">
                        <div class="card">
                          <div class="card-body">
                            <label class="form-check">
                              <input type="checkbox" v-model="formData.openScoreRequire" class="form-check-input">
                              <span class="form-check-label">开启积分访问限制</span>
                            </label>
                            <el-input-number v-if="formData.openScoreRequire" v-model="formData.scoreRequire" :min="0"/>

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
      </div>
    </div>


  </DefaultLayout>
</template>
<script setup>
import DefaultLayout from "@/layout/DefaultLayout.vue";
import {reactive, ref} from "vue"
import {getTagList} from "@/api/tag.js"
import WebEditor from "@/components/WebEditor.vue";
import ImageUploader from "@/components/ImageUploader.vue";
import {publishTopic as doPublishTopic} from "@/api/topic.js"
import lodash from "lodash";
import {ElMessage} from "element-plus"
import {useRoute, useRouter} from "vue-router"


const router = useRouter();
const tagList = ref([])
const formData = reactive({
  title: "",
  tag: "",
  content: "",
  summary: "",
  imageList: [],
  lock: false,
  scoreRequire: 0,
  openScoreRequire: false
})

const getTagData = async () => {
  const response = await getTagList()
  tagList.value = response.data
}

getTagData()


const publishTopic = async () => {
  if (lodash.isEmpty(formData.title)) {
    ElMessage({
      message: "请输入标题",
      type: "error",
      duration: 1500
    })
    return false;
  }

  if (formData.title.length > 50) {
    ElMessage({
      message: "标题过长",
      type: "error",
      duration: 1500
    })
    return false;
  }

  if (!lodash.isNumber(formData.tag)) {
    ElMessage({
      message: "请选择板块",
      type: "error",
      duration: 1500
    })
    return false;
  }

  if (lodash.isEmpty(formData.summary)) {
    ElMessage({
      message: "请输入内容正文",
      type: "error",
      duration: 1500
    })
    return false;
  }

  const response = await doPublishTopic(
      formData.title,
      formData.tag,
      formData.content,
      formData.summary,
      lodash.isEmpty(formData.imageList) ? undefined : formData.imageList,
      formData.lock ? 1 : 0,
      formData.openScoreRequire ? formData.scoreRequire : 0
  )

  if (response.code === 200) {
    ElMessage({
      message: response.msg,
      type: "success",
      duration: 1500
    })

    //1.5秒后刷新页面
    setTimeout(() => {
      router.push({name:"Topic",params:{id:response.data}})
    }, 1500)
  }

}

</script>

<style scoped>

</style>