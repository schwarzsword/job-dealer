<template>
    <v-app>
        <div>


            <v-text-field
                    v-model="filters.vacancyName"
                    label="Vacancy"/>
            <v-text-field
                    type="number"
                    maxva
                    v-model="filters.money"
                    label="Minimum salary"
                    @change="checkNull"/>
            <v-autocomplete
                    label="Company"
                    :items="companyNames"
                    v-model="filters.companyName"/>
            <v-combobox
                    :items="skills"
                    chips
                    clearable
                    label="Skills"
                    multiple
                    v-model="filters.requestedSkills"
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
            <v-btn @click="applyFilters"> Apply filters</v-btn>
        </div>

        <v-data-table
                :headers="headers"
                :items="vacancies"
                class="elevation-1"
                disable-pagination
                disable-items-per-page
                disable-sort
                hide-default-footer
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
                    <div>
                        Sort by
                        <v-select
                                :items="items"
                                v-model="savedFilters.sortBy"
                                @change="upload"
                        ></v-select>
                    </div>
                    <v-dialog v-model="dialog" max-width="1100px">
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
                        class="mr-2"
                        @click="editItem(item)"
                >
                    mdi-file-document-box
                </v-icon>
            </template>
            <template v-slot:no-data>
                Please, specify some data
            </template>
            <template v-slot:footer>
                <span>{{offset}}-{{offset+limit}}</span>
                <span>of {{totalSize}}</span>
                <v-icon
                        large
                        class="mr-2"
                        @click="left()"
                >
                    mdi-arrow-left-bold-box-outline
                </v-icon>
                <v-icon
                        large
                        class="mr-2"
                        @click="right()"
                >
                    mdi-arrow-right-bold-box-outline
                </v-icon>
            </template>
        </v-data-table>
    </v-app>
</template>

<script>

    import {urlPort} from "../../tool";

    export default {
        name: "vacancies",
        data: function () {
            return {
                items: ['Vacancy name ascending', 'Vacancy name descending', 'Salary ascending', 'Salary descending', 'Company name ascending', 'Company name descending',],
                skills: [],
                companyNames: [],
                dialog: false,
                limit: 20,
                offset: 0,
                totalSize: 0,
                vacancies: [],
                message: "",
                headers: [
                    {text: 'Vacancy name', sortable: false, value: 'name',},
                    {text: 'Salary', value: 'money', sortable: false,},
                    {text: 'Company name', value: 'ownerName', sortable: false,},
                    {text: 'Actions', value: 'action', sortable: false},
                ],
                editedItem: {
                    id: '',
                    name: '',
                    money: 0,
                    description: '',
                    requestedSkills: [],
                    ownerName: '',
                },
                filters: {
                    money: 0,
                    requestedSkills: [],
                    vacancyName: "",
                    companyName: "",
                },
                savedFilters: {
                    money: 0,
                    requestedSkills: [],
                    vacancyName: "",
                    companyName: "",
                    sortBy: "Salary descending",
                },
                rules: {
                    moneyRules: [
                        v => /[0-9]/.test(v) || 'It should be a number',
                    ],
                    requiredRules: [
                        v => !!v || 'Field is required',
                    ],
                },
            }

        },
        methods: {
            close() {
                this.dialog = false;
                setTimeout(() => {
                    this.editedIndex = -1;
                }, 300)
            },

            checkNull() {
                this.filters.money === "" ? this.filters.money = 0 : false;
            },

            editItem(item) {
                this.editedIndex = this.vacancies.indexOf(item);
                this.editedItem = Object.assign({}, item);
                this.dialog = true
            },

            apply() {

            },

            remove(item) {
                confirm()
                this.filters.requestedSkills.splice(this.filters.requestedSkills.indexOf(item), 1);
                this.filters.requestedSkills = [...this.filters.requestedSkills]
            },

            right() {
                if ((this.offset + this.limit) < this.totalSize) {
                    this.offset += this.limit;
                    this.upload();
                } else return false;
            },

            left() {
                if ((this.offset - this.limit) >= 0) {
                    this.offset -= this.limit;
                    this.upload();
                } else return false;
            },

            applyFilters() {
                this.savedFilters.vacancyName = this.filters.vacancyName;
                this.savedFilters.money = this.filters.money;
                this.savedFilters.requestedSkills = this.filters.requestedSkills;
                this.savedFilters.companyName = this.filters.companyName;
                this.upload();
            },

            upload() {
                let params = new URLSearchParams({
                    'limit': this.limit,
                    'offset': this.offset,
                    "money": this.savedFilters.money,
                    'sortBy': this.savedFilters.sortBy
                });
                if (this.savedFilters.requestedSkills.size !== 0) {
                    params.append('requestedSkills', this.savedFilters.requestedSkills)
                }
                if (this.savedFilters.companyName !== ""){
                    params.append('companyName', this.savedFilters.companyName)
                }
                if (this.savedFilters.vacancyName !== ""){
                    params.append('vacancyName', this.savedFilters.vacancyName)
                }
                urlPort.get('/vacancies/size', {params}).then(resp => {
                    this.totalSize = resp.data;
                    urlPort.get('/vacancies', {params}).then(resp => {
                        this.vacancies = resp.data;
                    }).catch(err => {

                    })
                }).catch(err => {

                });

            },

            uploadSkills() {
                urlPort.get('/skills')
                    .then(resp => {
                        this.skills = resp.data;
                    })
            },

            uploadCompanyNames() {
                urlPort.get('/companies/names')
                    .then(resp => {
                        this.companyNames = resp.data;
                    })
            }
        },


        created() {
            this.uploadSkills();
            this.uploadCompanyNames();
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