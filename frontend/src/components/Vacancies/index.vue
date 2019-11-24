<template>
    <v-app>
        <v-data-table
                :headers="headers"
                :items="vacancies"
                class="elevation-1"
        >
            <template v-slot:top>
                <v-toolbar flat color="white">
                    <v-toolbar-title>Vacancies</v-toolbar-title>
                    <v-divider
                            class="mx-4"
                            inset
                            vertical
                    ></v-divider>
                    <v-spacer></v-spacer>
                    <v-dialog v-model="dialog" max-width="500px">
                        <v-card>
                            <v-card-title>
                                <span class="headline">Vacancy</span>
                            </v-card-title>

                            <v-card-text>
                                <v-container>
                                            <div>{{editedItem.name}}</div>
                                            <div>{{editedItem.description}}</div>
                                            <div>{{editedItem.money}}</div>
                                            <div>{{editedItem.ownerName}}</div>
                                            <div>{{editedItem.requestedSkills}}</div>
                                </v-container>
                            </v-card-text>

                            <v-card-actions>
                                <v-spacer></v-spacer>
                                <v-btn color="blue darken-1" text @click="close">Cancel</v-btn>
                                <v-btn color="blue darken-1" text @click="apply">Apply</v-btn>
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
            </template>
            <template v-slot:no-data>
                <v-btn color="primary" @click="initialize">Reset</v-btn>
            </template>
        </v-data-table>
    </v-app>
</template>

<script>

    export default {
        name: "vacancies",
        data: function () {
            return {
                dialog: false,
                limit: 20,
                offset: 0,
                vacancies: [],
                message: "",
                headers: [
                    {
                        text: 'Vacancy name',
                        align: 'left',
                        sortable: false,
                        value: 'name',
                    },
                    {
                        text: 'Salary',
                        value: 'money',
                        sortable: false,
                    },
                    {
                        text: 'Company name',
                        value: 'ownerName',
                        sortable: false,
                    },
                    {text: 'Actions', value: 'action', sortable: false},
                ],
                editedItem: {
                    id : '',
                    name: '',
                    money: 0,
                    description: '',
                    requestedSkills: [],
                    ownerName: '',
                },
                editedIndex: -1,
            }

        },
        methods: {
            close() {
                this.dialog = false;
                setTimeout(() => {
                    this.editedIndex = -1;
                }, 300)
            },
            editItem(item) {
                this.editedIndex = this.vacancies.indexOf(item);
                this.editedItem = Object.assign({}, item);
                this.dialog = true
            },

            apply(item){
                console.log(item)
            },

            initialize() {
                this.vacancies = [
                    {
                        id: '1',
                        name: '1',
                        money: 20,
                        description: '3',
                        requestedSkills: ['js', 'java'],
                        ownerName: 'ooo',
                    },
                    {
                        id: '2',
                        name: '2',
                        money: 20,
                        description: '3',
                        requestedSkills: ['js', 'java'],
                        ownerName: 'ooo',
                    },
                    {
                        id: '3',
                        name: '3',
                        money: 20,
                        description: '3',
                        requestedSkills: ['js', 'java'],
                        ownerName: 'ooo',
                    },
                ]
            },
        },


        created() {
            this.initialize()
        },

        watch: {
            dialog(val) {
                val || this.close()
            },
        },

    }
</script>

<style scoped>

</style>