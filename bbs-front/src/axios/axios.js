import axios from "axios"
import {ElNotification} from 'element-plus'
import router from "@/router/index.js"

// import {useRouter} from "vue-router"
import credentialStore from "@/stores/credential.js"
import localforage from "localforage"


export function useAxios() {
    const store = credentialStore();
    const request = axios.create({
        baseURL: import.meta.env.VITE_API_BASE_URL,
        timeout: 5000
    })

    request.interceptors.request.use(function (config) {
        if (store.token !== undefined && store.token !== null && store.token !== "") {
            config.headers.setAuthorization(store.token)
        }
        // 在发送请求之前做些什么
        return config;
    }, function (error) {
        // 对请求错误做些什么
        return Promise.reject(error);
    });


    request.interceptors.response.use(function (response) {
        //报错处理
        if (response.data.code === 500) {
            ElNotification({
                title: "失败",
                message: response.data.data,
                type: 'error',
            })
        }

        //未登录处理
        if (response.data.code === 401) {
            ElNotification({
                title: "未登录,禁止访问",
                message: response.data.data,
                type: 'warning',
            })


            localforage.removeItem("credential")
            store.clear()
            router.push({name: "NoLogin"})
        }

        //无权限处理
        if (response.data.code === 403) {
            ElNotification({
                title: "无权访问",
                message: response.data.data,
                type: 'warning',
            })
            localforage.removeItem("credential")
            store.clear()
            router.push({name: "NoPermission"})
        }

        return response;
    }, function (error) {
        // 超出 2xx 范围的状态码都会触发该函数。
        // 对响应错误做点什么

        ElNotification({
            title: "出现错误",
            message: '后台发生未知错误',
            type: 'error',
        })
        return Promise.reject(error);
    });

    return request;
}