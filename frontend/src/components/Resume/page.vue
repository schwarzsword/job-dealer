<template>
  <div>

    <div class="left-sidebar" style="text-align: center; margin: 10px 0 0 0;">
    </div>

    <div class="content">
      <v-card class="mx-auto">
        <v-card-text>
          <h1>{{ resume.name }}</h1>
          <div>
            {{ resume.salary }}
          </div>
          <div>
            {{ resume.about }}
          </div>
          <div>
            {{ resume.firstName }} {{ resume.lastName }}
          </div>
          <div class="skills" v-for="skill in resume.skills">
            <v-chip outlined>{{ skill }}</v-chip>
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
        resume: {
          name: "Java Developer",
          description: "Desciption of Java Developer"
        }
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
    margin: 10px 0;
  }
</style>
