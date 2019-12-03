<template>
    <v-app>
        <v-card>
            {{vacancy.name}}
            {{vacancy.description}}
            {{vacancy.money}}
            {{vacancy.ownerName}}
            <ul>
                <li v-for="skill in vacancy.requestedSkills">
                    {{ skill }}
                </li>
            </ul>
        </v-card>
        <v-card v-if="task.name">
            {{task.name}}
            {{task.description}}
        </v-card>

        <v-data-table
                :headers="headers"
                :items="responses"
                sort-by="calories"
                class="elevation-1"
        >
            <template v-slot:top>
                <v-toolbar flat color="white">
                    <v-toolbar-title>Responces</v-toolbar-title>
                </v-toolbar>
            </template>
            <template v-slot:item.action="{ item }">

                <v-icon v-if="item.status === 'APPLIED'"
                        @click="accept(item)"
                >
                    mdi-check-outline
                </v-icon>
                <!--                <v-icon v-if=""-->
                <!--                        @click="accept(item)"-->
                <!--                >-->
                <!--                    mdi-arrow-compress-down-->
                <!--                </v-icon>-->
                <v-icon
                        v-if="item.status === 'APPLIED'"
                        @click="reject(item)"
                >
                    mdi-close-outline
                </v-icon>
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

    import Router from "../../router"
    import {urlPort} from "../../tool";

    export default {
        name: "companyVacancyPage",
        data: function () {
            return {
                vacancy: {
                    id: Router.currentRoute.params.id,
                    name: "",
                    description: "",
                    money: null,
                    ownerName: "",
                    requestedSkills: [],
                },
                task: {
                    name: "",
                    description: "",
                },
                responses: [],
                headers: [
                    {text: 'Applicant name', value: 'applicantName',},
                    {text: 'Status', value: 'status',},
                    {text: 'Actions', value: 'action', sortable: false},
                ],
            }
        },
        created() {
            urlPort.get("/vacancies/" + this.vacancy.id)
                .then(resp => {
                    this.vacancy = resp.data;
                    urlPort.get("/my/vacancies/" + this.vacancy.id)
                        .then(resp => {
                            this.responses = resp.data
                        });
                    if (this.vacancy.withTask) {
                        urlPort.get("/vacancies/" + this.vacancy.id + "/task")
                            .then(resp => {
                                this.task = resp.data
                            });

                    }
                });
        },
        methods: {
            accept(item) {
                item.status = "INVITED";
                this.setStatus(item);
            },
            reject(item) {
                item.status = "REJECTED";
                this.setStatus(item);
            },
            route(item) {
                //todo сделать линкк на резюме пользователя
            },
            setStatus(item) {
                let params = new URLSearchParams();
                params.append("status", item.status);
                console.log(params.get("status"));
                urlPort.post("/responses/" + item.id, params)
            }
        },
    }
</script>

<style scoped>

</style>