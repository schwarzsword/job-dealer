<template>
  <div>
    <img v-bind:src="'data:image/jpeg;base64,'+company.fileData" v-if="company.fileData"/>
    <h1>{{ company.name }} <span class="verified" v-if="company.verified === true"></span></h1>
    <div>
      {{company.description}}
    </div>
    <ReviewList :account-id="this.company.accountId" v-if="this.company.accountId"/>
  </div>
</template>

<script>
  import Router from "../../router"
  import {urlPort} from "../../tool";
  import ReviewList from "../Review/ReviewList";

  export default {
    name: 'CompanyPage',
    components: {ReviewList},
    data() {
      return {
        company: {},
        id: Router.currentRoute.params.id,
      }
    },
    beforeCreate() {
      urlPort.get('/companies/' + Router.currentRoute.params.id)
        .then(response => {
          this.company = response.data;
          console.log(this.company)
        })
        .catch(e => {
          this.errors.push(e)
          console.log(e);
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
