<template>
    <div class="navigation">
        <ul>
            <li>
                <router-link class="brand" to="/">
                    <img src="../../../public/logo.png" width="200px"/>
                </router-link>
            </li>
        </ul>
        <div class="menu">
            <ul>
                <li>
                    <router-link class="nav-link" to="/rules">Rules</router-link>
                </li>
                <li>
                    <router-link class="nav-link" to="/resumes">Resumes</router-link>
                </li>
                <li v-if="isProfileLoaded">
                    <router-link to="/profile">MyProfile</router-link>
                </li>
                <li v-if="isAuthenticated" @click="logout">
                    <span class="logout">Logout</span>
                </li>
                <li v-if="!isAuthenticated && !authLoading">
                    <router-link to="/login">Login</router-link>
                </li>
            </ul>
        </div>
    </div>
</template>

<style lang="scss" scoped>
    a {
        text-decoration: none;
    }

    .navigation {
        display: flex;
        align-items: center;
        padding: 5px;

        ul {
            display: flex;

            &:first-child {
                flex-grow: 1;
            }

            li {
                padding-right: 1em;
            }
        }

        border: #4e555b;
        border-radius: 10px;
        border-width: 2px;
    }

    .brand {
        display: flex;
        align-items: center;
    }

    .logout {
        &:hover {
            cursor: pointer;
        }
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