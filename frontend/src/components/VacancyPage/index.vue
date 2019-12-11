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

        </v-card>
        <v-card v-if="task.name">
            {{task.name}}
            {{task.description}}
        </v-card>

        <v-file-input
                @change="onFileChange"
                accept="image/*"
                label="Please, upload zip files"
                v-if="vacancy.withTask"
                v-model="file"
        />
        <v-btn :disabled="vacancy.withTask && !fileBytes" @click="apply" v-if="!isApplied">
            Apply
        </v-btn>
    </v-app>

</template>

<script>
    import Router from "../../router"
    import {base64ArrayBuffer, urlPort} from "../../tool";

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