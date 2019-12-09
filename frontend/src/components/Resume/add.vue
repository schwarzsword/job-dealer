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

          <span class="subheading">Resumes</span>
        </v-card-title>

        <v-card-text>
          <form>

            <v-text-field
                v-model="name"
                :error-messages="nameErrors"
                :counter="50"
                label="Resume name"
                required
                @input="$v.name.$touch()"
                @blur="$v.name.$touch()"
            ></v-text-field>

            <v-text-field
                v-model="salary"
                label="Salary"
            ></v-text-field>

            <v-text-field
                v-model="about"
                label="About me"
            ></v-text-field>

            <v-checkbox
                v-model="checkbox"
                :error-messages="checkboxErrors"
                label="Do you agree?"
                required
                @change="$v.checkbox.$touch()"
                @blur="$v.checkbox.$touch()"
            ></v-checkbox>

            <v-btn class="mr-4 primary" @click="submit">Add resume</v-btn>
          </form>
        </v-card-text>
      </v-card>
    </div>
    <div class="right-sidebar"></div>
  </div>
</template>

<script>
  import { validationMixin } from 'vuelidate'
  import { required, maxLength, email } from 'vuelidate/lib/validators'
  import {urlPort} from "../../tool";

  export default {
    name: 'ResumeAdd',

    mixins: [validationMixin],

    validations: {
      name: { required, maxLength: maxLength(50) },
      email: { required, email },
      select: { required },
      checkbox: {
        checked (val) {
          return val
        },
      },
    },

    data: () => ({
      name: '',
      salary: null,
      about: '',
      skills: [
        'Item 1',
        'Item 2',
        'Item 3',
        'Item 4',
      ],
      checkbox: false,

      resumeData: {
        name: '',
        firstName: '',
        lastName: '',
        salary: '',
        fileData: null,
        about: '',
        skills: [],
        applicantId: '',
        descending: true,
      },
    }),

    computed: {
      checkboxErrors () {
        const errors = []
        if (!this.$v.checkbox.$dirty) return errors
        !this.$v.checkbox.checked && errors.push('You must agree to continue!')
        return errors
      },
      selectErrors () {
        const errors = []
        if (!this.$v.select.$dirty) return errors
        !this.$v.select.required && errors.push('Item is required')
        return errors
      },
      nameErrors () {
        const errors = []
        if (!this.$v.name.$dirty) return errors
        !this.$v.name.maxLength && errors.push('Name must be at most 50 characters long')
        !this.$v.name.required && errors.push('Name is required.')
        return errors
      },
    },

    methods: {
      submit () {
        this.$v.$touch();
        let params = new URLSearchParams({
          'filters': JSON.stringify(this.resumeData)
        });

        params.append('resumeData', JSON.stringify(this.resume));

        urlPort.post('/my/resumes', params, {
          headers: {ContentType: 'multipart/form-data'}
        })
      },
    },

    created() {
    }
  }
</script>
