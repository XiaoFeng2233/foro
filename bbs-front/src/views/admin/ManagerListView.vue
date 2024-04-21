<template>
  <AdminLayout>
    <el-card header="版主管理">
      <el-button type="primary" class="mb-2" @click="openAddManagerDialog">
        <el-icon class="me-2" size="large">
          <Icon icon="gala:add"></Icon>
        </el-icon>
        添加新版主
      </el-button>
      <el-table :data="managerData">
        <el-table-column prop="id" label="id" width="70">
        </el-table-column>
        <el-table-column prop="user.username" label="用户名">
        </el-table-column>
        <el-table-column prop="user.nickname" label="昵称">
        </el-table-column>
        <el-table-column prop="user.avatar" label="头像">
          <template #default="scope">
            <el-avatar :src="scope.row.user.avatar"/>
          </template>
        </el-table-column>
        <el-table-column prop="tag" label="管理板块">
          <template #default="scope">
            <el-tag :color="scope.row.tag.color" effect="dark">
              {{scope.row.tag.name}}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="operation" label="操作">
          <template #default="scope">
            <el-button size="small" type="danger" @click="doDeleteManager(scope.row.id)">
              <el-icon size="large" class="me-2">
                <Icon icon="material-symbols:delete-outline" width="24" height="24"></Icon>
              </el-icon>
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination v-if="pageTotal> pageSize" layout="prev, pager, next" :total="pageTotal"
                     v-model:current-page="pageNum" :page-size="pageSize"
                     style="margin-top: 20px;justify-content: center"/>
    </el-card>

  </AdminLayout>

  <el-dialog
      destroy-on-close
      v-model="addManagerDialogVisible"
      title="添加新的版主"
      width="500"
  >
    <AddManager></AddManager>
  </el-dialog>
</template>
<script setup>
import {Icon} from "@iconify/vue"
import AdminLayout from "@/layout/AdminLayout.vue";
import {ref, watch} from "vue"
import AddManager from "@/components/admin/manager-list/AddManager.vue";
import {deleteTagManager, queryTagManager} from "@/api/admin.js"
import {ElMessage} from "element-plus"


const managerData = ref([])
const pageTotal = ref(0)
const pageSize = 10
const pageNum = ref(1)

const addManagerDialogVisible = ref(false)
const openAddManagerDialog = () => {
  addManagerDialogVisible.value = true
}

const doQueryTagManage = async () => {
  const response = await queryTagManager(pageNum.value, pageSize)
  if (response.code === 200) {
    managerData.value = response.data
    pageTotal.value = response.total
    pageNum.value = response.current
  }
}

await doQueryTagManage()

watch(pageNum, () => {
  doQueryTagManage()
})

const doDeleteManager = async (id)=>{
  const response = await deleteTagManager(id)
  if (response.code === 200){
    ElMessage.success({
      message:response.data,
      duration:1500
    })

    await doQueryTagManage()
  }
}
</script>
<style scoped>

</style>