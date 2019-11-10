<template>
    <v-app id="vacancies">
        <v-card>
            <v-card-title>
                Vacancy
                <v-spacer></v-spacer>
                <v-text-field
                        v-model="search"
                        append-icon="search"
                        label="Search"
                        single-line
                        hide-details
                ></v-text-field>
            </v-card-title>

            <v-data-table
                    multi-sort
                    :sort-by="['name', 'money', 'ownerName']"
                    :sort-desc="[false, true]"
                    :headers="headers"
                    :items="vacancies"
                    :items-per-page="5"
                    class="elevation-1"
                    :search="search"
            ></v-data-table>
        </v-card>
    </v-app>
</template>

<script>
    import axios from "axios";
    import {urlPort} from "../../tool";

    export default {
        name: "vacancies",
        data() {
            return {
                search: '',
                headers: [
                    {
                        text: 'Vacancy',
                        align: 'left',
                        sortable: true,
                        value: 'name',
                    },
                    {text: 'Salary', value: 'money', sortable: true},
                    {text: 'Company', value: 'ownerName', sortable: true},
                ],
                page: 1,
                vacancies: [],
                message: ""
            }
        },
        methods: {},
        created() {
            axios.get(urlPort("/api/vacancies/")).then(res => {
                this.vacancies = res.data;
                console.log(res.data);
            }).catch(reason => {
                this.message = reason.body;
            })
        }
    }
</script>

<style scoped>

</style>