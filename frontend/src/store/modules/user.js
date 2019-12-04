import {USER_ERROR, USER_REQUEST, USER_SUCCESS} from '../actions/user'
import Vue from 'vue'
import {AUTH_LOGOUT} from '../actions/auth'
import {urlPort} from "../../tool";

const state = {status: '', profile: {}};

const getters = {
    getProfile: state => state.profile,
    isProfileLoaded: state => !!state.profile.name,
    isCompany: state => state.profile.role === "ROLE_COMPANY",
    isUser: state => state.profile.role === "ROLE_USER",
    profile: state => state.profile,
};

const actions = {
    [USER_REQUEST]: ({commit, dispatch}) => {
        commit(USER_REQUEST);
        urlPort.get("/my/accounts", {withCredentials: true})
            .then(resp => {
                commit(USER_SUCCESS, resp)
            })
            // eslint-disable-next-line no-unused-vars
            .catch(resp => {
                commit(USER_ERROR);
                // dispatch(AUTH_LOGOUT)
            })
    },
};

const mutations = {
    [USER_REQUEST]: (state) => {
        state.status = 'loading'
    },
    [USER_SUCCESS]: (state, resp) => {
        state.status = 'success';
        Vue.set(state, 'profile', resp.data)
    },
    [USER_ERROR]: (state) => {
        state.status = 'error'
    },
    [AUTH_LOGOUT]: (state) => {
        state.profile = {}
    }
};

export default {
    state,
    getters,
    actions,
    mutations,
}