<template>
    <div class="header">
        <div class="container">
            <div class="logo">
                <div class="link">
                    <router-link to="/"></router-link>
                </div>
            </div>
            <div class="find">
                <form @submit.prevent="handleSubmit" id="search-form">
                    <label>
                        <input @change="handleQuery" autocomplete="off" id="find" name="query" placeholder="Enter job title" type="text"
                               v-model="query" value=""/>
                    </label>
                    <label>
                        <input class="search" id="search" type="submit" value=""/>
                    </label>
                </form>
                <div class="select" id="select">
                    <span class="item hideAction" id="i1">vacancies</span>
                    <a class="item" id="i2">resumes</a>
                </div>
                <div class="selected" id="selected">vacancies</div>
                <div class="histories" id="histories">
                    <div class="sub">
                        <a href="#">Advanced search</a>
                    </div>
                </div>
            </div>

            <div class="menu">
                <router-link title="Vacancies" to="/vacancies">
                    <v-icon class="elevation-2 m-icon i-vacancy" v-ripple></v-icon>
                </router-link>
                <router-link title="Resumes" to="/resumes">
                    <v-icon class="elevation-2 m-icon i-resume" v-ripple></v-icon>
                </router-link>
            </div>

            <!--@click="logout"-->
            <div class="user">
                <div @click="logout" class="profile" id="profile" v-if="isAuthenticated">
                    <v-avatar :size="avatarSize" color="teal">
                        <span class="white--text headline">K</span>
                    </v-avatar>
                    <div class="profile-menu" id="pm">
                    </div>
                </div>
                <div v-if="!isAuthenticated && !authLoading">
                    <router-link class="sign_in" to="/login">Sign in</router-link>
                </div>
            </div>

            <div class="notifications" v-if="isAuthenticated">
                <router-link style="float: right" to="/my/messages">
                    <v-icon color="black" large style="font-size: 32px">mdi-email</v-icon>
                    <v-badge class="m-count" color="green">
                        <span slot="badge">2</span>
                    </v-badge>
                </router-link>
            </div>

            <!--router-link class="sign_in" to="/logout">Logout</router-link-->
        </div>
    </div>
</template>

<script>
    import {mapGetters, mapState} from 'vuex'
    import {AUTH_LOGOUT} from '../../store/actions/auth'

    export default {
        name: 'navigation',

        data: function () {
            return {
                query: '',
                avatarSize: 32,
            }
        },

        methods: {
            logout: function () {
                this.$store.dispatch(AUTH_LOGOUT).then(() => this.$router.push('/'))
            },
            handleQuery() {
                let urlParams = new URLSearchParams(window.location.search);
                urlParams.set("q", this.query);
                if (document.getElementById('search-form').getAttribute('action') === '/resumes') {
                    this.$router.push('/resumes/?' + urlParams.toString());
                } else {
                    this.$router.push('/vacancies/?' + urlParams.toString());
                }
            },
            handleSubmit() {
                if (document.getElementById('search-form').getAttribute('action') === '/resumes') {
                    this.$router.push('/resumes/?q=' + this.query);
                } else {
                    this.$router.push('/vacancies/?q=' + this.query);
                }
            },
            // handleChangeQuery(e) {
            //   this.search.text = e.target.value;
            // }
        },

        computed: {

            ...mapGetters(['getProfile', 'isAuthenticated', 'isUser', 'isCompany', 'profile']),
            ...mapState({
                authLoading: state => state.auth.status === 'loading',
            })
        },

        created() {
            let urlParams = new URLSearchParams(window.location.search);
            this.query = urlParams.get('q');
            let selectFlag = false;

            function closeSelect() {
                document.getElementById('select').style.display = 'none';
                document.getElementById('selected').style.display = 'block';
            }

            window.onload = function () {
                let selected = document.querySelector('#selected');
                let select = document.querySelector('#select');
                let i1 = document.querySelector('#i1');
                let i2 = document.querySelector('#i2');
                let find = document.querySelector('#find');
                let histories = document.querySelector('#histories');
                let search_form = document.querySelector('#search-form');
                let profile = document.querySelector('#profile');
                let pm = document.querySelector('#pm');

                profile.onmouseover = function () {
                    if (pm.style.display === 'none') {
                        pm.style.display = 'block';
                    }
                    console.log(1);
                };

                find.onmouseover = function () {
                    addEventListener('click', function () {
                        histories.style.display = 'block';
                        find.style.backgroundColor = '#fff';
                        closeSelect();
                    });
                };

                find.onmouseleave = function () {
                    addEventListener('click', function () {
                        histories.style.display = 'none';
                        find.style.backgroundColor = '#f3f3f3';
                    })
                };

                selected.onmouseover = function () {
                    select.style.display = 'block';
                    selected.style.display = 'none';

                    select.onmouseout = function () {
                        window.setTimeout(function () {
                            if (selectFlag === false) {
                                closeSelect();
                            }
                        }, 2000);
                        selectFlag = false;
                    };

                    select.onmouseover = function () {
                        selectFlag = true;
                    };

                    function chooseAction() {
                        if (selected.textContent === 'vacancies') {
                            selected.innerText = 'resumes';
                            i1.innerText = 'resumes';
                            i2.innerText = 'vacancies';
                            search_form.setAttribute('action', '/resumes');
                        } else if (selected.textContent === 'resumes') {
                            selected.innerText = 'vacancies';
                            i1.innerText = 'vacancies';
                            i2.innerText = 'resumes';
                            search_form.setAttribute('action', '/vacancies');
                        }
                    }

                    // i1.onclick = function () {
                    //   chooseAction();
                    // };

                    i2.onclick = function () {
                        chooseAction();
                        closeSelect();
                    };

                };
            }
        }
    }
