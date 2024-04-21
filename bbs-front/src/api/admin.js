import {useAxios} from "@/axios/axios.js";

export async function getStatistic() {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/admin/get-statistic",
            async: false,
        })

        return response.data
    } catch (e) {
        console.error(e)
    }

}

export async function getUserStatistic() {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/admin/get-user-statistic",
            async: false,
        })

        return response.data
    } catch (e) {
        console.error(e)
    }

}

export async function getTopicStatistic() {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/admin/get-topic-statistic",
            async: false,
        })

        return response.data
    } catch (e) {
        console.error(e)
    }

}

export async function getEmailConfig() {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/admin/get-email-config",
            async: false,
        })

        return response.data
    } catch (e) {
        console.error(e)
    }

}

export async function updateEmailConfig(host, port, username, password, address, useSSL) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "POST",
            url: "/admin/update-email-config",
            async: false,
            data: {
                host: host,
                port: port,
                username: username,
                password: password,
                address: address,
                useSSL: useSSL
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }

}


export async function getStorageConfig() {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/admin/get-storage-config",
            async: false,
        })

        return response.data
    } catch (e) {
        console.error(e)
    }

}

export async function updateStorageConfig(endpoint, accessKey, secretKey) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "POST",
            url: "/admin/update-storage-config",
            async: false,
            data: {
                endpoint: endpoint,
                accessKey: accessKey,
                secretKey: secretKey
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }

}

export async function getSiteConfig() {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/admin/get-site-config",
            async: false,
        })

        return response.data
    } catch (e) {
        console.error(e)
    }

}

export async function updateSiteConfig(name, logo, favicon, url, open_register, open, footer, max_file_upload_size) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "POST",
            url: "/admin/update-site-config",
            async: false,
            data: {
                name: name,
                logo: logo,
                favicon: favicon,
                url: url,
                open_register: open_register,
                open: open,
                footer: footer,
                max_file_upload_size: max_file_upload_size
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }

}

export async function queryUser(pageNum, pageSize, userQuery) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "POST",
            url: "/admin/query-user",
            async: false,
            params: {
                pageNum: pageNum,
                pageSize: pageSize
            },
            data: userQuery
        })

        return response.data
    } catch (e) {
        console.error(e)
    }

}


export async function queryTag(pageNum, pageSize) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "POST",
            url: "/admin/query-tag",
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

export async function deleteTag(tagId) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/admin/delete-tag",
            async: false,
            params: {
                id: tagId
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }

}

export async function addTag(name, description, color) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "POST",
            url: "/admin/add-tag",
            async: false,
            data: {
                name: name,
                description: description,
                color: color
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }

}

export async function updateTag(name, description, color, id) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "POST",
            url: "/admin/update-tag",
            async: false,
            data: {
                name: name,
                description: description,
                color: color,
                id: id
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }

}

export async function muteUser(userId, endTime) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "POST",
            url: "/admin/mute-user",
            async: false,
            data: {
                userId: userId,
                endTime: endTime
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }

}

export async function forbiddenUser(userId, endTime, reason) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "POST",
            url: "/admin/forbidden-user",
            async: false,
            data: {
                userId: userId,
                endTime: endTime,
                reason: reason
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }

}

export async function unMuteUser(userId) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/admin/un-mute-user",
            async: false,
            params: {
                userId: userId
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function unForbiddenUser(userId) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/admin/un-forbidden-user",
            async: false,
            params: {
                userId: userId
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function updateUser(id, email, nickname, description, score, password) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "POST",
            url: "/admin/update-user",
            async: false,
            data: {
                id: id,
                email: email,
                nickname: nickname,
                description: description,
                score: score,
                password: password
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function deleteUser(id) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/admin/delete-user",
            async: false,
            params: {
                id: id
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function userList() {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/admin/user-list",
            async: false,
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function addTagManager(userId, tagId) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "POST",
            url: "/admin/add-tag-manager",
            async: false,
            data: {
                userId: userId,
                tagId: tagId
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function queryTagManager(pageNum, pageSize) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/admin/query-tag-manager",
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

export async function deleteTagManager(id) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/admin/delete-tag-manager",
            async: false,
            params: {
                id: id
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function queryTopic(pageNum, pageSize, query) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "POST",
            url: "/admin/query-topic",
            async: false,
            params: {
                pageNum: pageNum,
                pageSize: pageSize
            },
            data: {
                query
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function deleteComment(id) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/admin/delete-comment",
            async: false,
            params: {
                id:id
            },
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function deleteTopic(id) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/admin/delete-topic",
            async: false,
            params: {
                id:id
            },
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function updateTopic(id,title,content,sticky,recommend,isLock) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "POST",
            url: "/admin/update-topic",
            async: false,
            data:{
                id:id,
                title:title,
                content:content,
                sticky:sticky,
                recommend:recommend,
                isLock:isLock
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}