<template>
    <div>
        <v-data-table
                :headers="headers"
                :items="desserts"
                :items-per-page="5"
                class="elevation-1"
        ></v-data-table>
    </div>
</template>

<script>
    import axios from "axios";
    import {urlPort} from "../../tool";

    export default {
        name: "vacancies",
        data() {
            return {
                headers: [
                    {
                        text: 'Vacancy',
                        align: 'left',
                        sortable: true,
                        value: 'name',
                    },
                    { text: 'Calories', value: 'calories', sortable: true },
                    { text: 'Fat (g)', value: 'fat' },
                    { text: 'Carbs (g)', value: 'carbs' },
                    { text: 'Protein (g)', value: 'protein' },
                    { text: 'Iron (%)', value: 'iron' },
                ],
                page: 1,
                vacancies: [],
                message: ""
            }
        },
        methods: {},
        created() {
            axios.get(urlPort("/api/vacancies/" + this.page)).then(res => {
                this.vacancies = res.data;
            }).catch(reason => {
                this.message = reason.body;
            })
        }
    }
</script>

<style scoped>

</style>