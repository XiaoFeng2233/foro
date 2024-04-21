<template>
  <AdminLayout>
    <el-card header="用户列表">
      <el-table :data="userData">
        <el-table-column prop="id" label="id" width="70">
        </el-table-column>
        <el-table-column prop="username" label="用户名">
        </el-table-column>
        <el-table-column prop="avatar" label="头像">
          <template #default="scope">
            <el-avatar :src="scope.row.avatar"/>
          </template>
        </el-table-column>
        <el-table-column prop="email" label="邮箱">
        </el-table-column>
        <el-table-column prop="nickname" label="昵称">
        </el-table-column>
        <el-table-column prop="createTime" label="注册时间">
        </el-table-column>
        <el-table-column prop="emailVerified" label="邮箱激活">
          <template #default="scope">
            <el-tag type="success" v-if="scope.row.emailVerified === 1">已激活</el-tag>
            <el-tag type="warning" v-if="scope.row.emailVerified === 0">待激活</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态">
          <template #default="scope">
            <el-tag v-if="scope.row.status === 1" type="success">
              <el-icon>
                <Icon icon="dashicons:yes-alt" width="14" height="14"></Icon>
              </el-icon>
              正常
            </el-tag>
            <el-tag v-else-if="scope.row.status === 0" type="danger">
              <el-icon>
                <Icon icon="tabler:ban" width="14" height="14"></Icon>
              </el-icon>
              封禁
            </el-tag>
            <el-tag v-else-if="scope.row.status === 2" type="danger">
              <el-icon>
                <Icon icon="tabler:message-circle-off" width="14" height="14"></Icon>
              </el-icon>
              禁言
            </el-tag>
          </template>

        </el-table-column>
        <el-table-column prop="forbiddenEndTime" label="封禁结束时间">
        </el-table-column>
        <el-table-column prop="muteEndTime" label="禁言结束时间">
        </el-table-column>
        <el-table-column prop="operation" label="操作">
          <template #default="scope">
            <el-dropdown>
              <el-button size="small" type="primary">
                更多<el-icon style="color: white">
                <Icon icon="icon-park-outline:down" width="24" height="24"></Icon>
              </el-icon>
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="openEditDialog(scope.row.id)">
                    <el-icon>
                      <Icon icon="icon-park-outline:edit-two"></Icon>
                    </el-icon>
                    编辑
                  </el-dropdown-item>
                  <el-dropdown-item @click="doUnMuteUser(scope.row.id)" v-if="scope.row.status === 2">
                    <el-icon>
                      <Icon icon="tabler:message-circle-off" width="14" height="14"></Icon>
                    </el-icon>
                    解除禁言
                  </el-dropdown-item>

                  <el-dropdown-item @click="openMuteDialog(scope.row.id)" v-else>
                    <el-icon>
                      <Icon icon="tabler:message-circle-off" width="14" height="14"></Icon>
                    </el-icon>
                    禁言
                  </el-dropdown-item>
                  <el-dropdown-item @click="doUnForbiddenUser(scope.row.id)" v-if="scope.row.status === 0">
                    <el-icon>
                      <Icon icon="tabler:ban" width="14" height="14"></Icon>
                    </el-icon>
                    解除封禁
                  </el-dropdown-item>
                  <el-dropdown-item @click="openForbiddenDialog(scope.row.id)" v-else>
                    <el-icon>
                      <Icon icon="tabler:ban" width="14" height="14"></Icon>
                    </el-icon>
                    封禁
                  </el-dropdown-item>
                  <el-dropdown-item @click="doDeleteUser(scope.row.id)">
                    <el-icon>
                      <Icon icon="fluent:delete-24-regular"></Icon>
                    </el-icon>
                    删除
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>

        </el-table-column>
      </el-table>

      <el-pagination v-if="pageTotal> pageSize" layout="prev, pager, next" :total="pageTotal"
                     v-model:current-page="pageNum" :page-size="pageSize"
                     style="margin-top: 20px;justify-content: center"/>

    </el-card>


  </AdminLayout>

  <el-dialog
      v-model="muteDialogVisible"
      title="禁言用户"
      width="500"
      destroy-on-close
  >
    <MuteUser :id="muteDialogId"></MuteUser>
  </el-dialog>

  <el-dialog
      v-model="forbiddenDialogVisible"
      title="封禁用户"
      width="500"
      destroy-on-close
  >
    <ForbiddenUser :id="forbiddenDialogId"></ForbiddenUser>
  </el-dialog>

  <el-dialog
      v-model="editDialogVisible"
      title="编辑用户"
      width="500"
      destroy-on-close
  >
    <EditUser :id="editDialogId"></EditUser>
  </el-dialog>
</template>
<script setup>
import {unForbiddenUser,unMuteUser} from "@/api/admin.js"
import AdminLayout from "@/layout/AdminLayout.vue";
import {queryUser,deleteUser} from "@/api/admin.js"
import {ref, watch, reactive} from "vue"
import {Icon} from "@iconify/vue"
import MuteUser from "@/components/admin/user-list/MuteUserDialog.vue";
import ForbiddenUser from "@/components/admin/user-list/ForbiddenUserDialog.vue";
import EditUser from "@/components/admin/user-list/EditUserDialog.vue";
import {ElMessage} from "element-plus"



const editDialogVisible = ref(false)
const muteDialogVisible = ref(false)
const forbiddenDialogVisible = ref(false)

const editDialogId = ref()
const muteDialogId = ref()
const forbiddenDialogId = ref()
const openEditDialog = (id)=>{
  editDialogId.value = id
  editDialogVisible.value = true
}

const openMuteDialog = (id)=>{
  muteDialogId.value = id
  muteDialogVisible.value = true
}

const openForbiddenDialog = (id)=>{
  forbiddenDialogId.value = id
  forbiddenDialogVisible.value = true
}

const doUnMuteUser = async (id)=>{
  const response = await unMuteUser(id)
  if(response.code === 200){
    ElMessage.success({
      message:response.data,
      duration:1500
    })

    await doGetUserList()
  }
}

const doUnForbiddenUser = async (id)=>{
  const response = await unForbiddenUser(id)
  if(response.code === 200){
    ElMessage.success({
      message:response.data,
      duration:1500
    })

    await doGetUserList()
  }
}

const userData = ref([])
const pageTotal = ref(0)
const pageSize = 10
const pageNum = ref(1)
const userQuery = reactive({
  username: undefined,
  email: undefined
})
const doGetUserList = async () => {
  const response = await queryUser(pageNum.value, pageSize, userQuery)
  if (response.code === 200) {
    userData.value = response.data
    pageTotal.value = response.total
    pageNum.value = response.current
  }
}

await doGetUserList()

watch(pageNum, () => doGetUserList())

const doDeleteUser = async (id)=>{
  const response = await deleteUser(id)
  if(response.code === 200){
    ElMessage.success({
      message:response.data,
      duration:1500
    })
    await doGetUserList()
  }
}
</script>
<style scoped>

</style>