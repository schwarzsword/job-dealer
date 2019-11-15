import {USER_REQUEST, USER_ERROR, USER_SUCCESS} from '../actions/user'
import Vue from 'vue'
import {AUTH_LOGOUT} from '../actions/auth'
import axios from 'axios'
import {urlPort} from "../../tool";

const state = {status: '', profile: {}};

const getters = {
    getProfile: state => state.profile,
    isProfileLoaded: state => !!state.profile.name,
}

const actions = {
    [USER_REQUEST]: ({commit, dispatch}) => {
        commit(USER_REQUEST);
        urlPort.get("/profile", {withCredentials: true})
            .then(resp => {
                commit(USER_SUCCESS, resp)
            })
            // eslint-disable-next-line no-unused-vars
            .catch(resp => {
                commit(USER_ERROR);
                // dispatch(AUTH_LOGOUT)
            })
    },
}

const mutations = {
    [USER_REQUEST]: (state) => {
        state.status = 'loading'
    },
    [USER_SUCCESS]: (state, resp) => {
        state.status = 'success';
        Vue.set(state, 'profile', resp)
    },
    [USER_ERROR]: (state) => {
        state.status = 'error'
    },
    [AUTH_LOGOUT]: (state) => {
        state.profile = {}
    }
}

export default {
    state,
    getters,
    actions,
    mutations,
}