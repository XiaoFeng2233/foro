<template>
  <el-row :gutter="24">
    <el-col :span="6">
      <el-card>
        <div class="statistic">
          <el-avatar style="background-color: #eccc68" shape="square">
            <template #default>
              <Icon icon="ph:user-bold" width="26"></Icon>
            </template>
          </el-avatar>
          <div class="statistic-info">
            <div class="statistic-title">用户总数</div>
            <div class="statistic-value">{{statistic.userCount}}</div>
          </div>
        </div>

      </el-card>
    </el-col>

    <el-col :span="6">
      <el-card>
        <div class="statistic">
          <el-avatar shape="square" style="background-color: #7bed9f">
            <template #default>
              <Icon icon="mdi:text-box-outline" width="26"></Icon>
            </template>
          </el-avatar>
          <div class="statistic-info">
            <div class="statistic-title">帖子总数</div>
            <div class="statistic-value">{{statistic.topicCount}}</div>
          </div>
        </div>
      </el-card>
    </el-col>

    <el-col :span="6">
      <el-card>
        <div class="statistic">
          <el-avatar shape="square" style="background-color: #ff4757">
            <template #default>
              <Icon icon="material-symbols:comment-outline" width="26"></Icon>
            </template>
          </el-avatar>
          <div class="statistic-info">
            <div class="statistic-title">评论总数</div>
            <div class="statistic-value">{{statistic.commentCount}}</div>
          </div>
        </div>
      </el-card>
    </el-col>

    <el-col :span="6">
      <el-card>
        <div class="statistic">
          <el-avatar shape="square" style="background-color: #5352ed">
            <template #default>
              <Icon icon="mdi:tag-outline" width="26"></Icon>
            </template>
          </el-avatar>
          <div class="statistic-info">
            <div class="statistic-title">板块总数</div>
            <div class="statistic-value">{{statistic.tagCount}}</div>
          </div>
        </div>
      </el-card>
    </el-col>
  </el-row>
</template>
<script setup>
import {getStatistic} from "@/api/admin.js"
import {Icon} from "@iconify/vue";
import {ref} from "vue"

const statistic = ref({})

const doGetStatistic = async ()=>{
  const response = await getStatistic()
  statistic.value = response.data
}

doGetStatistic()
</script>
<style scoped>
.statistic {
  display: flex;
  justify-content: center;
  align-items: center;
}

.statistic > .statistic-info {
  margin-left: 15px;
  flex-grow: 1;
}

.statistic-title {
  color: rgb(24, 36, 51);
  font-size: 18px;
  font-weight: 500;
}

.statistic-value {
  color: rgb(102, 115, 130);
  font-size: 16px;

}

</style>