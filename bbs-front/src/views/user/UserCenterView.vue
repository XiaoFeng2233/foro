<template>
  <DefaultLayout>
    <div class="row row-cards">

      <div class="col-md-12">
        <div class="border-0 card">
          <el-image
                    fit="none"
                    loading="eager"
                    :crossorigin="null"
             :src="user.backgroundImage" :preview-src-list="[user.backgroundImage]" style="height: 200px">
            <el-image-viewer :crossorigin="null"></el-image-viewer>
          </el-image>
          <div class="card-body ms-1">
            <div class="row">
              <div class="col">
                <div class="row">
                  <div class="col-auto text-center">
                  <span class="avatar avatar-xl avatar-thumb text-light border-2 border-wide rounded"
                        :style="{marginTop:'-220px',backgroundImage:`url(${user.avatar})`}"></span>
                    <div>
                                        <span class="card-title mb-1" style="font-size: 25px;display: inline">
                                          <span class="text-reset">{{ username }}</span>
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
                        <el-dropdown-item>
                          <router-link :to="{name:'UserSetting'}">
                            <span>个人设置</span>
                          </router-link>
                        </el-dropdown-item>
                      </el-dropdown-menu>
                    </template>
                  </el-dropdown>
                </div>
              </div>
            </div>
            <div class="text-muted" v-if="user.description == undefined">
              这个人很懒，什么都没留下！
            </div>
            <div class="text-muted" v-else>
              {{ user.description }}
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
        <router-view></router-view>
      </div>

    </div>
  </DefaultLayout>
</template>

<script setup>
import MenuList from "@/components/user/center/MenuList.vue"
import DefaultLayout from "@/layout/DefaultLayout.vue";
import credentialStore from "@/stores/credential.js"
import {info} from "@/api/user.js"
import {ref} from "vue"
import {Icon} from "@iconify/vue"



const username = credentialStore().username
const user = ref({})

const getUserInfo = async () => {
  const response = await info();
  if (response.code === 200) {
    user.value = response.data
  }
}

getUserInfo()
</script>

<style scoped>
a{
  text-decoration: none;
}
</style>