<template>
    <v-stepper v-model="e1">
        <v-stepper-header>
            <v-stepper-step :complete="e1 > 1" step="1">Step 1</v-stepper-step>

            <v-divider></v-divider>

            <v-stepper-step :complete="e1 > 2" step="2">Step 2</v-stepper-step>

        </v-stepper-header>

        <v-stepper-items>
            <v-stepper-content step="1">
                <v-card
                        class="mb-12"
                        color="grey lighten-1"
                        height="350px"
                >
                    <div>Login</div>
                    <v-text-field
                            v-model="email"
                            :rules="emailRules"
                            label="E-mail"
                            required
                            autofocus
                    ></v-text-field>

                    <v-text-field
                            v-model="password"
                            label="Password"
                            required
                            :type="show1 ? 'text' : 'password'"
                            @click:append="show1 = !show1"
                    ></v-text-field>

                    <v-text-field
                            v-model="password_confirmation"
                            label="Confirm password"
                            required
                            :type="show1 ? 'text' : 'password'"
                            @click:append="show2 = !show2"
                    ></v-text-field>

                    <div id="passError" v-if="!!passError">{{passError}}</div>

                    <v-checkbox
                            v-model="isCompany"
                            :label="'Do you want a company account?'"
                    ></v-checkbox>

                </v-card>

                <v-btn
                        color="primary"
                        @click="this.verifyStep1"
                >
                    Continue
                </v-btn>

                <router-link class="sign_in" to="/login">Cancel</router-link>
            </v-stepper-content>

            <v-stepper-content step="2">
                <v-card
                        class="mb-12"
                        color="grey lighten-1"
                        height="350px"
                >
                    <div v-if="isCompany">

                        <v-text-field
                                v-model="company.name"
                                label="Company name"
                                required
                                autofocus
                        ></v-text-field>

                        <v-textarea
                                name="input-7-1"
                                label="Company description"
                                v-model="company.description"
                        ></v-textarea>

                        <v-file-input
                                v-model="company.file"
                                label="Select image file..."
                                accept="image/*"
                                @change="onFileChange"
                        ></v-file-input>
                    </div>
                    <div v-else>

                    </div>
                </v-card>

                <v-btn
                        color="primary"
                        @click="registerCompany"
                >
                    Sign up
                </v-btn>

                <v-btn
                        color="primary"
                        @click="e1 = 1"
                >
                    Back
                </v-btn>

                <router-link class="sign_in" to="/login">Cancel</router-link>
            </v-stepper-content>

        </v-stepper-items>
    </v-stepper>
</template>


<script>

    import {urlPort} from "../../tool";

    export default {
        data() {
            return {
                e1: 0,
                show1: false,
                show2: false,
                imageUrl: null,
                email: "",
                password: "",
                password_confirmation: "",
                isCompany: false,
                passError: "",
                err: "",
                accountId: null,
                company: {
                    name: "",
                    description: "",
                    file: null,
                },
                applicant: {},

                emailRules: [
                    v => !!v || 'E-mail is required',
                    v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
                ],
            }
        },
        methods: {
            onFileChange() {
                let reader = new FileReader();
                reader.onload = () => {
                    this.imageUrl = reader.result
                };
                reader.readAsDataURL(this.company.file)
            },
            verifyStep1: function () {
                if (this.email) {
                    if (this.password === this.password_confirmation) {
                        this.passError = "";
                        this.e1 = 2;
                    } else {
                        this.passError = "Passwords must be similar";
                    }
                }
            },
            registerCompany: function () {
                let params = new URLSearchParams();
                params.append('email', this.email);
                params.append('password', this.password);
                params.append('isCompany', true);
                urlPort.post('/accounts', params)
                    .then(resp => {
                        this.accountId = resp.data.id;
                        let params = new FormData();
                        params.append('name', this.company.name);
                        params.append('description', this.company.description);
                        params.append('file', this.company.file);
                        params.append('accountId', this.accountId);
                        urlPort.post('/companies', params, {headers: {ContentType: 'multipart/form-data'}})
                            .then(resp => {
                                this.$router.push('/login');
                            })
                            .catch(err => {
                                //TODO сделать обработку ошибок
                                console.log(err)
                            })
                    }).catch(err => {
                    //TODO тут тоже
                    this.e1 = 1;
                })
            },
            registerUser: function () {
                let params = new URLSearchParams();
                params.append('email', this.email);
                params.append('password', this.password);
                params.append('isCompany', false);
                urlPort.post('/accounts', params)
                    .then(resp => {
                        this.accountId = resp.data.id;
                        urlPort
                        let params = new FormData();
                        params.append('accountId', this.accountId);
                        urlPort.post('/companies', params, {headers: {ContentType: 'multipart/form-data'}})
                            .then(resp => {
                                this.$router.push('/login');
                            })
                            .catch(err => {
                                //TODO сделать обработку ошибок
                                console.log(err)
                            })
                    }).catch(err => {
                    //TODO тут тоже
                    this.e1 = 1;
                })
            },
        }
    }

</script>

<style scoped>

</style>