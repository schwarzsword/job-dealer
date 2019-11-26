<template>
    <v-app>
        <div>какая то инфа про компанию, может кто сделает</div>
        <v-data-table
                :headers="headers"
                :items="vacancies"
                sort-by="calories"
                class="elevation-1"
        >
            <template v-slot:top>
                <v-toolbar flat color="white">
                    <v-toolbar-title>My vacancies</v-toolbar-title>
                    <v-divider
                            class="mx-4"
                            inset
                            vertical
                    ></v-divider>
                    <v-spacer></v-spacer>
                    <v-dialog v-model="dialog" max-width="1100px">
                        <template v-slot:activator="{ on }">
                            <v-btn color="primary" dark class="mb-2" v-on="on">New Item</v-btn>
                        </template>
                        <v-card>
                            <v-card-title>
                                <span class="headline">{{ formTitle }}</span>
                            </v-card-title>

                            <v-card-text>
                                <v-container>

                                    <v-text-field v-model="editedItem.name" label="Vacancy name"
                                                  :rules="rules.requiredRules"></v-text-field>
                                    <v-text-field v-model="editedItem.money" label="Salary"
                                                  :rules="rules.salaryRules"></v-text-field>
                                    <v-textarea v-model="editedItem.description" :rules="rules.requiredRules"
                                                label="Vacancy description"></v-textarea>
                                    <v-combobox
                                            :items="skills"
                                            chips
                                            clearable
                                            label="Skills"
                                            multiple
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


                                </v-container>
                            </v-card-text>

                            <v-card-actions>
                                <v-spacer></v-spacer>
                                <v-btn color="blue darken-1" text @click="close">Cancel</v-btn>
                                <v-btn color="blue darken-1" text @click="save">Save</v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-dialog>
                </v-toolbar>
            </template>
            <template v-slot:item.action="{ item }">
                <v-icon
                        small
                        class="mr-2"
                        @click="editItem(item)"
                >
                    edit
                </v-icon>
                <v-icon
                        small
                        @click="deleteItem(item)"
                >
                    delete
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
        name: "CompanyProfile",
        data: function () {
            return {
                dialog: false,
                headers: [
                    {text: 'Vacancy name', value: 'name',},
                    {text: 'Salary', value: 'money'},
                    {text: 'Actions', value: 'action', sortable: false},
                ],
                vacancies: [],
                editedIndex: -1,
                editedItem: {
                    id: null,
                    name: '',
                    money: 0,
                    requestedSkills: [],
                    description: '',
                },
                defaultItem: {
                    id: null,
                    name: '',
                    money: 0,
                    requestedSkills: [],
                    description: '',
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


            editItem(item) {
                this.editedIndex = this.vacancies.indexOf(item);
                this.editedItem = Object.assign({}, item);
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
                    this.editedIndex = -1
                }, 300)
            },

            save() {
                if (this.editedIndex > -1) {
                    Object.assign(this.vacancies[this.editedIndex], this.editedItem);
                } else {
                    this.vacancies.push(this.editedItem);
                    let params = new URLSearchParams();
                    params.append("id", this.editedItem.id);
                    params.append("requestedSkills", this.editedItem.requestedSkills);
                    params.append("money", this.editedItem.money);
                    params.append("description", this.editedItem.description);
                    params.append("name", this.editedItem.name);
                    urlPort.post('/vacancies', params).then(resp => {

                    }).catch(err => {

                    })
                }
                this.close()
            },
            remove(item) {
                this.editedItem.requestedSkills.splice(this.editedItem.requestedSkills.indexOf(item), 1);
                this.editedItem.requestedSkills = [...this.editedItem.requestedSkills]
            },
        },
    }

</script>

<style scoped>

</style>