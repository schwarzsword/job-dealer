<template>
    <div>
        <form class="login" @submit.prevent="login">
            <h1>Sign in</h1>

            <label>
                <input required v-model="username" type="text" placeholder="Email"/>
            </label>
            <br/>
            <label>
                <input required v-model="password" type="password" placeholder="Password"/>
            </label>
            <br/>
            <button type="submit">Login</button>
        </form>
        <div>Have no account? Sign up
            <router-link class="signup" to="/signup">here</router-link>
        </div>
    </div>
</template>

<script>
    import {AUTH_REQUEST} from "../../store/actions/auth";

    export default {
        name: 'login',
        data() {
            return {
                username: '',
                password: '',
            }
        },
        methods: {
            login: function () {
                const {username, password} = this;
                this.$store.dispatch(AUTH_REQUEST, {username, password}).then(() => {
                    this.$router.push('/')
                })
            }
        },
    }
</script>

<style scoped>
    .login {
        display: flex;
        flex-direction: column;
        width: 300px;
        padding: 10px;
    }
</style>