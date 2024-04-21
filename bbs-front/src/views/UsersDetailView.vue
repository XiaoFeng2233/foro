<script setup>
import DefaultLayout from "@/layout/DefaultLayout.vue";
import {Icon} from "@iconify/vue";
import MenuList from "@/components/user-detail/MenuList.vue";
import {getInfoByUserId} from "@/api/user.js"
import {followUser, unFollowUser} from "@/api/follow.js"
import {ref} from "vue"
import {useRouter, useRoute} from "vue-router"
import credentialStore from "@/stores/credential.js"
import {ElMessage} from "element-plus";

const route = useRoute();
const router = useRouter();
const userInfo = ref({})
const store = credentialStore()

const doGetUserInfo = async () => {
  const response = await getInfoByUserId(route.params.id)
  if (response.code === 200) {
    userInfo.value = response.data
    if (userInfo.value.id === store.id) {
      router.push({name: "UserCenterPanel"})
    }
  } else {
    router.push("/")
  }
}

doGetUserInfo()

const doFollow = async ()=>{
  const response = await followUser(route.params.id)
  if(response.code === 200){
    ElMessage({
      message:response.data,
      type:"success",
      duration:1500
    })
    await doGetUserInfo()
  }
}

const doUnFollow = async ()=>{
  const response = await unFollowUser(route.params.id)
  if(response.code === 200){
    ElMessage({
      message:response.data,
      type:"success",
      duration:1500
    })
    await doGetUserInfo()
  }
}
</script>

<template>
  <DefaultLayout>
    <div class="row row-cards">

      <div class="col-md-12">
        <div class="border-0 card">
          <el-image
              fit="none"
              loading="eager"
              :crossorigin="null"
              :src="userInfo.backgroundImage" :preview-src-list="[userInfo.backgroundImage]" style="height: 200px">
            <el-image-viewer :crossorigin="null"></el-image-viewer>
          </el-image>
          <div class="card-body ms-1">
            <div class="row">
              <div class="col">
                <div class="row">
                  <div class="col-auto text-center">
                  <span class="avatar avatar-xl avatar-thumb text-light border-2 border-wide rounded"
                        :style="{marginTop:'-220px',backgroundImage:`url(${userInfo.avatar})`}"></span>
                    <div>
                                        <span class="card-title mb-1" style="font-size: 25px;display: inline">
                                          <span class="text-reset">{{ userInfo.username }}</span>
                                        </span>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-auto align-self-center text-center">
                <div class="dropdown">
                  <el-dropdown>
                    <button type="button" class="btn dropdown-toggle" data-bs-toggle="dropdown">
                      <Icon icon="clarity:add-line" width="22" height="22"></Icon>
                    </button>
                    <template #dropdown>
                      <el-dropdown-menu>
                        <el-dropdown-item @click="doFollow" v-if="!userInfo.followed">
                          <div>关注</div>
                        </el-dropdown-item>
                        <el-dropdown-item v-else @click="doUnFollow">
                          <div>取消关注</div>
                        </el-dropdown-item>
                      </el-dropdown-menu>
                    </template>
                  </el-dropdown>
                </div>
              </div>
            </div>
            <div class="text-muted" v-if="userInfo.description == undefined">
              这个人很懒，什么都没留下！
            </div>
            <div class="text-muted" v-else>
              {{ userInfo.description }}
            </div>
          </div>
        </div>
      </div>

      <div class="col-lg-3">
        <div class="border-0 card">
          <div class="card-body">
            <MenuList></MenuList>
          </div>
        </div>
      </div>

      <div class="col-lg-9">
        <suspense>
          <router-view></router-view>
        </suspense>
      </div>

    </div>
  </DefaultLayout>
</template>

<style scoped>

</style>