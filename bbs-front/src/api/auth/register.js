import {useAxios} from "@/axios/axios.js"


export async function registerCaptcha() {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/register-captcha",
            async: false
        })

        return response.data
    } catch (e) {
        console.error(e)
    }

}

export async function registerActive(token,code){
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/register/active",
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

export async function register(email,username,password,captcha,captchaId){
    const axios = useAxios();
    try {
        let response = await axios({
            method: "POST",
            url: "/register",
            data:{
                email,
                username,
                password,
                captcha,
                captchaId
            },
            async: false
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}