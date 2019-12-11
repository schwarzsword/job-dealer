<template>
    <v-app>
        <v-data-table
                :headers="headers"
                :items="vacancies"
                class="elevation-1"
                sort-by="calories"
        >
            <template v-slot:top>
                <v-toolbar color="white" flat>
                    <v-toolbar-title>My vacancies</v-toolbar-title>
                    <v-divider
                            class="mx-4"
                            inset
                            vertical
                    ></v-divider>
                    <v-spacer></v-spacer>
                    <v-dialog max-width="1100px" v-model="dialog">
                        <template v-slot:activator="{ on }">
                            <v-btn class="mb-2" color="primary" dark v-on="on">New Item</v-btn>
                        </template>
                        <v-card>
                            <v-card-title>
                                <span class="headline">{{ formTitle }}</span>
                            </v-card-title>

                            <v-card-text>
                                <v-container>

                                    <v-text-field
                                            :rules="rules.requiredRules" label="Vacancy name"
                                            outlined
                                            v-model="editedItem.name"/>
                                    <v-text-field :rules="rules.salaryRules" label="Salary"
                                                  outlined
                                                  type="number"
                                                  v-model="editedItem.money"/>
                                    <v-textarea :rules="rules.requiredRules"
                                                label="Vacancy description" outlined
                                                v-model="editedItem.description"/>
                                    <v-combobox
                                            :items="skills"
                                            chips
                                            clearable
                                            label="Skills"
                                            multiple
                                            outlined
                                            v-model="editedItem.requestedSkills"
                                    >
                                        <template v-slot:selection="{ attrs, item, select, selected }">
                                            <v-chip
                                                    :input-value="selected"
                                                    @click="select"
                                                    @click:close="remove(item)"
                                                    close
                                                    v-bind="attrs"
                                            >
                                                <strong>{{ item }}</strong>&nbsp;
                                            </v-chip>
                                        </template>
                                    </v-combobox>
                                    <v-checkbox label="Add test task?" v-model="editedItem.withTask"></v-checkbox>

                                </v-container>
                                <v-container v-if="editedItem.withTask">
                                    <v-text-field
                                            :rules="rules.requiredRules"
                                            label="Task name"
                                            outlined
                                            v-model="task.name"/>
                                    <v-textarea :rules="rules.requiredRules"
                                                label="Task description"
                                                outlined
                                                v-model="task.description"/>
                                </v-container>
                            </v-card-text>

                            <v-card-actions>
                                <v-spacer></v-spacer>
                                <v-btn @click="close" color="blue darken-1" text>Cancel</v-btn>
                                <v-btn @click="save" color="blue darken-1" text>Save</v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-dialog>
                </v-toolbar>
            </template>
            <template v-slot:item.action="{ item }">
                <v-icon
                        @click="editItem(item)"
                        class="mr-2"
                        small
                >
                    edit
                </v-icon>
                <v-icon
                        @click="deleteItem(item)"
                        small
                >
                    delete
                </v-icon>
                <v-icon
                        @click="route(item)"
                        class="mr-2"
                >
                    mdi-file-document-box
                </v-icon>
            </template>
            <template v-slot:no-data>
                You have no active vacancies
            </template>
        </v-data-table>

    </v-app>
</template>

<script>
    import {urlPort} from "../../tool";

    export default {
        name: "companyVacancies",
        data: function () {
            return {
                dialog: false,
                headers: [
                    {text: 'Vacancy name', value: 'name',},
                    {text: 'Salary', value: 'money'},
                    {text: 'Actions', value: 'action', sortable: false},
                ],
                task: {
                    id: null,
                    name: "",
                    description: "",
                    vacancyId: null,
                },
                defaultTask: {
                    id: null,
                    name: "",
                    description: "",
                    vacancyId: null
                },

                vacancies: [],
                editedIndex: -1,
                editedItem: {
                    id: null,
                    name: '',
                    money: 0,
                    requestedSkills: [],
                    description: '',
                    ownerName: '',
                    withTask: false,
                },
                defaultItem: {
                    id: null,
                    name: '',
                    money: 0,
                    requestedSkills: [],
                    description: '',
                    ownerName: '',
                    withTask: false,
                },
                skills: [],
                rules: {
                    salaryRules: [
                        v => /[0-9]/.test(v) || 'It should be a number',
                        v => !!v || 'Field is required',
                    ],
                    requiredRules: [
                        v => !!v || 'Field is required',
                    ],
                },
            }
        },
        computed: {
            formTitle() {
                return this.editedIndex === -1 ? 'New vacancy' : 'Edit vacancy'
            },

        },


        watch: {
            dialog(val) {
                val || this.close()
            },
        },

        created() {
            this.uploadSkills();
            this.initialize()
        },


        methods: {
            initialize() {
                urlPort.get("/my/vacancies").then(resp => {
                    this.vacancies = resp.data;
                })
            },

            uploadSkills() {
                urlPort.get('/skills')
                    .then(resp => {
                        this.skills = resp.data;
                    })
            },

            route(item) {
                this.$router.push('/my/vacancies/' + item.id)
            },


            editItem(item) {
                this.editedIndex = this.vacancies.indexOf(item);
                this.editedItem = Object.assign({}, item);
                urlPort.get('/vacancies/' + item.id + '/tasks').then(
                    resp => {
                        this.task = resp.data;
                        this.editedItem.withTask = true;
                    }
                );
                this.dialog = true
            },

            deleteItem(item) {
                const index = this.vacancies.indexOf(item);
                confirm('Are you sure you want to delete this vacancy?')
                && this.vacancies.splice(index, 1)
                && urlPort.delete("/vacancies/" + item.id)
            },

            close() {
                this.dialog = false;
                setTimeout(() => {
                    this.editedItem = Object.assign({}, this.defaultItem);
                    this.task = Object.assign({}, this.defaultTask);
                    this.editedIndex = -1
                }, 300)
            },

            save() {
                if (this.editedIndex > -1) {
                    Object.assign(this.vacancies[this.editedIndex], this.editedItem);
                } else {
                    this.vacancies.push(this.editedItem);
                }
                let params = new URLSearchParams();
                params.append("vacancyData", JSON.stringify(this.editedItem));
                let w = this.editedItem.withTask;
                let task = this.task;
                urlPort.post('/vacancies', params).then(resp => {
                    if (w) {
                        task.vacancyId = resp.data.id;
                        let params = new URLSearchParams();
                        params.append("taskData", JSON.stringify(task));
                        urlPort.post('/vacancies/' + task.vacancyId + '/tasks', params)
                    }
                }).catch(err => {

                });
                this.close()
            },
            remove(item) {
                this.editedItem.requestedSkills.splice(this.editedItem.requestedSkills.indexOf(item), 1);
                this.editedItem.requestedSkills = [...this.editedItem.requestedSkills]
            },
        }
        ,
    }

</script>

<style scoped>

</style>