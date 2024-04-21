<script setup>
import {reactive, ref} from "vue"
import AdminLayout from "@/layout/AdminLayout.vue";
import {queryTopic, deleteTopic} from "@/api/admin.js"
import TopicView from "@/components/admin/topic-list/TopicView.vue"
import ImageView from "@/components/admin/topic-list/ImageView.vue"
import CommentView from "@/components/admin/topic-list/CommentView.vue"
import EditTopic from "@/components/admin/topic-list/EditTopic.vue";

const query = ref({})
const topicData = ref([])
const pageTotal = ref(0)
const pageSize = 10
const pageNum = ref(1)

const content = ref("")
const TopicContentDialogVisible = ref(false)

const imageList = ref([])
const ImageDialogVisible = ref(false)

const topicId = ref()
const commentDialogVisible = ref(false)

const editTopicId = ref()
const editTopicDialogVisible = ref(false)

const getTopicData = async () => {
  const response = await queryTopic(pageNum.value, pageSize, query.value)
  if (response.code === 200) {
    topicData.value = response.data
    pageTotal.value = response.total
    pageNum.value = response.current
  }
}

await getTopicData()

const openTopicContentDialog = c => {
  content.value = c
  TopicContentDialogVisible.value = true
}

const openImageViewDialog = e => {
  imageList.value = e
  ImageDialogVisible.value = true
}

const openCommentViewDialog = e => {
  topicId.value = e
  commentDialogVisible.value = true
}

const doDeleteTopic = async id => {
  const response = await deleteTopic(id)
  if (response.code === 200) {
    await getTopicData()
  }
}

const openEditTopicDialog = id => {
  editTopicId.value = id
  editTopicDialogVisible.value = true
}
</script>

<template>
  <AdminLayout>
    <el-card header="帖子管理">
      <el-table :data="topicData">
        <el-table-column prop="id" label="ID"></el-table-column>
        <el-table-column prop="title" label="标题" width="180">
          <template #default="scope">
            <el-tooltip
                effect="dark"
                :content="scope.row.title"
                placement="top"
            >
              <div class="title-box">
                {{ scope.row.title }}
              </div>

            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="summary" label="内容">
          <template #default="scope">
            <el-button size="small" @click="openTopicContentDialog(scope.row.content)">点击查看</el-button>
          </template>
        </el-table-column>
        <el-table-column prop="tag.name" label="板块">
          <template #default="scope">
            {{ scope.row.tag.name }}
          </template>
        </el-table-column>
        <el-table-column prop="imageList" label="图片列表">
          <template #default="scope">
            <el-button size="small" @click="openImageViewDialog(scope.row.imageList)">点击查看</el-button>
          </template>
        </el-table-column>
        <el-table-column prop="user.username" label="发布用户"></el-table-column>
        <el-table-column prop="locationA" label="发布地点"></el-table-column>
        <el-table-column prop="ipA" label="发布IP"></el-table-column>
        <el-table-column prop="createTime" label="创建时间"></el-table-column>
        <el-table-column prop="sticky" label="置顶">
          <template #default="scope">
            <el-tag type="success" v-if="scope.row.sticky === 1">是</el-tag>
            <el-tag type="danger" v-else>否</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="recommend" label="推荐">
          <template #default="scope">
            <el-tag type="success" v-if="scope.row.recommend === 1">是</el-tag>
            <el-tag type="danger" v-else>否</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="isLock" label="锁定">
          <template #default="scope">
            <el-tag type="success" v-if="scope.row.isLock === 1">是</el-tag>
            <el-tag type="danger" v-else>否</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="comment" label="评论">
          <template #default="scope">
            <el-button size="small" @click="openCommentViewDialog(scope.row.id)">点击查看</el-button>
          </template>
        </el-table-column>
        <el-table-column prop="operation" label="操作">
          <template #default="scope">
            <el-button style="width: 100%;margin: 0;" type="primary" size="small" @click="openEditTopicDialog(scope.row.id)">
              编辑
            </el-button>
            <el-button style="width: 100%;margin: 0;" type="danger" size="small" @click="doDeleteTopic(scope.row.id)">
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
      v-model="TopicContentDialogVisible"
      title="帖子内容"
      width="600"
      destroy-on-close
  >
    <TopicView :content="content"/>
  </el-dialog>

  <el-dialog
      v-model="ImageDialogVisible"
      title="帖子图片查看"
      width="600"
      destroy-on-close
  >
    <ImageView :image-list="imageList"/>
  </el-dialog>

  <el-dialog
      v-model="commentDialogVisible"
      title="帖子评论查看"
      width="800"
      destroy-on-close
  >
    <CommentView :id="topicId"/>
  </el-dialog>

  <el-dialog
      v-model="editTopicDialogVisible"
      title="帖子编辑"
      width="500"
      destroy-on-close
  >
    <EditTopic :id="editTopicId"/>
  </el-dialog>
</template>

<style scoped>
.title-box {
  width: 100%;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>