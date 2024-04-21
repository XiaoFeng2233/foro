<script setup>
import AdminLayout from "@/layout/AdminLayout.vue";
import {ref} from "vue";
import {queryTag} from "@/api/admin.js"
import {Icon} from "@iconify/vue"
import {deleteTag} from "@/api/admin.js"
import {ElNotification} from "element-plus"
import EditTagDialog from "@/components/admin/tag-list/EditTagDialog.vue";


const editDialogVisible = ref(false)
const editDialogId = ref(undefined)
const tagData = ref([])
const pageTotal = ref(0)
const pageSize = 10
const pageNum = ref(1)

const doGetTagList = async ()=>{
  const response = await queryTag(pageNum.value,pageSize)
  if(response.code === 200){
    tagData.value = response.data
    pageTotal.value = response.total
    pageNum.value = response.current
  }

}

await doGetTagList()

const doDeleteTag = async (id)=>{
  const response = await deleteTag(id)
  if(response.code === 200){
    ElNotification({
      message:response.data,
      type:"success",
      title:"成功",
      duration:1500
    })
    await doGetTagList()
  }
}

const openEditDialog = (id)=>{
  editDialogId.value = id
  editDialogVisible.value = true
}
</script>

<template>
  <AdminLayout>
    <el-card header="板块列表">
      <el-table :data="tagData">
        <el-table-column prop="id" label="id" width="70">
        </el-table-column>
        <el-table-column prop="name" label="名称">
        </el-table-column>
        <el-table-column prop="color" label="颜色">
          <template #default="scope">
            <el-tooltip
                effect="dark"
                :content="scope.row.color"
                placement="top"
            >
              <el-tag :color="scope.row.color" round style="border:none;width:30px;height:30px;border-radius: 999px"></el-tag>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="描述">
        </el-table-column>
        <el-table-column prop="operation" label="操作">
          <template #default="scope">
            <el-button type="primary" @click="openEditDialog(scope.row.id)" link>
              <el-icon>
                <Icon icon="ri:edit-line"></Icon>
              </el-icon>
              编辑</el-button>
            <el-popconfirm
                width="220"
                confirm-button-text="确认"
                cancel-button-text="取消"
                title="确定要删除该板块吗?"
                @confirm="doDeleteTag(scope.row.id)"
            >
              <template #reference>
                <el-button type="danger" link>
                  <el-icon>
                    <Icon icon="fluent:delete-28-regular" width="24" height="24"></Icon>
                  </el-icon>
                  删除</el-button>
              </template>
            </el-popconfirm>

          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </AdminLayout>
  <el-dialog v-model="editDialogVisible" title="修改板块" width="800" destroy-on-close>
    <EditTagDialog :id="editDialogId"></EditTagDialog>
  </el-dialog>
</template>

<style scoped>

</style>