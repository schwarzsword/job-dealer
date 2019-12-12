<template>
    <div>
        <div class="left-sidebar" style="text-align: center; margin: 10px 0 0 0;">
            <div>
                <router-link class="my-2" to="/resumes/add">
                    <v-btn color="primary" large outlined>Add resume</v-btn>
                </router-link>
            </div>
            <div class="link-my-resumes">
                <router-link class="link" to="/my/resumes">
                    My resumes
                </router-link>
            </div>
        </div>

        <div class="content">
            <div v-for="item in this.resumes">
                <ResumeItem
                        :author="`${item.firstName} ${item.lastName}`"
                        :id="`${item.id}`"
                        :skills="`${item.skills}`"
                        :title="`${item.name}`"/>
            </div>
        </div>

        <div class="right-sidebar">
            <v-card class="filter-block" outlined>
                <v-card-text>

                    <div>
                        Sort by
                        <v-select
                                :items="items"
                                v-model="filters.sortBy"
                        />
                        <v-checkbox label="descending" v-model="filters.descending">descending</v-checkbox>
                    </div>

                    <!--          <v-select :items="countries" v-model="country" @change="handleCountry" label="Choose country"></v-select>-->
                    <!--          <v-select :items="cities" v-model="city" @change="handleCity" label="Choose city"></v-select>-->
                    Salary:
                    <div style="display: table; margin-bottom: 20px;">
                        <v-text-field @change="handleSalary" class="salary-input" hide-details single-line
                                      type="number"
                                      v-model="price[0]"/>
                        <v-text-field @change="handleSalary" class="salary-input" hide-details single-line
                                      type="number"
                                      v-model="price[1]"/>
                    </div>
                    <v-range-slider :max="5000" :min="0" :step="100" @change="handleSalary" v-model="price"/>
                    <!--          <v-checkbox class="check-box" v-model="experience" @change="handleExperience" label="Experience"/>-->
                    <!--          <v-checkbox class="check-box" v-model="driverLicense" @change="handleDriverLicense"-->
                    <!--                      label="Driver license"/>-->
                    <!--          <v-switch v-model="saveFilter" @change="handleSaveFilter" label="Save filter"/>-->
                </v-card-text>
            </v-card>
        </div>
    </div>
</template>

<script>
    import ResumeItem from "./item";
    import {urlPort} from "../../tool";

    export default {
        name: 'Resume',
        components: {ResumeItem},
        data: function () {
            return {
                countries: ['Russia', 'USA', 'Germany', 'India'],
                cities: ['Moscow', 'St. Petersburg', 'Saratov'],
                country: 'Russia',
                city: 'St. Petersburg',
                checkbox: true,
                saveFilter: false,
                salary: 0,
                price: [0, 5000],
                experience: false,
                driverLicense: false,

                totalSize: 0,
                resumes: [],

                items: ['Name', 'Salary'],

                filters: {
                    limit: 20,
                    offset: 0,
                    minSalary: 0,
                    maxSalary: 500,
                    skills: [],
                    sortBy: "Name",
                    descending: true,
                },
            }
        },
        methods: {
            handleCountry() {
                let urlParams = new URLSearchParams(window.location.search);
                urlParams.set("country", this.country);
                this.$router.push('/resumes/?' + urlParams.toString());
            },
            handleCity() {
                let urlParams = new URLSearchParams(window.location.search);
                urlParams.set("city", this.city);
                this.$router.push('/resumes/?' + urlParams.toString());
            },
            handleSalary() {
                let urlParams = new URLSearchParams(window.location.search);
                urlParams.set("salaryMin", this.price[0]);
                urlParams.set("salaryMax", this.price[1]);
                this.$router.push('/resumes/?' + urlParams.toString());
            },
            handleExperience() {
                let urlParams = new URLSearchParams(window.location.search);
                urlParams.set("experience", this.experience);
                this.$router.push('/resumes/?' + urlParams.toString());
            },
            handleDriverLicense() {
                let urlParams = new URLSearchParams(window.location.search);
                urlParams.set("driverLicense", this.driverLicense);
                this.$router.push('/resumes/?' + urlParams.toString());
            },
            handleSaveFilter() {
                let urlParams = new URLSearchParams(window.location.search);
                urlParams.set("saveFilter", this.saveFilter);
                this.$router.push('/resumes/?' + urlParams.toString());
            },
        },

        created: function () {
            const app = document.createElement('div');
            app.setAttribute('data-app', true);
            document.body.append(app);

            let params = new URLSearchParams({
                'filters': JSON.stringify(this.filters)
            });
            urlPort.get('/resumes/size', {params}).then(resp => {
                this.totalSize = resp.data;
                urlPort.get('/resumes', {params}).then(resp => {
                    this.resumes = resp.data;
                    console.log(this.resumes)
                }).catch(err => {
                    console.log(err)
                })
            }).catch(err => {
                console.log(err)  // тут status: 304
            });
            //console.log(this.$store.state.profile);
        },
    }
</script>

<style>
    /* right sidebar */
    .right-sidebar .filter-block {
        display: block;
        width: calc(100% - 20px);
        height: auto;
        margin: 10px;
    }

    .right-sidebar .filter-block .salary-input {
        display: block;
        margin: 0;
        padding: 0;
        float: left;
        width: 50%;
    }

    .right-sidebar .filter-block .check-box {
        display: block;
        margin: 0;
        padding: 0;
        line-height: 20px;
        height: 30px;
    }

    /* content */
    .content {
        margin-top: 10px;
    }

    .content .item {
        display: block;
        width: calc(100% - 20px);
        height: auto;
        padding: 0;
        margin: 0 10px;
        background-color: #fafafa;
        border-bottom: 1px solid #f0f0f0;
    }

    .content .item:hover {
        background-color: #f5f5f5;
    }

    .content .item .title {
        font-size: 20px;
        /*color: #1b1e21;*/
    }

    .content .item .title:hover {
        color: #5bbaff;
    }

    .content .item .author {
        font-size: 14px;
        font-weight: bold;
        color: #4e555b;
    }

    .content .item .desc {
        font-size: 14px;
    }

    .content .item .item-bottom {
        display: table;
        width: 100%;
        height: 40px;
        line-height: 40px;
    }

    .content .item .item-bottom .invite {
        display: block;
        float: left;
        font-size: 14px;
        color: #1e7e34;
    }

    .content .item .item-bottom .invite:hover {
        color: #5bbaff;
    }

    .content .item .item-bottom .date {
        display: block;
        float: right;
        font-size: 14px;
    }

    .link-my-resumes {
        display: table;
        width: 100%;
        height: 50px;
        text-align: center;
        font-size: 14px;
        color: #1b1e21;
    }

    .link-my-resumes .link {
        line-height: 50px;
        font-size: 14px;
        color: #1b1e21;
    }

    .link-my-resumes .link:hover {
        text-decoration: underline;
    }
</style>
