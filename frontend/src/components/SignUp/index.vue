<template>
    <div>
        <h4>Register</h4>
        <form @submit.prevent="register">
            <label for="email">E-Mail Address</label>
            <div>
                <input id="email" type="email" v-model="email" required>
            </div>
            <label for="password">Password</label>
            <div>
                <input id="password" type="password" v-model="password" required @keyup="verifyPass">
            </div>
            <label for="password-confirm">Confirm Password</label>
            <div>
                <input id="password-confirm" type="password" v-model="password_confirmation" required
                       @keyup="verifyPass">
            </div>
            <div id="passError" v-if="passError">{{passError}}</div>
            <label for="isCompany">Do you want a company account?</label>
            <input id="isCompany" type="checkbox" v-model="isCompany">
            <div>
                <button type="submit">Register</button>
            </div>
        </form>
        <p id="errMessage" v-if="err">{{err}}</p>

    </div>
</template>


<script>
    import {REG_REQUEST} from "../../store/actions/register";

    export default {
        data() {
            return {
                name: "",
                email: "",
                password: "",
                password_confirmation: "",
                isCompany: false,
                passError: "",
                err: "",
            }
        },
        methods: {
            verifyPass: function () {
                if (this.password !== this.password_confirmation) this.passError = "Passwords must be similar";
                else this.passError = "";
            },
            register: function () {
                const {email, password, isCompany} = this;
                this.$store.dispatch(REG_REQUEST, {email, password, isCompany}).then(() => {
                    this.$router.push('/login')
                }).catch(err => {
                    this.err = err
                })
            }
        },
    }

</script>

<style scoped>

</style>