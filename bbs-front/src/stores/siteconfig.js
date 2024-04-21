import {defineStore} from 'pinia'

const store = defineStore("siteConfig", {
    state: () => {
        return {
            name: "",
            logo: "",
            favicon: "",
            footer: "",
            openRegister: true,
            open: true,
            maxFileUploadSize:0
        }

    },
    actions: {
        set(name, logo, favicon, footer, openRegister, open,maxFileUploadSize) {
            this.name = name;
            this.logo = logo;
            this.favicon = favicon;
            this.footer = footer;
            this.openRegister = openRegister;
            this.open = open;
            this.maxFileUploadSize = maxFileUploadSize;

        }
    }
})

export default store