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
                                v-model="email"
                        ></v-text-field>

                        <v-text-field
                                :type="show1 ? 'text' : 'password'"
                                @click:append="show1 = !show1"
                                label="Password"
                                required
                                v-model="password"
                        ></v-text-field>

                        <v-text-field
                                :type="show1 ? 'text' : 'password'"
                                @click:append="show2 = !show2"
                                label="Confirm password"
                                required
                                v-model="password_confirmation"
                        ></v-text-field>

                        <div id="passError" v-if="!!passError">{{passError}}</div>

                        <v-checkbox
                                :label="'Do you want a company account?'"
                                v-model="isCompany"
                        ></v-checkbox>

                    </v-card>

                    <v-btn
                            @click="this.verifyStep1"
                            color="primary"
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
                            v-if="isCompany"
                    >
                        <v-text-field
                                autofocus
                                label="Company name"
                                :rules="rules.requiredRules"
                                v-model="company.name"
                        ></v-text-field>

                        <v-textarea
                                label="Company description"
                                name="input-7-1"
                                :rules="rules.requiredRules"
                                v-model="company.description"
                        ></v-textarea>

                        <v-file-input
                                @change="onFileChange"
                                accept="image/*"
                                label="Select image file..."
                                v-model="file"
                        ></v-file-input>

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
                                v-model="resume.resumeName"
                        ></v-text-field>
                        <v-text-field
                                autofocus
                                label="Your name"
                                :rules="rules.requiredRules"
                                v-model="resume.firstName"
                        ></v-text-field>
                        <v-text-field
                                autofocus
                                label="Your last name"
                                :rules="rules.requiredRules"
                                v-model="resume.lastName"
                        ></v-text-field>
                        <v-text-field
                                :rules="rules.salaryRules"
                                autofocus
                                label="Salary you wish"
                                v-model="resume.salary"
                        ></v-text-field>
                        <v-textarea
                                label="Something about you"
                                name="input-7-1"
                                :rules="rules.requiredRules"
                                v-model="resume.about"
                        ></v-textarea>
                        <v-file-input
                                @change="onFileChange"
                                accept="image/*"
                                label="Select image file..."
                                v-model="file"
                        ></v-file-input>
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

    import {urlPort} from "../../tool";

    export default {
        data() {
            return {
                e1: 0,
                file: null,
                fileBytes: null,
                show1: false,
                show2: false,
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
                },
                applicantId: null,
                resume: {
                    resumeName: "",
                    firstName: "",
                    lastName: "",
                    salary: "",
                    about: "",
                    skills: []
                },
                skills: ['test', "qwe"],
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
                    ]
                },
            }
        },
        methods: {
            onFileChange() {

                let reader = new FileReader();
                reader.readAsArrayBuffer(this.file);
                reader.onload = () => {
                    this.fileBytes = new Uint8Array(reader.result);
                };


            },
            remove(item) {
                this.resume.skills.splice(this.resume.skills.indexOf(item), 1);
                this.resume.skills = [...this.resume.skills]
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
                        params.append('fileData', this.fileBytes);
                        params.append('accountId', this.accountId);
                        urlPort.post('/companies', params, {headers: {ContentType: 'multipart/form-data'}})
                            .then(resp => {
                                console.log(resp.data);
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
                        let params = new URLSearchParams();
                        params.append('accountId', this.accountId);
                        urlPort.post('/applicants', params)
                            .then(resp => {
                                this.applicantId = resp.data.id;
                                let params = new FormData();
                                params.append('applicantId', this.applicantId);
                                params.append('resumeName', this.resume.resumeName);
                                params.append('firstName', this.resume.firstName);
                                params.append('lastName', this.resume.lastName);
                                params.append('salary', this.resume.salary);
                                params.append('about', this.resume.about);
                                params.append('fileData', this.fileBytes);
                                params.append('skills', this.resume.skills);
                                urlPort.post('/resumes', params, {headers: {ContentType: 'multipart/form-data'}})
                                    .then(resp => {
                                        this.$router.push('/login')
                                    })
                                    .catch(err => {
                                        //TODO добавить обработку ошибок
                                    })
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