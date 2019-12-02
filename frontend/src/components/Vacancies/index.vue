<template>
    <v-app>
        <div>


            <v-text-field
                    v-model="tempFilters.vacancyName"
                    label="Vacancy"/>
            <v-text-field
                    type="number"
                    v-model="tempFilters.money"
                    label="Minimum salary"
                    @change="checkNull"/>
            <v-autocomplete
                    label="Company"
                    :items="companyNames"
                    v-model="tempFilters.companyName"/>
            <v-combobox
                    :items="skills"
                    chips
                    clearable
                    label="Skills"
                    multiple
                    v-model="tempFilters.requestedSkills"
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
                    <v-spacer></v-spacer>
                    <div>
                        Sort by
                        <v-select
                                :items="items"
                                v-model="filters.sortBy"
                                @change="upload"
                        ></v-select>
                        <v-checkbox v-model="filters.descending" label="descending" @change="upload">descending</v-checkbox>
                    </div>
                </v-toolbar>
            </template>
            <template v-slot:item.action="{ item }">
                <v-icon
                        class="mr-2"
                        @click="route(item)"
                >
                    mdi-file-document-box
                </v-icon>
            </template>
            <template v-slot:no-data>
                Please, specify some data
            </template>
            <template v-slot:footer>
                <span>{{filters.offset}}-{{filters.offset+filters.limit}}</span>
                <span> of {{totalSize}}</span>
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
                items: ['Vacancy name', 'Salary', 'Company name'],
                skills: [],
                companyNames: [],
                dialog: false,
                totalSize: 0,
                vacancies: [],
                message: "",
                headers: [
                    {text: 'Vacancy name', sortable: false, value: 'name',},
                    {text: 'Salary', value: 'money', sortable: false,},
                    {text: 'Company name', value: 'ownerName', sortable: false,},
                    {text: 'Actions', value: 'action', sortable: false},
                ],
                tempFilters: {
                    money: 0,
                    requestedSkills: [],
                    vacancyName: "",
                    companyName: "",
                },
                filters: {
                    limit: 20,
                    offset: 0,
                    money: 0,
                    requestedSkills: [],
                    vacancyName: "",
                    companyName: "",
                    sortBy: "Salary",
                    descending: true,
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
            checkNull() {
                this.tempFilters.money = this.tempFilters.money || 0;
                this.tempFilters.money = Number.parseInt(this.tempFilters.money);
            },

            route(item) {
                this.$router.push('/vacancies/'+item.id)
            },

            remove(item) {
                this.tempFilters.requestedSkills.splice(this.tempFilters.requestedSkills.indexOf(item), 1);
                this.tempFilters.requestedSkills = [...this.tempFilters.requestedSkills]
            },

            right() {
                if ((this.filters.offset + this.filters.limit) < this.totalSize) {
                    this.filters.offset += this.filters.limit;
                    this.upload();
                } else return false;
            },

            left() {
                if ((this.filters.offset - this.filters.limit) >= 0) {
                    this.filters.offset -= this.filters.limit;
                    this.upload();
                } else return false;
            },

            applyFilters() {
                this.filters.vacancyName = this.tempFilters.vacancyName;
                this.filters.money = this.tempFilters.money;
                this.filters.requestedSkills = this.tempFilters.requestedSkills;
                this.filters.companyName = this.tempFilters.companyName;
                this.upload();
            },

            upload() {
                let params = new URLSearchParams({
                    'filters': JSON.stringify(this.filters)
                });
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