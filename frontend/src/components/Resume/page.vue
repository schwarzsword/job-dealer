<template>
  <div>
    <div class="left-sidebar" style="text-align: center; margin: 10px 0 0 0;">
    </div>
    <div class="content">
      <v-card class="mx-auto">
        <v-card-title>
          <router-link to="/resumes">
            <v-btn icon class="mr-6">
              <v-icon>mdi-chevron-left</v-icon>
            </v-btn>
          </router-link>

          <span class="subheading">{{ resume.name }}</span>
        </v-card-title>


        <v-card-text>

          <router-link class="invite" to="">Send invite</router-link>

          <div class="salary">
            {{ resume.salary }}
          </div>
          <div>
            {{ resume.about }}
          </div>
          <div>
            {{ resume.firstName }} {{ resume.lastName }}
          </div>

          <div class="skills">
            <div class="skill" v-for="skill in resume.skills">
              <v-chip outlined>
                {{ skill }}
              </v-chip>
            </div>
          </div>

        </v-card-text>
      </v-card>
    </div>
    <div class="right-sidebar">
    </div>
  </div>
</template>

<script>
  import Router from "../../router"
  import {urlPort} from "../../tool";

  export default {
    name: 'ResumePage',
    data() {
      return {
        id: Router.currentRoute.params.id,
        status: "",
        resume: []
      }
    },
    created() {
      urlPort.get('/resumes/' + this.id)
        .then(resp => {
          this.resume = resp.data;
          status = resp.status.toString();
          console.log(status);
          console.log(this.resume);
        }).catch(function (error) {
        if (error.response) {
          status = error.response.status.toString();
        }
      });
    }
  }
</script>

<style>
  .skills {
    display: table;
    margin: 20px 0;
  }
  .skill {
    display: block;
    float: left;
    margin: 0 5px 0 0;
  }
  .salary {
    display: table;
    margin: 10px 0;
  }
</style>
