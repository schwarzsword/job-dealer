<template>
  <div>
    <h1>{{ company.name }} <span class="verified" v-if="company.verified === true"></span></h1>
    <div>
      {{company.description}}
    </div>
    <!--Company id: {{ $route.params.id }}-->
  </div>
</template>

<script>
  import axios from 'axios';
  import Router from "../../router"
  import {urlPort} from "../../tool";

  export default {
    name: 'CompanyPage',

    data() {
      // const route = Router.currentRoute.params.id;

      return {
        company: {},
        id: Router.currentRoute.params.id,
      }
    },
    created() {
      axios.get(urlPort('/companies/' +  this.id))
        .then(response => {
          this.company = response.data;
        })
        .catch(e => {
          this.errors.push(e)
        })
    }
  }
</script>

<style>
  .verified {
    position: relative;
    display: inline-block;
    width: 20px;
    height: 20px;
    background: url("/img/verified.png") no-repeat center;
    background-size: 20px;
  }
</style>
