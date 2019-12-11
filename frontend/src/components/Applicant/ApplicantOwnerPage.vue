<template>
    <v-app>
        <div> всякое разное, типа мои резюме, активное резюме, крч разберетесь, кто фронт пилит</div>
        <v-data-table
                :headers="headers"
                :items="responses"
                class="elevation-1"
                sort-by="calories"
        >
            <template v-slot:top>
                <v-toolbar color="white" flat>
                    <v-toolbar-title>Responces</v-toolbar-title>
                </v-toolbar>
            </template>

      <template v-slot:item.action="{ item }">

        <v-icon
            @click="route(item)"
        >
          mdi-file-document-box
        </v-icon>
      </template>
      <template v-slot:no-data>
        You have no active responses
      </template>
    </v-data-table>
  </v-app>
</template>

<script>

  import {urlPort} from "../../tool";

  export default {
    name: "applicantOwnerPage",
    data: function () {
      return {
        responses: [],
        headers: [
          {text: 'Vacancy name', value: 'vacancyName',},
          {text: 'Status', value: 'status',},
          {text: 'Company', value: 'vacancyCompanyName',},
          {text: 'Actions', value: 'action', sortable: false},
        ],
      }
    },
    created() {
      urlPort.get("/my/responses/")
        .then(resp => {
          this.responses = resp.data
        });
    },
    methods: {

            route(item) {
                this.$router.push('/vacancies/' + item.vacancyId);
            },
        },
    }
</script>

<style scoped>

</style>