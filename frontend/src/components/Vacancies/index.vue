<template>
    <div>
        <div class="left-sidebar" style="text-align: center; margin: 10px 0 0 0;">
            <div v-if="isCompany">
                <div>
                    <router-link class="my-2" to="/my/vacancies">
                        <v-btn color="primary" large outlined>Add vacancy</v-btn>
                    </router-link>
                </div>
                <div class="link-my-resumes">
                    <router-link class="link" to="/my/vacancies">
                        My vacancies
                    </router-link>
                </div>
            </div>
        </div>

        <div class="content">
            <v-card-text style="margin-top: -25px;">
                <v-data-table
                        :headers="headers"
                        :items="vacancies"
                        class="elevation-1"
                        disable-items-per-page
                        disable-pagination
                        disable-sort
                        hide-default-footer
                >
                    <template v-slot:top>
                        <v-toolbar color="white" flat>
                            <v-toolbar-title>Vacancies</v-toolbar-title>
                            <v-spacer/>
                        </v-toolbar>
                    </template>
                    <template v-slot:item.action="{ item }">
                        <v-icon
                                @click="route(item)"
                                class="mr-2"
                        >
                            mdi-file-document-box
                        </v-icon>
                    </template>
                    <template v-slot:no-data>
                        Please, specify some data
                    </template>
                    <template v-slot:footer>
                        <div style="padding: 10px;">
                            <span>{{filters.offset}}-{{secondNumber}}</span>
                            <span> of {{totalSize}}</span>
                            <v-icon
                                    @click="left()"
                                    class="mr-2"
                                    large
                            >
                                mdi-arrow-left-bold-box-outline
                            </v-icon>
                            <v-icon
                                    @click="right()"
                                    class="mr-2"
                                    large
                            >
                                mdi-arrow-right-bold-box-outline
                            </v-icon>
                        </div>
                    </template>
                </v-data-table>
            </v-card-text>

        </div>
        <div class="right-sidebar">

            <v-card outlined>
                <v-card-text>
                    <div>
                        <div>
                            Sort by
                            <v-select
                                    :items="items"
                                    @change="upload"
                                    v-model="filters.sortBy"
                            />
                            <v-checkbox @change="upload" label="descending" v-model="filters.descending">descending
                            </v-checkbox>
                        </div>

                        <v-text-field
                                label="Vacancy"
                                v-model="tempFilters.vacancyName"/>
                        <v-text-field
                                @change="checkNull"
                                label="Minimum salary"
                                type="number"
                                v-model="tempFilters.money"/>
                        <v-autocomplete
                                :items="companyNames"
                                label="Company"
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
                        <v-btn @click="applyFilters" class="primary"> Apply filters</v-btn>
                    </div>
                </v-card-text>
            </v-card>

        </div>
    </div>

</template>

<script>

    import {urlPort} from "../../tool";
    import {mapGetters} from "vuex";

    export default {
        name: "vacancies",
        data: function () {
            return {
                items: ['Vacancy name', 'Salary', 'Company name'],
                skills: [],
                companyNames: [],
                dialog: false,
                secondNumber: 0,
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
        computed: {
            ...mapGetters(['isUser', 'isCompany']),
        },

        methods: {
            checkNull() {
                this.tempFilters.money = this.tempFilters.money || 0;
                this.tempFilters.money = Number.parseInt(this.tempFilters.money);
            },

            route(item) {
                this.$router.push('/vacancies/' + item.id)
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
                    this.secondNumber = this.totalSize > (this.filters.offset + this.filters.limit) ? this.filters.offset + this.filters.limit : this.totalSize;
                    urlPort.get('/vacancies', {params}).then(resp => {
                        this.vacancies = resp.data;
                        console.log(this.vacancies);
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
            this.upload();
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