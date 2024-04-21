import credentialStore from "@/stores/credential.js"
import lodash from "lodash"

export function isAuth() {
    const store = credentialStore()
    if (!lodash.isEmpty(store.username) && !lodash.isEmpty(store.token)) {
        return true;
    } else {
        return false;
    }
}


export function hasPermission(permissionName) {
    const store = credentialStore()
    if (isAuth()) {
        return store.permissions.some(elem => elem === permissionName);
    } else {
        return false;
    }
}

export function hasRole(roleName) {
    const store = credentialStore()
    if (isAuth()) {
        return store.roles.some(elem => elem === roleName);
    } else {
        return false;
    }
}

export function isManager(tagId) {
    const store = credentialStore()
    if (isAuth()) {
        if (store.roles.some(elem => elem === "topic_manager") && store.mangedTagIds.some(elem => elem === tagId)){
            return true;
        }else {
            return false;
        }
    } else {
        return false;
    }
}