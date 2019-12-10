<template>
    <div>
        <div class="left-sidebar"></div>
        <div class="content">
            <v-card outlined>

                <v-card-title>
                    <router-link to="/resumes">
                        <v-btn
                                icon
                                class="mr-6"
                        >
                            <v-icon>mdi-chevron-left</v-icon>
                        </v-btn>
                    </router-link>

                    <span class="subheading">My resume</span>
                </v-card-title>

                <v-card-text>
                    <form>

                        <v-text-field
                            v-model="resume.name"
                            :counter="50"
                            label="Resume name"
                        />

                        <v-text-field
                            v-model="resume.firstName"
                            :counter="50"
                            label="First name"
                        />

                        <v-text-field
                            v-model="resume.lastName"
                            :counter="50"
                            label="Last name"
                        />

                        <v-text-field
                                v-model="resume.salary"
                                type="number"
                                label="Salary"
                        />

                        <v-textarea
                                v-model="resume.about"
                                label="About me"
                        />

                        <v-checkbox
                                v-model="checkbox"
                                label="Do you agree?"
                                required
                                @change="$v.checkbox.$touch()"
                                @blur="$v.checkbox.$touch()"
                        />

                        <v-btn class="mr-4 primary" @click="submit">Add resume</v-btn>
                    </form>
                </v-card-text>
            </v-card>
        </div>
        <div class="right-sidebar"></div>
    </div>
</template>

<script>
    import {validationMixin} from 'vuelidate'
    // import {email, maxLength, required} from 'vuelidate/lib/validators'
    import {urlPort} from "../../tool";

    export default {
        name: 'ResumeAdd',

        mixins: [validationMixin],

        validations: {
            // name: {required, maxLength: maxLength(50)},
            // email: {required, email},
            // select: {required},
            checkbox: {
                checked(val) {
                    return val
                },
            },
        },

        data: () => ({
            checkbox: false,
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
        }),

        computed: {
            // checkboxErrors() {
            //     const errors = [];
            //     if (!this.$v.checkbox.$dirty) return errors;
            //     !this.$v.checkbox.checked && errors.push('You must agree to continue!');
            //     return errors
            // },
            // selectErrors() {
            //     const errors = [];
            //     if (!this.$v.select.$dirty) return errors;
            //     !this.$v.select.required && errors.push('Item is required');
            //     return errors
            // },
            // nameErrors() {
            //     const errors = [];
            //     if (!this.$v.resume.name.$dirty) return errors;
            //     !this.$v.resume.name.maxLength && errors.push('Name must be at most 50 characters long');
            //     !this.$v.resume.name.required && errors.push('Name is required.');
            //     return errors
            // },
        },

        methods: {
            submit() {
                this.$v.$touch();
                urlPort.get('/who').then(resp => {
                    this.resume.applicantId = resp.data;
                    let params = new URLSearchParams();
                    params.append('resumeData', JSON.stringify(this.resume));

                    urlPort.post('/my/resumes', params, {
                        headers: {ContentType: 'multipart/form-data'}
                    })
                })
            },
        },

        created() {
        }
    }
</script>
