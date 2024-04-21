import {useAxios} from "@/axios/axios.js";

export async function queryUserCommentList(pageNum, pageSize) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/comment/query-user-comment-list",
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

export async function queryUserCommentListByUserId(pageNum, pageSize, userId) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/comment/query-user-comment-list-by-user-id",
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


export async function queryTopicComment(pageNum, pageSize, topicId) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/comment/query-topic-comment",
            async: false,
            params: {
                pageNum: pageNum,
                pageSize: pageSize,
                topicId: topicId
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }

}

export async function publishComment(topicId, content, parentId) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "POST",
            url: "/comment/publish-comment",
            async: false,
            data: {
                topicId: topicId,
                content: content,
                parentId: parentId
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }

}


export async function deleteComment(commentId) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/comment/delete",
            async: false,
            params: {
                id:commentId
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }

}