<template>
  <div class="col-md-12" id="topic">
    <div class="card">
      <div class="card-header">
        <h2 class="card-title text-reset" style="font-size: 1.2rem;line-height: 1.5;">
          {{ model.title }}
        </h2>
      </div>
      <div class="mx-3 my-3 mb-0">
        <div class="row">
          <div class="col">
            <div class="row">
              <div class="col-auto">
                <router-link class="avatar avatar-rounded" :to="{name:'OtherUserCenterPanel',params:{id:prop.user.id}}"
                             :style="{backgroundImage: `url(${prop.user.avatar})`}">
                </router-link>
              </div>
              <div class="col">
                <div class="topic-author-name">
                  <router-link :to="{name:'OtherUserCenterPanel',params:{id:prop.user.id}}" class="text-reset">
                    {{ prop.user.nickname }}
                  </router-link>
                </div>
                <div>
                  <span class="cursor-pointer"> 发表:{{ moment(model.createTime).fromNow() }} </span></div>
              </div>
              <div class="col-auto"></div>
            </div>
          </div>
          <div class="col-auto"></div>
        </div>
      </div>
      <div class="mt-0 mb-0">
        <div class="hr-text hr-text-right mt-3 mb-0" style="margin-right: 1rem;">
          <router-link class="text-muted" :to="{name:'Tag',params:{id:model.id}}">
            <el-tooltip
                class="box-item"
                effect="dark"
                content="所属板块"
                placement="top"
            >
              <div class="flex justify-content-center align-items-center">
                <Icon icon="ph:hash" width="16"></Icon>
                {{ model.name }}
              </div>
            </el-tooltip>
          </router-link>
          <span class="mx-1">|</span>
          <div class="text-muted">
            <el-tooltip
                class="box-item"
                effect="dark"
                content="访问数量"
                placement="top"
            >
              <div class="flex justify-content-center align-items-center">
                <Icon icon="mdi:eye-outline" width="16"></Icon>
                {{ model.viewCount }}
              </div>
            </el-tooltip>
          </div>
          <span class="mx-1">|</span>
          <div class="text-muted">
            <el-tooltip
                class="box-item"
                effect="dark"
                content="评论数量"
                placement="top"
            >
              <div class="flex justify-content-center align-items-center">
                <Icon icon="fa-regular:comment-dots" width="16"></Icon>
                {{ model.commentCount }}
              </div>
            </el-tooltip>
          </div>
        </div>
      </div>
      <article class="card-body topic article markdown text-reset">
        <div v-html="model.content"></div>
      </article>

      <div class="hr-text hr-text-left" style="margin-left: 1.2rem" v-if="model.imageList != undefined">
        附加图片
      </div>
      <article class="card-body" v-if="model.imageList != undefined">

        <el-image
            v-for="(item,index) in model.imageList"
            :key="index"
            class="rounded border-2 border-dark mx-1"
            style="width: 50px; height: 50px"
            :src="item"
            :zoom-rate="1.2"
            :max-scale="7"
            :min-scale="0.2"
            :initial-index="4"
            :preview-src-list="model.imageList"
            fit="cover"
        />


      </article>


      <div class="card-footer">
        <div class="d-flex justify-content-between">
          <div>
            <el-button @click="doUnLike(model)" text style="color: #ff6b81" v-if="model.liked">
              <Icon icon="mdi:heart" width="20"></Icon>
              <span>{{ model.likeCount }}</span>
            </el-button>
            <el-button @click="doLike(model)" text v-else>
              <Icon icon="mdi:heart-outline" width="20"></Icon>
              <span>{{ model.likeCount }}</span>
            </el-button>

            <el-button @click="doUnCollect(model)" text style="color:orange" v-if="model.collected">
              <Icon icon="ph:star-fill" width="20"></Icon>
              <span>已收藏</span>
            </el-button>
            <el-button @click="doCollect(model)" text v-else>
              <Icon icon="ph:star-bold" width="20"></Icon>
              <span>收藏</span>
            </el-button>

          </div>


          <div v-if="hasRole('admin') || isManager(model.tagId)">
            <el-dropdown>
              <el-button text style="border:none;outline: none">
                <Icon icon="ri:more-2-fill" width="24" height="24"></Icon>
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item>
                    <el-popconfirm
                        width="220"
                        confirm-button-text="确定"
                        cancel-button-text="取消"
                        title="确定要删除本篇帖子吗？"
                        @confirm="doDeleteTopic"
                    >
                      <template #reference>
                        <el-link :underline="false">
                          <el-icon>
                            <Icon icon="material-symbols:delete-outline" width="24" height="24"></Icon>
                          </el-icon>
                          删除
                        </el-link>

                      </template>
                    </el-popconfirm>

                  </el-dropdown-item>

                  <el-dropdown-item v-if="model.sticky === 0" @click="doStick">
                    <el-link :underline="false">
                      <el-icon>
                        <Icon icon="solar:pin-linear" width="24" height="24"></Icon>
                      </el-icon>
                      置顶
                    </el-link>
                  </el-dropdown-item>

                  <el-dropdown-item v-else @click="doUnStick">
                    <el-link :underline="false">
                      <el-icon>
                        <Icon icon="solar:pin-linear" width="24" height="24"></Icon>
                      </el-icon>
                      取消置顶
                    </el-link>
                  </el-dropdown-item>

                  <el-dropdown-item v-if="model.recommend === 0 " @click="doRecommend">
                    <el-link :underline="false">
                      <el-icon>
                        <Icon icon="solar:like-linear" width="24" height="24"></Icon>
                      </el-icon>
                      推荐
                    </el-link>
                  </el-dropdown-item>

                  <el-dropdown-item v-else @click="doUnRecommend">
                    <el-link :underline="false">
                      <el-icon>
                        <Icon icon="solar:like-linear" width="24" height="24"></Icon>
                      </el-icon>
                      取消推荐
                    </el-link>
                  </el-dropdown-item>


                  <el-dropdown-item v-if="model.isLock === 0 " @click="doLock">
                    <el-link :underline="false">
                      <el-icon>
                        <Icon icon="material-symbols:lock-outline" width="24" height="24"></Icon>
                      </el-icon>
                      锁定
                    </el-link>
                  </el-dropdown-item>

                  <el-dropdown-item v-else @click="doUnLock">
                    <el-link :underline="false">
                      <el-icon>
                        <Icon icon="material-symbols:lock-outline" width="24" height="24"></Icon>
                      </el-icon>
                      取消锁定
                    </el-link>
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </div>
      </div>


    </div>
  </div>
