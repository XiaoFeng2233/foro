import {useAxios} from "@/axios/axios.js"

export async function uploadImage(formData){
    const axios = useAxios();
    try {
        let response = await axios({
            method: "POST",
            url: "/upload/image",
            async: false,
            data:formData
        })

        return response.data
    } catch (e) {
        console.error(e)
    }
}
