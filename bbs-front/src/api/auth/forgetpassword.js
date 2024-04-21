import {useAxios} from "@/axios/axios.js";

export async function forgetPasswordCaptcha() {
    const axios = useAxios()
    try {
        let response = await axios({
            method: "GET",
            url: "/forget-password-captcha",
            async: false
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function forgetPassword(email,captcha,captchaId) {
    const axios = useAxios()
    try {
        let response = await axios({
            method: "POST",
            url: "/forget-password",
            async: false,
            data:{
                email:email,
                captcha:captcha,
                captchaId:captchaId
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function getForgetPasswordInfo(token,code) {
    const axios = useAxios()
    try {
        let response = await axios({
            method: "GET",
            url: "/get-forget-password-info",
            async: false,
            params:{
                token:token,
                code:code
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

export async function resetPassword(token,code,password) {
    const axios = useAxios()
    try {
        let response = await axios({
            method: "POST",
            url: "/reset-password",
            async: false,
            data:{
                token:token,
                code:code,
                password:password
            }
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}

