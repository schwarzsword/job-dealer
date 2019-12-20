<template>
    <div>
        <div>
            <div class="left-sidebar" style="text-align: center; margin: 10px 0 0 0;">
            </div>
            <div class="content">
                <div class="success" v-if="isApplied">{{successMessage}}</div>
                <v-card style="padding: 10px;">
                    <v-card-title>
                        <router-link to="/vacancies">
                            <v-btn class="mr-6" icon>
                                <v-icon>mdi-chevron-left</v-icon>
                            </v-btn>
                        </router-link>

                        <span class="subheading">{{vacancy.name}}</span>
                    </v-card-title>
                    <v-card-text>
                        <div>{{vacancy.money}}</div>
                        <div>{{vacancy.ownerName}}</div>
                    </v-card-text>
                    <ul>
                        <li v-for="skill in vacancy.requestedSkills">
                            {{ skill }}
                        </li>
                    </ul>

                </v-card>
                <v-card v-if="task.name" style="margin: 10px auto">
                    <v-card-title>
                        {{task.name}}
                    </v-card-title>
                    <v-card-text>
                        {{task.description}}
                    </v-card-text>
                </v-card>

                <v-file-input
                        @change="onFileChange"
                        accept="image/*"
                        label="Upload your zip here"
                        v-if="vacancy.withTask && !isApplied && isUser"
                        v-model="file"
                />
                <v-btn :disabled="vacancy.withTask && !fileBytes" @click="apply" v-if="!isApplied && isUser">
                    Apply

                </v-btn>
            </div>
            <div class="right-sidebar">
            </div>
        </div>
    </div>

</template>

<script>
    import Router from "../../router"
    import {base64ArrayBuffer, urlPort} from "../../tool";
    import {mapGetters, mapState} from "vuex";

    export default {
        name: "vacancyPage",
        data: function () {
            return {
                file: null,
                fileBytes: null,
                vacancy: {
                    id: Router.currentRoute.params.id,
                    name: "",
                    money: null,
                    ownerName: "",
                    requestedSkills: [],
                    withTask: null,
                },
                task: {
                    id: null,
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
                    if (this.vacancy.withTask) {
                        urlPort.get("/vacancies/" + this.vacancy.id + "/tasks")
                            .then(resp => {
                                this.task = resp.data
                            });
                    }
                });
            this.checkApplied();
        },
        computed: {

            ...mapGetters('isUser', 'isCompany'),
        },
        methods: {

            onFileChange: function () {
                let reader = new FileReader();
                reader.readAsArrayBuffer(this.file);
                reader.onload = () => {
                    this.fileBytes = base64ArrayBuffer(reader.result)
                };
            },
            apply() {
                if (this.vacancy.withTask) {
                    let params = new URLSearchParams();
                    params.append("fileData", this.fileBytes);
                    urlPort.post("/vacancies/" + this.vacancy.id + "/tasks/" + this.task.id, params,
                        {headers: {ContentType: 'multipart/form-data'}})
                        .then(resp => {
                                this.checkApplied();
                            }
                        )
                } else {
                    urlPort.post("/vacancies/" + this.vacancy.id).then(resp => {
                        this.checkApplied();
                    })
                }
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