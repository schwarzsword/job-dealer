import {AUTH_REQUEST, AUTH_ERROR, AUTH_SUCCESS, AUTH_LOGOUT} from '../actions/auth'
import {USER_REQUEST} from '../actions/user'
import axios from 'axios'
import {urlPort} from "../../tool";
import VueCookies from 'vue-cookies'


const state = {
    token: localStorage.getItem('user-token') || '',
    status: '', hasLoadedOnce: false
};

const getters = {
    isAuthenticated: state => !!state.token,
    authStatus: state => state.status,
};

const actions = {
    [AUTH_REQUEST]: ({commit, dispatch}, user) => {
        return new Promise((resolve, reject) => {
            commit(AUTH_REQUEST);
            let params = new URLSearchParams();
            params.append('username', user.username);
            params.append('password', user.password);
            // let remember = this.$cookies.get("remember-me-token");
            // alert(this.$cookies.isKey("remember-me-token"));
            axios.post(urlPort("/login"), params, {withCredentials: true})
                .then(resp => {
                    localStorage.setItem('user-token', "logged");
                    console.log(resp);
                    axios.defaults.headers.common['Authorization'] = "logged";
                    commit(AUTH_SUCCESS, resp);
                    // dispatch(USER_REQUEST);
                    resolve(resp)
                })
                .catch(err => {
                    commit(AUTH_ERROR, err);
                    localStorage.removeItem('user-token');
                    reject(err)
                })
        })
    },
    [AUTH_LOGOUT]: ({commit}) => {
        return new Promise((resolve) => {
            commit(AUTH_LOGOUT);
            localStorage.removeItem('user-token');
            resolve()
        })
    }
};

const mutations = {
    [AUTH_REQUEST]: (state) => {
        state.status = 'loading'
    },
    [AUTH_SUCCESS]: (state, resp) => {
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