<template>
    <v-app>
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
                                :rules="rules.emailRules"
                                autofocus
                                label="E-mail"
                                required
                                v-model="account.email"
                        ></v-text-field>

                        <div class="error--text" v-if="errors.step1Error">{{errors.step1Error}}</div>

                        <v-text-field
                                :type="show1 ? 'text' : 'password'"
                                @click:append="show1 = !show1"
                                label="Password"
                                required
                                v-model="account.password"
                        ></v-text-field>

                        <v-text-field
                                :type="show1 ? 'text' : 'password'"
                                @click:append="show2 = !show2"
                                label="Confirm password"
                                required
                                v-model="account.password_confirmation"
                                :rules="rules.confirmPassRules"
                        ></v-text-field>

                        <v-checkbox
                                :label="'Do you want a company account?'"
                                v-model="account.isCompany"
                        ></v-checkbox>

                    </v-card>

                    <v-btn
                            @click="this.next"
                            color="primary"
                            :disabled="!(account.email && account.password && account.password_confirmation)"
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
                            v-if="account.isCompany"
                    >
                        <v-text-field
                                autofocus
                                label="Company name"
                                :rules="rules.requiredRules"
                                v-model="company.name"
                        />
                        <div class="error--text" v-if="errors.companyNameError">{{errors.companyNameError}}</div>
                        <v-textarea
                                label="Company description"
                                name="input-7-1"
                                :rules="rules.requiredRules"
                                v-model="company.description"
                        />

                        <v-file-input
                                @change="onFileChange"
                                accept="image/*"
                                label="Select image file..."
                                v-model="file"
                        />

                        <v-btn
                                @click="registerCompany"
                                color="primary"
                        >
                            Sign up
                        </v-btn>

                        <v-btn
                                @click="e1 = 1"
                                color="primary"
                        >
                            Back
                        </v-btn>

                        <router-link class="sign_in" to="/login">Cancel</router-link>
                    </v-card>
                    <v-card class="mb-12"
                            color="grey lighten-1"
                            height="650px"
                            v-else>
                        <v-text-field
                                autofocus
                                label="Vacancy you apply"
                                :rules="rules.requiredRules"
                                v-model="resume.name"
                        />
                        <v-text-field
                                autofocus
                                label="Your name"
                                :rules="rules.requiredRules"
                                v-model="resume.firstName"
                        />
                        <v-text-field
                                autofocus
                                label="Your last name"
                                :rules="rules.requiredRules"
                                v-model="resume.lastName"
                        />
                        <v-text-field
                                type="number"
                                :rules="rules.salaryRules"
                                autofocus
                                label="Salary you wish"
                                v-model="resume.salary"
                        />
                        <v-textarea
                                label="Something about you"
                                name="input-7-1"
                                :rules="rules.requiredRules"
                                v-model="resume.about"
                        />
                        <v-file-input
                                @change="onFileChange"
                                accept="image/*"
                                label="Select image file..."
                                v-model="file"
                        />
                        <v-combobox
                                :items="skills"
                                chips
                                clearable
                                label="Your skills"
                                multiple
                                v-model="resume.skills"
                        >
                            <template v-slot:selection="{ attrs, item, select, selected }">
                                <v-chip
                                        :input-value="selected"
                                        @click="select"
                                        @click:close="remove(item)"
                                        close
                                        v-bind="attrs"
                                >
                                    <strong>{{ item }}</strong>&nbsp;
                                </v-chip>
                            </template>
                        </v-combobox>
                        <v-btn
                                @click="registerUser"
                                color="primary"
                        >
                            Sign up
                        </v-btn>

                        <v-btn
                                @click="e1 = 1"
                                color="primary"
                        >
                            Back
                        </v-btn>

                        <router-link class="sign_in" to="/login">Cancel</router-link>
                    </v-card>

                </v-stepper-content>

            </v-stepper-items>
        </v-stepper>
    </v-app>
</template>


