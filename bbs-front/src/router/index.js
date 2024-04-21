import {createRouter, createWebHistory} from 'vue-router'
import Home from "@/views/HomeView.vue"
import Login from "@/views/LoginView.vue"
import Register from "@/views/RegisterView.vue"
import RegisterActive from "@/views/RegisterActiveView.vue"
import ForgetPassword from "@/views/ForgetPasswordView.vue"
import ForgetPasswordReset from "@/views/ForgetPasswordResetView.vue";
import Tags from "@/views/TagsView.vue"
import Tag from "@/views/TagView.vue"
import Topic from "@/views/TopicView.vue"
import Users from "@/views/UsersView.vue"
import UserDetail from "@/views/UsersDetailView.vue"
import Search from "@/views/SearchView.vue"

import Admin from "@/views/AdminView.vue"
import AdminIndex from "@/views/admin/IndexView.vue"
import AdminSiteConfig from "@/views/admin/SiteConfigView.vue"
import AdminMailConfig from "@/views/admin/MailConfigView.vue"
import AdminAddTag from "@/views/admin/AddTagView.vue"
import AdminUserList from "@/views/admin/UserListView.vue"
import AdminStorageConfig from "@/views/admin/StorageConfigView.vue"
import AdminManagerList from "@/views/admin/ManagerListView.vue"
import AdminTagList from "@/views/admin/TagListView.vue"
import AdminTopicManage from "@/views/admin/TopicManageView.vue"

import NotFound from "@/views/error/NotFound.vue"
import NoPermission from "@/views/error/NoPermission.vue"
import NoLogin from "@/views/error/NoLogin.vue"
import CloseRegister from "@/views/CloseRegisterView.vue";

import OtherUserCenterPanel from "@/views/user-detail/OtherUserCenterPanelView.vue"
import OtherUserBanLog from "@/views/user-detail/OtherUserBanLogView.vue"
import OtherUserCollect from "@/views/user-detail/OtherUserCollectListView.vue"
import OtherUserTopic from "@/views/user-detail/OtherUserTopicListVIew.vue"
import OtherUserComment from "@/views/user-detail/OtherUserCommentListView.vue"
import OtherUserFollow from "@/views/user-detail/OtherUserFollowListView.vue"
import OtherUserFans from "@/views/user-detail/OtherUserFansListView.vue"

import User from "@/views/UserView.vue"
import UserCenter from "@/views/user/UserCenterView.vue"
import UserCenterPanel from "@/views/user/user-center/UserCenterPanelView.vue";
import UserLoginLog from "@/views/user/user-center/LoginLogView.vue";
import UserBanLog from "@/views/user/user-center/BanLogView.vue";
import UserTopicList from "@/views/user/user-center/TopicListView.vue";
import UserCommentList from "@/views/user/user-center/CommentListView.vue";
import UserFollowList from "@/views/user/user-center/FollowListView.vue";
import UserFansList from "@/views/user/user-center/FansListView.vue";
import UserCollectList from "@/views/user/user-center/CollectListView.vue";
import UserSetting from "@/views/user/SettingView.vue";
import UserNotice from "@/views/user/NoticeView.vue";
import UserCreateTopic from "@/views/user/CreateTopicView.vue";


import nprogress from "nprogress"
import "nprogress/nprogress.css"
import lodash from "lodash"
import siteConfigStore from "@/stores/siteconfig.js"
import {hasPermission, hasRole, isAuth} from "@/utils/authUtil.js"


