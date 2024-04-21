import {useAxios} from "@/axios/axios.js"

export async function queryTagList(pageNum, pageSize) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/tag/query",
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

export async function getTagList() {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/tag/list",
            async: false,
        })

        return response.data
    } catch (e) {
        console.error(e)
    }

}

export async function info(tagId) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/tag/info",
            async: false,
            params:{
                id:tagId
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }

}