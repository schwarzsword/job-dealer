<template>
    <div>
        <div class="left-sidebar"></div>

        <div class="content">
            <v-card>
                <v-card-text>
                    <v-form
                            lazy-validation
                            ref="form"
                            v-model="valid"
                    >
                        <h2 style="margin-bottom: 20px;">Log in</h2>
                        <v-text-field
                                :rules="emailRules"
                                label="E-mail"
                                required
                                v-model="email"
                        />

                        <v-text-field
                                :type="show1 ? 'text' : 'password'"
                                @click:append="show1 = !show1"
                                label="Password"
                                required
                                v-model="password"
                        />

                        <v-btn
                                :disabled="!valid"
                                @click="this.login"
                                class="mr-4"
                                color="primary"
                        >
                            Login
                        </v-btn>
                        <div v-model="message" class="error--text"/>
                        <div style="margin-top: 20px;">Have no account? Sign up
                            <router-link class="signup" to="/signup">here.</router-link>
                        </div>
                    </v-form>
                </v-card-text>
            </v-card>
        </div>

        <div class="right-sidebar">
        </div>

    </div>

</template>

<script>
    import {AUTH_REQUEST} from "../../store/actions/auth";

    export default {
        name: 'login',
        data() {
            return {
                message: "",
                show1: false,
                valid: true,
                email: '',
                password: '',
                emailRules: [
                    v => !!v || 'E-mail is required',
                    v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
                ],
            }
        },
        methods: {
            login: function () {
                const {email, password} = this;
                this.$store.dispatch(AUTH_REQUEST, {email, password}).then(() => {
                    this.$router.push('/')
                }).catch(err => {
                    this.message = "Invalid login or password";
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