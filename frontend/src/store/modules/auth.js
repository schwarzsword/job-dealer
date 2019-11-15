import {AUTH_ERROR, AUTH_LOGOUT, AUTH_REQUEST, AUTH_SUCCESS} from '../actions/auth'
import axios from 'axios'
import {urlPort} from "../../tool";


const state = {
    token: sessionStorage.getItem('user-token') || '',
    status: '', hasLoadedOnce: false
};

const getters = {
    isAuthenticated: state => !!state.token,
    authStatus: state => state.status,
};

const actions = {
    [AUTH_REQUEST]: ({commit}, user) => {
        return new Promise((resolve, reject) => {
            commit(AUTH_REQUEST);
            let params = new URLSearchParams();
            params.append('username', user.username);
            params.append('password', user.password);
            urlPort.post("/login", params, {withCredentials: true})
                .then(resp => {
                    sessionStorage.setItem('user-token', "logged");
                    axios.defaults.headers.common['Authorization'] = "logged";
                    commit(AUTH_SUCCESS, resp);
                    // dispatch(USER_REQUEST);
                    resolve(resp)
                })
                .catch(err => {
                    commit(AUTH_ERROR, err);
                    sessionStorage.removeItem('user-token');
                    reject(err)
                })
        })
    },
    [AUTH_LOGOUT]: ({commit}) => {
        return new Promise((resolve) => {
            commit(AUTH_LOGOUT);
            sessionStorage.removeItem('user-token');
            resolve()
        })
    }
};

const mutations = {
    [AUTH_REQUEST]: (state) => {
        state.status = 'loading'
    },
    [AUTH_SUCCESS]: (state) => {
        state.status = 'success';
        state.token = "logged";
        state.hasLoadedOnce = true
    },
    [AUTH_ERROR]: (state) => {
        state.status = 'error';
        state.hasLoadedOnce = true
    },
    [AUTH_LOGOUT]: (state) => {
        state.token = ''
    }
};

export default {
    state,
    getters,
    actions,
    mutations,
}