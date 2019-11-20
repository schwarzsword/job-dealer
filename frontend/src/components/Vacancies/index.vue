<template>
    <v-data-table
            :headers="headers"
            :items="vacancies"
            :items-per-page="5"
            :search="search"
            :sort-by="['name', 'money', 'ownerName']"
            :sort-desc="[false, true]"
            class="elevation-1"
            multi-sort
    >
        <template v-slot:top>
            <v-text-field
                    append-icon="search"
                    hide-details
                    label="Search"
                    single-line
                    v-model="search"
            ></v-text-field>
        </template>


    </v-data-table>
</template>

<script>
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
            urlPort.get("/vacancies/").then(res => {
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