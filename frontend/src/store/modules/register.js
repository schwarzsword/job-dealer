import {REG_REQUEST} from "../actions/register";
import axios from "axios";
import {urlPort} from "../../tool";


const state = {status: ''};

const actions = {
    [REG_REQUEST]: ({commit}, user) => {
        return new Promise((resolve, reject) => {
            commit(REG_REQUEST);
            let params = new URLSearchParams();
            params.append('email', user.email);
            params.append('password', user.password);
            params.append('isCompany', user.isCompany);
            axios.post(urlPort("/signUp"), params, {withCredentials: true})
                .then(resp => {
                    resolve(resp)
                })
                .catch(err => {
                    reject(err)
                })
        })
    },
};

const mutations = {
    [REG_REQUEST]: (state) => {
        state.status = 'loading'
    },
};

export default {
    state,
    actions,
    mutations,
}