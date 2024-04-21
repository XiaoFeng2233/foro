import {useAxios} from "@/axios/axios.js";

export async function queryUserForbiddenLog(pageNum, pageSize) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/forbidden-log/query-user-forbidden-log",
            async: false,
            params: {
                pageNum: pageNum,
                pageSize: pageSize
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function queryUserForbiddenLogByUserId(pageNum, pageSize,userId) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/forbidden-log/query-user-forbidden-log-by-user-id",
            async: false,
            params: {
                pageNum: pageNum,
                pageSize: pageSize,
                userId:userId
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}