import {useAxios} from "@/axios/axios.js";

export async function queryUserNotice(pageNum, pageSize) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/notice/query-user-notice-list",
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