</template>
<script setup>
import {ElMessage} from "element-plus"
import {defineProps, defineModel} from "vue"
import moment from "moment"
import {Icon} from "@iconify/vue"
import {
  like,
  collect,
  unLike,
  unCollect,
  deleteTopic,
  lock,
  unLock,
  recommend,
  unRecommend,
  stick,
  unStick
} from "@/api/topic.js";
import {hasRole, isManager} from "@/utils/authUtil.js"
import {useRouter} from "vue-router"

const prop = defineProps(["topic", "user", "tag"])
const router = useRouter()

const model = defineModel()


const doLike = async (model) => {
  const response = await like(model.id)
  if (response.code === 200) {
    ElMessage.success({
      message: response.data,
      duration: 1500
    })
    model.liked = true
    model.likeCount++

  }
}

const doCollect = async (model) => {
  const response = await collect(model.id)
  if (response.code === 200) {
    ElMessage.success({
      message: response.data,
      duration: 1500
    })
    model.collected = true
  }
}

const doUnCollect = async (model) => {
  const response = await unCollect(model.id)
  if (response.code === 200) {
    ElMessage.success({
      message: response.data,
      duration: 1500
    })
    model.collected = false
  }
}
const doUnLike = async (model) => {
  const response = await unLike(model.id)
  if (response.code === 200) {
    ElMessage.success({
      message: response.data,
      duration: 1500
    })

    model.liked = false
    model.likeCount--
  }
}

const doDeleteTopic = async () => {
  const response = await deleteTopic(model.value.id)
  if (response.code === 200) {
    ElMessage.success({
      message: response.data,
      duration: 1500
    })

    //1.5秒后刷新网页
    setTimeout(() => {
      router.push("/")
    }, 1500)
  }
}


const doLock = async () => {
  const response = await lock(model.value.id)

  if(response.code === 200){
    ElMessage.success({
      message: response.data,
      duration: 1500
    })

    //1.5秒后刷新页面
    setTimeout(() => {
      window.location.reload()
    },1500)
  }
}

const doUnLock = async () => {
  const response = await unLock(model.value.id)

  if(response.code === 200){
    ElMessage.success({
      message: response.data,
      duration: 1500
    })

    //1.5秒后刷新页面
    setTimeout(() => {
      window.location.reload()
    },1500)
  }
}

const doStick = async () => {
  const response = await stick(model.value.id)

  if(response.code === 200){
    ElMessage.success({
      message: response.data,
      duration: 1500
    })

    //1.5秒后刷新页面
    setTimeout(() => {
      window.location.reload()
    },1500)
  }
}

const doUnStick = async () => {
  const response = await unStick(model.value.id)

  if(response.code === 200){
    ElMessage.success({
      message: response.data,
      duration: 1500
    })

    //1.5秒后刷新页面
    setTimeout(() => {
      window.location.reload()
    },1500)
  }
}

const doRecommend = async () => {
  const response = await recommend(model.value.id)

  if(response.code === 200){
    ElMessage.success({
      message: response.data,
      duration: 1500
    })

    //1.5秒后刷新页面
    setTimeout(() => {
      window.location.reload()
    },1500)
  }
}

const doUnRecommend = async () => {
  const response = await unRecommend(model.value.id)

  if(response.code === 200){
    ElMessage.success({
      message: response.data,
      duration: 1500
    })

    //1.5秒后刷新页面
    setTimeout(() => {
      window.location.reload()
    },1500)
  }
}

</script>
<style scoped>
.el-dropdown-link:focus-visible {
  outline: unset;
}

.el-dropdown-link:focus {
  outline: unset;
}

</style>