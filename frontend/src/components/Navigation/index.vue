<template>

    <div class="header">
        <div class="container">
            <div class="logo">
                <div class="link">
                    <router-link to="/"></router-link>
                </div>
            </div>
            <div class="find">
                <input id="find" type="input" name="find" placeholder="Search"/>
                <div id="select" class="select">
                    <span id="vacancies" class="item">vacancies</span>
                    <span id="resumes" class="item">resumes</span>
                </div>
                <div id="selected" class="selected">vacancies</div>
                <div id="histories" class="histories"></div>
            </div>
            <div class="menu">
                <ul>
                    <li><router-link class="nav-link" to="/vacancies">Vacancies</router-link></li>
                    <li><router-link class="nav-link" to="/resumes">Resumes</router-link></li>
                    <li v-if="isUser">
                        <router-link class="sign_in" to="/profile">My profile</router-link>
                    </li>
                    <li v-if="isCompany">
                        <router-link class="sign_in" to="/company">My profile</router-link>

                    </li>
                </ul>
            </div>
            <div class="user">
                <div v-if="isAuthenticated" @click="logout">
                    <router-link class="sign_in" to="/logout">Logout</router-link>
                </div>
                <div v-if="!isAuthenticated && !authLoading">
                    <router-link class="sign_in" to="/login">Sign in</router-link>
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
        width: 1200px;
        height: inherit;
        line-height: inherit;
        margin: 0 auto;
        padding: 0;
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
        width: 500px;
        height: inherit;
        line-height: inherit;
        float: left;
        margin: 0;
        position: relative;
        z-index: 11;
    }

    .header .container .find input {
        position: relative;
        width: 480px;
        height: 40px;
        border: 0;
        border-radius: 20px;
        padding: 0 20px;
        margin: 0 10px;
        background-color: #eaeef1;
        transition: border-color 0.1s ease-out 0s, border-width, background-color;
        box-shadow: inset 0 0 2px 0 rgba(0, 0, 0, .1);
        z-index: 11;
    }

    .header .container .find input::placeholder {
        text-align: center;
        color: #000;
        opacity: 0.4;
        transition-delay: 2s;
        transition: width 2s ease-in-out;
        transition-duration: 5s;
        font-size: 16px;
    }

    .header .container .find input:focus {
        outline: none;
    }

    .header .container .find input:focus::placeholder {
        text-align: left;
        color: #000;
        opacity: 0.2;
    }

    .header .container .find .select {
        display: none;
        padding: 0;
        margin: -50px 0 0 400px;
        width: 100px;
        height: 80px;
        position: absolute;
        background: white;
        box-shadow: 0 0 5px 1px rgba(0, 0, 0, .1);
        border-radius: 10px;
        color: #777;
        z-index: 12;
    }

    .header .container .find .select span.item {
        display: block;
        padding: 0 10px;
        margin: 0;
        width: 100px;
        height: 40px;
        line-height: 40px;
        cursor: pointer;
        font-size: 14px;
    }

    .header .container .find .select span.item:hover {
        color: #000;
    }

    .header .container .find .selected {
        display: block;
        padding: 0 10px;
        margin: -50px 0 0 400px;
        width: 100px;
        height: 40px;
        line-height: 40px;
        position: absolute;
        border-radius: 10px;
        color: #999;
        cursor: pointer;
        font-size: 14px;
        z-index: 12;
    }

    .header .container .find .histories {
        display: none;
        margin: -40px 0 0 10px;
        width: 480px;
        height: 65px;
        background: #fff;
        box-shadow: 0 0 5px 1px rgba(0, 0, 0, .1);
        border-radius: 10px;
        position: relative;
        z-index: 10;
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
        color: rgba(0, 0, 0, 0.6);
        border: 2px rgba(0, 0, 0, 0.2) solid;
        padding: 5px 20px;
        border-radius: 20px;
    }

    .header .container .user a.sign_in:hover {
        text-decoration: none;
        color: rgba(0, 0, 0, 0.9);
        background-color: rgba(0, 0, 0, 0.03);
        border: 2px rgba(0, 0, 0, 0.2) solid;
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

            ...mapGetters(['getProfile', 'isAuthenticated', 'isUser', 'isCompany', 'profile']),
            ...mapState({
                authLoading: state => state.auth.status === 'loading',
            })
        },
    }

    let selectFlag = false;

    function closeSelect() {
        document.getElementById('select').style.display = 'none';
        document.getElementById('selected').style.display = 'block';
    }

    window.onload = function () {
        let selected = document.querySelector('#selected');
        let select = document.querySelector('#select');
        let vacancies = document.querySelector('#vacancies');
        let resumes = document.querySelector('#resumes');
        let find = document.querySelector('#find');
        let histories = document.querySelector('#histories');

        find.onmouseover = function (e) {
            addEventListener('click', function (e) {
                histories.style.display = 'block';
                find.style.backgroundColor = '#fff';
                closeSelect();
            });
        };

        find.onmouseleave = function (e) {
            addEventListener('click', function (e) {
                histories.style.display = 'none';
                find.style.backgroundColor = '#eaeef1';
            })
        };

        selected.onmouseover = function (e) {
            select.style.display = 'block';
            selected.style.display = 'none';

            select.onmouseout = function (e) {
                window.setTimeout(function (e) {
                    if (selectFlag === false) {
                        closeSelect();
                    }
                }, 2000);
                selectFlag = false;
            };

            select.onmouseover = function (e) {
                selectFlag = true;
            };

            vacancies.onclick = function (e) {
                selected.innerText = 'vacancies';
            };

            resumes.onclick = function (e) {
                selected.innerText = 'resumes';
                closeSelect();
            };

        };
    };
</script>
