
<template>
   <DefaultLayout>
     <div class="row row-card justify-content-center">
       <div class="col-lg-9">
         <TopicList/>
       </div>
       <div class="col-lg-3">
         <div class="row row-cards ">
           <div class="col-md-12 " style="top: 105px">
             <div class="row row-cards" v-if="isAuth()">
               <UserCard></UserCard>
             </div>
             <div class="card" v-else>
               <div class="card-header">
                 <h3 class="card-title">
                   {{store.name}}
                 </h3>
               </div>
               <div class="card-body ">
                 {{store.name}}是一个聚集程序员、站长、互联网从业者的在线社区平台，主要分享和讨论与编程、网站建设、SEO、服务器运维、互联网营销等相关的技术和经验。
               </div>
               <div class="d-flex">
                 <router-link :to="{name:'Register'}" class="card-btn">注册</router-link>
                 <router-link :to="{name:'Login'}" class="card-btn">登陆</router-link>
               </div>
             </div>
           </div>
         </div>
       </div>
     </div>

   </DefaultLayout>
</template>
<script setup>
import {isAuth} from "@/utils/authUtil.js"
import DefaultLayout from "@/layout/DefaultLayout.vue";
import TopicList from "@/components/home-view/TopicList.vue";
import UserCard from "@/components/home-view/UserCard.vue";
import siteConfigStore from "@/stores/siteconfig.js"

const store = siteConfigStore()

</script>

<style scoped></style>
