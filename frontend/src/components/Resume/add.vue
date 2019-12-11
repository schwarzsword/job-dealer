<template>
  <div>
    <div class="left-sidebar"></div>
    <div class="content">
      <v-card style="margin-bottom: 20px">
        <v-card-title>
          <router-link to="/resumes">
            <v-btn
                class="mr-6"
                icon
            >
              <v-icon>mdi-chevron-left</v-icon>
            </v-btn>
          </router-link>

          <span class="subheading">Create resume</span>
        </v-card-title>

        <v-card-text>
          <form>

            <v-text-field
                :counter="50"
                @blur="$v.name.$touch()"
                @input="$v.resume.name.$touch()"
                label="Resume name"
                required
                v-model="resume.name"
            />

            <v-text-field
                :counter="50"
                @blur="$v.name.$touch()"
                @input="$v.resume.name.$touch()"
                label="First name"
                required
                v-model="resume.firstName"
            />

            <v-text-field
                :counter="50"
                @blur="$v.name.$touch()"
                @input="$v.resume.name.$touch()"
                label="Last name"
                required
                v-model="resume.lastName"
            />

            <v-text-field
                label="Salary"
                type="number"
                v-model="resume.salary"
            />

            <v-textarea
                label="About me"
                v-model="resume.about"
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

            <v-file-input
                @change="onFileChange"
                accept="image/*"
                label="Select image file..."
                v-model="file"
            />

            <v-checkbox
                :error-messages="checkboxErrors"
                @blur="$v.checkbox.$touch()"
                @change="$v.checkbox.$touch()"
                label="Do you agree?"
                required
                v-model="checkbox"
            />

            <v-btn @click="submit" class="mr-4 primary">Add resume</v-btn>
          </form>
        </v-card-text>
      </v-card>
    </div>
    <div class="right-sidebar"></div>
  </div>
</template>

<script>
  import {validationMixin} from 'vuelidate'
  import {email, maxLength, required} from 'vuelidate/lib/validators'
  import {base64ArrayBuffer, urlPort} from "../../tool";

  export default {
    name: 'ResumeAdd',

    mixins: [validationMixin],

    validations: {
      name: {required, maxLength: maxLength(50)},
      email: {required, email},
      select: {required},
      checkbox: {
        checked(val) {
          return val
        },
      },
    },

    data: () => ({
      file: null,
      fileBytes: null,
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
      onFileChange: function () {

        let reader = new FileReader();
        reader.readAsArrayBuffer(this.file);

        reader.onload = () => {
          this.fileBytes = base64ArrayBuffer(reader.result);
        };


      },

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
