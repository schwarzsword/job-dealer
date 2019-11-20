<template>
    <v-form
            lazy-validation
            ref="form"
            v-model="valid"
    >
        <div>Login</div>
        <v-text-field
                :rules="emailRules"
                label="E-mail"
                required
                v-model="username"
        ></v-text-field>

        <v-text-field
                :type="show1 ? 'text' : 'password'"
                @click:append="show1 = !show1"
                label="Password"
                required
                v-model="password"
        ></v-text-field>

        <v-btn
                :disabled="!valid"
                @click="this.login"
                class="mr-4"
                color="green"
        >
            Login
        </v-btn>
        <div>Have no account? Sign up
            <router-link class="signup" to="/signup">here</router-link>
        </div>
    </v-form>

</template>

<script>
    import {AUTH_REQUEST} from "../../store/actions/auth";

    export default {
        name: 'login',
        data() {
            return {
                show1: false,
                valid: true,
                username: '',
                password: '',
                emailRules: [
                    v => !!v || 'E-mail is required',
                    v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
                ],
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
        width: 500px;
        padding: 10px;
    }
</style>