<template>
    <v-app>
        {{vacancy.name}}
        {{vacancy.money}}
        {{vacancy.ownerName}}
        <ul>
            <li v-for="skill in vacancy.requestedSkills">
                {{ skill }}
            </li>
        </ul>
    </v-app>

</template>

<script>
    import Router from "../../router"
    import {urlPort} from "../../tool";

    export default {
        name: "vacancyPage",
        data: function () {
            return {
                vacancy: {
                    id: Router.currentRoute.params.id,
                    name: "",
                    money: null,
                    ownerName: "",
                    requestedSkills: [],
                },
                testTask: {},
                submission: null,
            }
        },
        created() {
            urlPort.get("/vacancies/" + this.vacancy.id)
                .then(resp => {
                    this.vacancy = resp.data
                })
        }
    }
</script>

<style scoped>

</style>