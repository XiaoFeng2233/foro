import {useAxios} from "@/axios/axios.js";

export async function siteConfig() {
    const axios = useAxios();
    try {
        let response = await axios({
            method: "GET",
            url: "/config/site-config",
            async: false
        })

        return response.data
    } catch (e) {
        console.error(e)
    }

}