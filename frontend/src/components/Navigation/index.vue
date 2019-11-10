<template>
    <div class="header">
        <div class="container">
            <div class="logo">
                <div class="link">
                    <router-link to="/"></router-link>
                </div>
            </div>
            <div class="find">
                <input type="input" name="find" placeholder="Search...">
            </div>
            <div class="menu">
                <ul>
                    <li><router-link class="nav-link" to="/about">Vacancies</router-link></li>
                    <li><router-link class="nav-link" to="/rules">Resumes</router-link></li>
                </ul>
            </div>
            <div class="user">
                <div v-if="isProfileLoaded">
                    <router-link class="sign_in" to="/profile">My profile</router-link>
                </div>
                <div v-if="isAuthenticated" @click="logout">
                    <router-link class="sign_in" to="/logout">Logout</router-link>
                </div>
                <div v-if="!isAuthenticated && !authLoading">
                    <router-link class="sign_in" to="/account">Sign in</router-link>
                </div>
            </div>
        </div>
    </div>
</template>

<style>
    .header {
        display: table;
        width: 100%;
        height: 60px;
        line-height: 60px;
        background-color: #fff;
        /*box-shadow: 0 0 5px 2px rgba(200, 200, 200, .6);*/
    }
    .header .container {
        display: block;
        width: 1000px;
        height: inherit;
        line-height: inherit;
    }

    /* logo */
    .header .container .logo {
        display: block;
        width: 200px;
        height: inherit;
        line-height: inherit;
        float: left;
    }
    .header .container .logo .link {
        display: block;
        width: 200px;
        height: inherit;
        line-height: inherit;
        float: left;
    }
    .header .container .logo .link a {
        display: block;
        width: 200px;
        height: inherit;
        line-height: inherit;
        float: left;
        text-align: center;
        background: url("/logo.png") no-repeat center;
        background-size: auto 50px;
    }
    .header .container .logo .link a:hover {
        text-decoration: none;
    }

    /* menu */
    .header .container .menu {
        display: block;
        width: auto;
        height: inherit;
        float: left;
    }
    .header .container .menu ul {
        display: block;
        float: left;
        width: auto;
        height: inherit;
        margin: 0;
        padding: 0;
    }
    .header .container .menu ul li {
        display: block;
        float: left;
        width: auto;
        height: inherit;
        line-height: inherit;
    }
    .header .container .menu ul li a {
        display: block;
        float: left;
        width: auto;
        margin: 15px 0;
        height: 30px;
        padding: 0 15px;
        line-height: 30px;
        border-radius: 30px;
        transition: background-color 0.15s ease 0s;
    }
    .header .container .menu ul li a:hover {
        background-color: rgba(0, 0, 0, 0.07);
        text-decoration: none;
        color: #000;
    }

    /* find */
    .header .container .find {
        display: block;
        width: 400px;
        height: inherit;
        line-height: inherit;
        float: left;
        margin: 0;
    }
    .header .container .find input {
        width: 380px;
        height: 40px;
        border: 0;
        border-radius: 20px;
        padding: 0 20px;
        margin: 0 10px;
        background-color: rgba(0, 0, 0, 0.07);
        transition: border-color 0.1s ease-out 0s, border-width, background-color;
    }
    .header .container .find input::placeholder {
        text-align: center;
        color: #000;
        opacity: 0.4;
        transition-delay: 2s;
        transition: width 2s ease-in-out;
        transition-duration: 5s;
    }
    .header .container .find input:focus {
        outline: none;
    }
    .header .container .find input:focus::placeholder {
        text-align: left;
        color: #000;
        opacity: 0.2;
    }

    /* user */
    .header .container .user {
        display: block;
        width: auto;
        height: inherit;
        line-height: inherit;
        float: right;
        margin: 0;
        text-align: center;
    }
    .header .container .user a.sign_in {
        line-height: inherit;
        color: #000;
        border: 2px rgba(0, 0, 0, 0.07)	solid;
        padding: 5px 20px;
        border-radius: 20px;
    }
    .header .container .user a.sign_in:hover {
        text-decoration: none;
        color: rgba(0, 0, 0, 0.9);
        background-color: rgba(0, 0, 0, 0.03);
        border-color: rgba(0, 0, 0, 0.03);
    }
</style>

<script>
    import {mapGetters, mapState} from 'vuex'
    import {AUTH_LOGOUT} from '../../store/actions/auth'

    export default {
        name: 'navigation',
        methods: {
            logout: function () {
                this.$store.dispatch(AUTH_LOGOUT).then(() => this.$router.push('/'))
            }
        },
        computed: {
            ...mapGetters(['getProfile', 'isAuthenticated', 'isProfileLoaded']),
            ...mapState({
                authLoading: state => state.auth.status === 'loading',
            })
        },
    }
</script>
