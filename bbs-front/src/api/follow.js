import {useAxios} from "@/axios/axios.js";

export async function queryUserFollowList(pageNum, pageSize) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/follow/query-user-follow-list",
            async: false,
            params: {
                pageNum: pageNum,
                pageSize: pageSize,
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }

}

export async function queryUserFollowListByUserId(pageNum, pageSize, userId) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/follow/query-user-follow-list-by-user-id",
            async: false,
            params: {
                pageNum: pageNum,
                pageSize: pageSize,
                userId: userId
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }

}

export async function queryUserFansList(pageNum, pageSize) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/follow/query-user-fans-list",
            async: false,
            params: {
                pageNum: pageNum,
                pageSize: pageSize,
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }

}

export async function queryUserFansListByUserId(pageNum, pageSize, userId) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/follow/query-user-fans-list-by-user-id",
            async: false,
            params: {
                pageNum: pageNum,
                pageSize: pageSize,
                userId: userId
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }

}

export async function followUser(targetUserId) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/follow/follow-user",
            async: false,
            params: {
                targetUserId: targetUserId
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }

}

export async function unFollowUser(targetUserId) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/follow/unfollow-user",
            async: false,
            params: {
                targetUserId: targetUserId
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }

}