</script>

<style>
    .header {
        display: table;
        width: 100%;
        height: 60px;
        line-height: 60px;
        background-color: #fff;
        box-shadow: 0 0 5px 2px rgba(200, 200, 200, .6);
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

    /* notifications */
    .header .container .notifications {
        display: block;
        min-width: 100px;
        height: inherit;
        float: left;
    }

    /* menu */
    .header .container .menu {
        display: block;
        min-width: 100px;
        height: inherit;
        float: left;
    }

    .header .container .menu .m-icon {
        display: block;
        width: 60px;
        height: 60px;
        float: left;
        line-height: 60px;
        text-align: center;
        font-size: 28px;
        box-shadow: rgba(0, 0, 0, 0.2) 0px 0px 0px 0px,
        rgba(0, 0, 0, 0.14) 0px 0px 0px 0px, rgba(0, 0, 0, 0.12) 0px 0px 0px 0px !important;
    }

    .header .container .menu .m-icon:hover {
        /*background-color: #f5f5f5;*/
    }

    .header .container .menu .i-vacancy {
        background: url("/img/briefcase-search.svg") no-repeat center;
        background-size: 28px;
    }

    .header .container .menu .i-resume {
        background: url("/img/file-account.svg") no-repeat center;
        background-size: 28px;
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

    .header .container .notifications .m-count {
        left: -15px;
        top: -15px;
    }

    .header .container .menu .messages {
        display: block;
        float: right;
        margin: 10px 0;
        padding: 0 10px;
        width: 40px;
        height: 40px;
        background: url("/img/chat.png") no-repeat center;
        background-size: 32px;
    }

    .header .container .menu .messages:hover .count {
        background-color: #ffc369;
        color: #000;
    }

    .header .container .menu .messages .count {
        display: block;
        width: 24px;
        height: 24px;
        background: #ffc369;
        color: #000;
        border-radius: 50%;
        line-height: 24px;
        text-align: center;
        margin: -2px 0 0 -2px;
        font-size: 12px;
        box-shadow: 0 0 10px 0 rgba(0, 0, 0, .2);
    }

    /* find */
    .header .container .find {
        display: block;
        width: 500px;
        height: inherit;
        line-height: inherit;
        font-size: 16px;
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
        font-size: 16px;
        background-color: #f3f3f3;
        transition: border-color 0.1s ease-out 0s, border-width, background-color;
        /*box-shadow: inset 0 0 2px 0 rgba(0, 0, 0, .1);*/
        z-index: 11;
    }

    .header .container .find input::placeholder {
        text-align: left;
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
        margin: -52px 0 0 340px;
        width: 100px;
        height: 80px;
        position: absolute;
        background: white;
        box-shadow: 0 0 5px 1px rgba(0, 0, 0, .1);
        border-radius: 10px;
        color: #777;
        z-index: 12;
    }

    .header .container .find .select .hideAction {
        display: block;
        padding: 0 10px;
        margin: 0;
        width: 100px;
        height: 40px;
        line-height: 40px;
        font-size: 14px;
        opacity: 0.3;
    }

    .header .container .find .select .hideAction:hover {
        cursor: text;
        opacity: 0.3;
    }

    /* search button */
    .header .container .find .search {
        display: block;
        padding: 0;
        margin: -45px 0 0 455px;
        width: 30px;
        height: 30px;
        position: absolute;
        background: url(/img/search.png) no-repeat center;
        background-size: 16px;
        color: #777;
        z-index: 12;
        border-radius: 50%;
    }

    .header .container .find .search:hover {
        cursor: pointer;
        background-color: #e3e3e3;
    }

    .header .container .find .select a.item {
        display: block;
        padding: 0 10px;
        margin: 0;
        width: 100px;
        height: 40px;
        line-height: 40px;
        cursor: pointer;
        font-size: 14px;
        color: #777;
    }

    .header .container .find .select a.item:hover {
        color: #000;
    }

    .header .container .find .selected {
        display: block;
        padding: 0 10px;
        margin: -52px 0 0 340px;
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

    .header .container .find .histories .sub {
        display: table;
        height: 30px;
        font-size: 10px;
        line-height: 30px;
        padding: 30px 0 0 20px;
    }

    /* user */
    .header .container .user {
        display: block;
        width: auto;
        height: inherit;
        line-height: inherit;
        float: right;
        margin: 0;
        padding: 0 20px;
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

    .header .container .user .profile {
        display: table;
    }

    .header .container .user .profile .profile-menu {
        display: none;
        position: absolute;
        width: 100px;
        min-height: 30px;
        background-color: #fff;
        box-shadow: 0 0 5px 1px rgba(0, 0, 0, .1);
        margin: -25px 0 0 -50px;
        border-radius: 3px;
    }

</style>