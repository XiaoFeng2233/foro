import {defineStore} from 'pinia'

const store = defineStore("credential", {
    state: () => {
        return {
            token: "",
            username: "",
            roles: [],
            permissions: [],
            avatar: "",
            id: 0,
            mangedTagIds:[]
        }
    },
    actions: {
        clear() {
            this.id = 0
            this.token = ""
            this.username = ""
            this.avatar = ""
            this.roles = []
            this.permissions = []
            this.mangedTagIds = []
        },
        set(id, token, username, avatar, roles, permissions, mangedTagIds) {
            this.id = id
            this.token = token
            this.username = username
            this.avatar = avatar
            this.roles = roles
            this.permissions = permissions
            this.mangedTagIds = mangedTagIds
        }
    }
})

export default store

//     const credential = reactive({
//     token: "",
//     username: "",
//     roles:[],
//     permissions:[],
//     avatar:"",
//     id:""
// })