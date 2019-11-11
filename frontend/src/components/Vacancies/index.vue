<template>
            <v-data-table
                    multi-sort
                    :sort-by="['name', 'money', 'ownerName']"
                    :sort-desc="[false, true]"
                    :headers="headers"
                    :items="vacancies"
                    :items-per-page="5"
                    class="elevation-1"
                    :search="search"
            >
                <template v-slot:top>
                    <v-text-field
                            v-model="search"
                            append-icon="search"
                            label="Search"
                            single-line
                            hide-details
                    ></v-text-field>
                </template>



            </v-data-table>
</template>

<script>
    import axios from "axios";
    import {urlPort} from "../../tool";

    export default {
        name: "vacancies",
        data: function () {
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
                    // {text: 'more', }
                ],
                page: 1,
                vacancies: [],
                message: ""
            }
        },
        methods: {
            more(item) {
                console.log(item)
            }
        },
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