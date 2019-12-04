<template>
  <div>
    1
    <h1>{{ resume.name }}</h1>
    <div>
      {{ resume.description }}
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
        }).catch(function (error) {
          if (error.response) {
            status = error.response.status.toString();
          }
      });
    }
  }
</script>

<style>
</style>
