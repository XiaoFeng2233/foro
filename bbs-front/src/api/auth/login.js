import {useAxios} from "@/axios/axios.js"


export async function loginCaptcha() {
    const axios = useAxios()
    try {
        let response = await axios({
            method: "GET",
            url: "/login-captcha",
            async: false
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function login(username, password, captcha, captchaId) {
    const axios = useAxios()
    try {
        const res = await axios({
            method: "POST",
            url: "/login",
            data: {
                username,
                password,
                captcha,
                captchaId
            }
        })
        return res.data
    } catch (e) {
        console.error(e)
    }

}