<script>

    import {base64ArrayBuffer, urlPort} from "../../tool";
    import {AUTH_REQUEST} from "../../store/actions/auth";

    export default {
        data() {
            return {
                e1: 0,
                file: null,
                fileBytes: null,
                show1: false,
                show2: false,
                errors: {
                    step1Error: "",
                    companyNameError: "",
                },
                company: {
                    name: "",
                    description: "",
                    fileData: null,
                    accountId: null,
                },
                account: {
                    email: "",
                    password: "",
                    password_confirmation: "",
                    isCompany: false,
                },
                resume: {
                    name: "",
                    firstName: "",
                    lastName: "",
                    salary: "",
                    about: "",
                    skills: [],
                    applicantId: null,
                    fileData: null
                },
                skills: [],
                rules: {
                    emailRules: [
                        v => !!v || 'E-mail is required',
                        v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
                    ],
                    salaryRules: [
                        v => /[0-9]/.test(v) || 'It should be a number',
                    ],
                    requiredRules: [
                        v => !!v || 'Field is required',
                    ],
                    confirmPassRules: [
                        v => v === this.account.password || 'Passwords must be similar'
                    ]
                },
            }
        },
        methods: {
            onFileChange: function () {

                let reader = new FileReader();
                reader.readAsArrayBuffer(this.file);

                reader.onload = () => {
                    this.fileBytes = base64ArrayBuffer(reader.result)
                };


            },
            remove(item) {
                this.resume.skills.splice(this.resume.skills.indexOf(item), 1);
                this.resume.skills = [...this.resume.skills]
            },
            next: function () {
                this.e1 = 2
            },
            registerCompany: function () {
                let params = new URLSearchParams();
                params.append('email', this.account.email);
                params.append('password', this.account.password);
                params.append('isCompany', this.account.isCompany);
                urlPort.post('/accounts', params)
                    .then(resp => {
                        this.company.accountId = resp.data.id;
                        this.company.fileData = this.fileBytes;
                        let params = new FormData();
                        params.append('companyData', JSON.stringify(this.company));
                        urlPort.post('/companies', params, {headers: {ContentType: 'multipart/form-data'}})
                            .then(resp => {
                                const {email, password} = this.account;
                                this.$store.dispatch(AUTH_REQUEST, {email, password}).then(() => {
                                    this.$router.push('/');
                                });
                            })
                            .catch(err => {
                                this.errors.step1Error = "This email already in use";
                            })
                    }).catch(err => {
                    this.errors.step1Error = "This email already in use";
                    this.e1 = 1;
                })
            },
            registerUser: function () {
                let params = new URLSearchParams();
                params.append('email', this.account.email);
                params.append('password', this.account.password);
                params.append('isCompany', this.account.isCompany);
                urlPort.post('/accounts', params)
                    .then(resp => {
                        let params = new URLSearchParams();
                        params.append('accountId', resp.data.id);
                        urlPort.post('/applicants', params)
                            .then(resp => {
                                this.resume.applicantId = resp.data.id;
                                const {email, password} = this.account;
                                this.$store.dispatch(AUTH_REQUEST, {email, password}).then(() => {
                                });
                                this.resume.fileData = this.fileBytes;
                                let params = new FormData();
                                params.append('resumeData', JSON.stringify(this.resume));
                                urlPort.post('/my/resumes', params, {headers: {ContentType: 'multipart/form-data'}})
                                    .then(resp => {
                                        this.$router.push('/');
                                    })
                                    .catch(err => {
                                        //TODO добавить обработку ошибок
                                    })
                            })
                            .catch(err => {
                                this.errors.step1Error = "This email already in use";
                                this.e1 = 1;
                            })
                    }).catch(err => {
                    this.errors.step1Error = "This email already in use";
                    this.e1 = 1;
                })
            },
        },
        created() {
            urlPort.get('/skills')
                .then(resp => {
                    this.skills = resp.data;
                })
        }
    }

</script>

<style scoped>

</style>