const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'Home',
            component: Home,
            meta: {
                title: "论坛主页"
            }
        },
        {
            path: '/tags',
            name: 'Tags',
            component: Tags,
            meta: {
                title: "板块列表"
            }
        },
        {
            path: '/users',
            name: 'Users',
            component: Users,
            meta: {
                title: "用户列表"
            }
        },
        {
            path: "/users/:id",
            name: "UserDetail",
            component: UserDetail,
            meta: {
                title: "用户详情",
                // requireAuth: true
            },
            children: [
                {
                    path: "",
                    name: "UserDetailRedirect",
                    redirect: {name: "OtherUserCenterPanel"}
                },
                {
                    path: "panel",
                    name: "OtherUserCenterPanel",
                    component: OtherUserCenterPanel
                },
                {
                    path: "ban-log",
                    name: "OtherUserBanLog",
                    component: OtherUserBanLog
                },
                {
                    path: "collect",
                    name: "OtherUserCollect",
                    component: OtherUserCollect
                },
                {
                    path: "topic",
                    name: "OtherUserTopic",
                    component: OtherUserTopic
                },
                {
                    path: "comment",
                    name: "OtherUserComment",
                    component: OtherUserComment
                },
                {
                    path: "follow",
                    name: "OtherUserFollow",
                    component: OtherUserFollow
                },
                {
                    path: "fans",
                    name: "OtherUserFans",
                    component: OtherUserFans
                },
            ]
        },
        {
            path: '/tag/:id',
            name: 'Tag',
            component: Tag,
            meta: {
                title: "板块详情"
            }
        },
        {
            path: '/topic/:id',
            name: 'Topic',
            component: Topic,
            meta: {
                title: "帖子详情"
            }
        },
        {
            path: '/login',
            name: 'Login',
            component: Login,
            meta: {
                title: "登录"
            }
        },
        {
            path: '/register',
            name: 'Register',
            component: Register,
            meta: {
                title: "注册"
            }
        },
        {
            path: '/register/active/:token/:code',
            name: 'RegisterActive',
            component: RegisterActive,
            meta: {
                title: "账户激活"
            }
        },
        {
            path: '/forget-password',
            name: 'ForgetPassword',
            component: ForgetPassword,
            meta: {
                title: "找回密码"
            }
        },
        {
            path: '/forget-password/reset/:token/:code',
            name: 'ForgetPasswordReset',
            component: ForgetPasswordReset,
            meta: {
                title: "重置密码"
            }
        },
        {
            path: '/search/:param',
            name: 'Search',
            component: Search,
            meta: {
                title: "搜索关键词"
            }
        },
        {
            path: "/admin",
            component: Admin,
            name: "Admin",
            meta: {
                title: "后台管理",
                requireAuth: true,
                requirePermissions:["admin"]
            },
            children: [
                {
                    path: "",
                    name: "AdminDefaultRedirect",
                    redirect: {name: "AdminIndex"}
                },
                {
                    path: "index",
                    name: "AdminIndex",
                    component: AdminIndex
                },
                {
                    path: "site-config",
                    name: "AdminSiteConfig",
                    component: AdminSiteConfig
                },
                {
                    path: "mail-config",
                    name: "AdminMailConfig",
                    component: AdminMailConfig
                },
                {
                    path: "user-list",
                    name: "AdminUserList",
                    component: AdminUserList
                },
                {
                    path: "manager-list",
                    name: "AdminManagerList",
                    component: AdminManagerList
                },
                {
                    path: "tag-list",
                    name: "AdminTagList",
                    component: AdminTagList
                },
                {
                    path: "add-tag",
                    name: "AdminAddTag",
                    component: AdminAddTag
                },
                {
                    path: "/storage-config",
                    name: "AdminStorageConfig",
                    component: AdminStorageConfig
                },
                {
                    path: "/manage-topic",
                    name: "AdminTopicManage",
                    component: AdminTopicManage
                },
            ]
        },
        {
            path: "/user",
            component: User,
            name: "User",
            meta: {
                requireAuth: true
            },
            children: [
                {
                    path: "",
                    name: "UserDefaultRedirect",
                    redirect: {name: "UserCenter"}
                },
                {
                    path: "create-topic",
                    name: "UserCreateTopic",
                    component: UserCreateTopic,
                    meta: {
                        title: "发布帖子",
                        requireAuth: true
                    }
                },
                {
                    path: "setting",
                    name: "UserSetting",
                    component: UserSetting,
                    meta: {
                        title: "个人设置",
                        requireAuth: true
                    }
                },
                {
                    path: "notice",
                    name: "UserNotice",
                    component: UserNotice,
                    meta: {
                        title: "用户消息",
                        requireAuth: true
                    }
                },
                {
                    path: "center",
                    name: "UserCenter",
                    component: UserCenter,
                    meta: {
                        title: "用户中心",
                        requireAuth: true
                    },
                    children: [
                        {
                            path: "",
                            name: "UserCenterDefaultRedirect",
                            redirect: {name: "UserCenterPanel"}
                        },
                        {
                            path: "panel",
                            name: "UserCenterPanel",
                            component: UserCenterPanel,
                            meta: {
                                title: "用户中心",
                                // requireAuth: true
                            }
                        },
                        {
                            path: "login-log",
                            name: "UserLoginLog",
                            component: UserLoginLog,
                            meta: {
                                title: "登录记录",
                                // requireAuth: true
                            }
                        },
                        {
                            path: "ban-log",
                            name: "UserBanLog",
                            component: UserBanLog,
                            meta: {
                                title: "小黑屋记录",
                                // requireAuth: true
                            }
                        },
                        {
                            path: "topic-list",
                            name: "UserTopicList",
                            component: UserTopicList,
                            meta: {
                                title: "用户主题",
                                // requireAuth: true
                            }
                        },
                        {
                            path: "comment-list",
                            name: "UserCommentList",
                            component: UserCommentList,
                            meta: {
                                title: "用户评论",
                                // requireAuth: true
                            }
                        },
                        {
                            path: "follow-list",
                            name: "UserFollowList",
                            component: UserFollowList,
                            meta: {
                                title: "用户关注列表",
                                // requireAuth: true
                            }
                        },
                        {
                            path: "fans-list",
                            name: "UserFansList",
                            component: UserFansList,
                            meta: {
                                title: "用户粉丝列表",
                                // requireAuth: true
                            }
                        },
                        {
                            path: "collect-list",
                            name: "UserCollectList",
                            component: UserCollectList,
                            meta: {
                                title: "用户收藏列表",
                                requireAuth: true
                            }
                        },
                    ]
                }
            ]
        },
        {
            path: '/403',
            name: 'NoPermission',
            component: NoPermission
        },
        {
            path: '/401',
            name: 'NoLogin',
            component: NoLogin
        },

        {
            path: '/close-register',
            name: 'CloseRegister',
            component: CloseRegister
        },
        {
            path: '/:pathMatch(.*)*',
            name: 'NotFound',
            component: NotFound
        }
    ]
})


//定义路由守卫
router.beforeEach((to, from, next) => {
    nprogress.start()

    //检查需要登录的接口是否登录
    if (to.meta.requireAuth === true) {
        if (!isAuth()) {
            next("/401")
            return false;
        }
    }

    //检查需要检测角色接口是否检测角色
    if (!lodash.isEmpty(to.meta.requireRoles)) {
        to.meta.requireRoles.forEach(item => {
            if (!hasRole(item)) {
                next("/403")
                return false;
            }
        })
    }

    //检查需要鉴权的接口是否鉴权
    if (!lodash.isEmpty(to.meta.requirePermissions)) {
        to.meta.requirePermissions.forEach(item => {
            if (!hasRole(item)) {
                next("/403")
                return false;
            }
        })
    }

    next()
    let siteConfig = siteConfigStore()
    if (!lodash.isEmpty(to.meta.title)) {
        document.title = siteConfig.name + " - " + to.meta.title
    }
    nprogress.done()
})

router.afterEach(() => {
    nprogress.done()
})


export default router
