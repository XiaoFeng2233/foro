import {useAxios} from "@/axios/axios.js"

export async function logout() {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/user/logout",
            async: false
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function info() {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/user/info",
            async: false
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function generalInfo() {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/user/general-info",
            async: false
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function getInfoByUserId(userId) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/user/get-info-by-user-id",
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

export async function userList(pageNum, pageSize) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/user/user-list",
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

export async function updateNickname(nickname) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "POST",
            url: "/user/update-nickname",
            async: false,
            params: {
                nickname: nickname
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function updateDescription(description) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "POST",
            url: "/user/update-description",
            async: false,
            params: {
                description: description
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function updatePassword(oldPwd, newPwd) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "POST",
            url: "/user/update-password",
            async: false,
            data: {
                oldPwd: oldPwd,
                newPwd: newPwd
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function updateAvatar(file) {
    let formData = new FormData;
    formData.append("file", file)
    const axios = useAxios();
    try {
        let response = await axios({
            method: "POST",
            headers: {
                "Content-Type": "multipart/form-data"
            },
            url: "/user/update-avatar",
            async: false,
            data: formData
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function updateBackgroundImage(file) {
    let formData = new FormData;
    formData.append("file", file)
    const axios = useAxios();
    try {
        let response = await axios({
            method: "POST",
            headers: {
                "Content-Type": "multipart/form-data"
            },
            url: "/user/update-background-image",
            async: false,
            data: formData
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function loginLog(pageNum, pageSize) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/user/login-log",
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

export async function queryUserTopic(pageNum, pageSize) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/user/query-user-topic",
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

export async function queryUserTopicByUserId(pageNum, pageSize,userId) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/user/query-user-topic-by-user-id",
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

export async function queryUserCollect(pageNum, pageSize) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/user/query-user-collect",
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

export async function queryUserCollectByUserId(pageNum, pageSize,userId) {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/user/query-user-collect-by-user-id",
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

export async function signIn() {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/user/sign-in",
            async: false,
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}