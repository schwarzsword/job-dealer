<template>
    <v-app>
        <div class="success" v-if="isApplied">{{successMessage}}</div>
        <v-card>
            {{vacancy.name}}
            {{vacancy.money}}
            {{vacancy.ownerName}}
            <ul>
                <li v-for="skill in vacancy.requestedSkills">
                    {{ skill }}
                </li>
            </ul>
            <v-btn v-if="!task.name && !isApplied" @click="apply">
                Apply
            </v-btn>
        </v-card>
        <v-card v-if="task.name">
            {{task.name}}
            {{task.description}}
        </v-card>
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
                    withTask: null,
                },
                task: {
                    name: "",
                    description: "",
                },
                submission: null,
                successMessage: "You applied for this job",
                isApplied: false,
            }
        },
        created() {
            urlPort.get("/vacancies/" + this.vacancy.id)
                .then(resp => {
                    this.vacancy = resp.data;
                    if(this.vacancy.withTask) {
                        urlPort.get("/vacancies/" + this.vacancy.id + "/task")
                            .then(resp => {
                                this.task = resp.data
                            });
                    }
                });

            this.checkApplied();


        },
        methods: {
            apply() {
                urlPort.post("/vacancies/" + this.vacancy.id).then(resp => {
                    this.checkApplied();
                })

            },
            checkApplied() {
                urlPort.get("/vacancies/" + this.vacancy.id + "/isApplied")
                    .then(resp => {
                        this.isApplied = resp.data
                    });
            }
        }
    }
</script>

<style scoped>

</style>