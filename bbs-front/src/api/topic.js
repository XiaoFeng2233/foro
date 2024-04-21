import {useAxios} from "@/axios/axios.js"

export async function publishTopic(title, tagId, content, summary, imageFileIds, lock, scoreRequire) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "POST",
            url: "/topic/publish",
            async: false,
            data: {
                title: title,
                tagId: tagId,
                content: content,
                summary: summary,
                imageFileIds: imageFileIds,
                lock: lock,
                scoreRequire: scoreRequire
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}


export async function view(id) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/topic/view",
            async: false,
            params:{
                id:id
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function like(id) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/topic/like",
            async: false,
            params:{
                topicId:id
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function collect(id) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/topic/collect",
            async: false,
            params:{
                topicId:id
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}


export async function unLike(id) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/topic/unLike",
            async: false,
            params:{
                topicId:id
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function unCollect(id) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/topic/unCollect",
            async: false,
            params:{
                topicId:id
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function queryLatestTopic(pageNum, pageSize) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/topic/query-latest-topic",
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

export async function queryRecommendTopic(pageNum, pageSize) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/topic/query-recommend-topic",
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

export async function queryHotTopic(pageNum, pageSize) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/topic/query-hot-topic",
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

export async function queryLatestTopicByTagId(pageNum, pageSize,tagId) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/topic/query-latest-topic-by-tag-id",
            async: false,
            params: {
                pageNum: pageNum,
                pageSize: pageSize,
                tagId:tagId
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function queryRecommendTopicByTagId(pageNum, pageSize,tagId) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/topic/query-recommend-topic-by-tag-id",
            async: false,
            params: {
                pageNum: pageNum,
                pageSize: pageSize,
                tagId:tagId
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function queryHotTopicByTagId(pageNum, pageSize,tagId) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/topic/query-hot-topic-by-tag-id",
            async: false,
            params: {
                pageNum: pageNum,
                pageSize: pageSize,
                tagId:tagId
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function search(pageNum, pageSize,param) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/topic/search",
            async: false,
            params: {
                pageNum: pageNum,
                pageSize: pageSize,
                param:param
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}


export async function deleteTopic(topicId) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/topic/delete",
            async: false,
            params: {
                id:topicId
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}


export async function stick(topicId) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/topic/stick",
            async: false,
            params: {
                id:topicId
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function unStick(topicId) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/topic/unstick",
            async: false,
            params: {
                id:topicId
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}



export async function lock(topicId) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/topic/lock",
            async: false,
            params: {
                id:topicId
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function unLock(topicId) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/topic/unlock",
            async: false,
            params: {
                id:topicId
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}


export async function recommend(topicId) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/topic/recommend",
            async: false,
            params: {
                id:topicId
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function unRecommend(topicId) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/topic/unrecommend",
            async: false,
            params: {
                id:topicId
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}