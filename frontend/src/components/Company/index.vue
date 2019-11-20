<template>
    <div>
        <div class="companies" v-if="companies.length > 0">
            <div class="sort">
                Sort by: <a href="#">ID</a> <a href="#">Name</a> <a href="#">Verified</a>
            </div>
            <div class="company" v-for="company in companies">
                <div class="title">
                    <h2>
                        <router-link :to="'/companies/' + company.id" class="">{{ company.name }}</router-link>
                        <span class="verified" v-if="company.verified === true"></span>
                    </h2>
                </div>
                <div class="description">{{ company.description }}</div>
            </div>
        </div>
        <div class="companies" style="width: 480px;margin: 0 10px;" v-else>
            No companies found
        </div>
    </div>
</template>

<script>
    import axios from 'axios';
    import {urlPort} from "../../tool";

    export default {
        name: 'Company',

        data() {
            return {
                companies: [],
            }
        },
        created() {
            axios.get(urlPort("/companies"))
                .then(response => {
                    this.companies = response.data;
                })
                .catch(e => {
                    this.errors.push(e)
                })

            // async / await version (created() becomes async created())
            //
            // try {
            //   const response = await axios.get(`http://jsonplaceholder.typicode.com/posts`)
            //   this.posts = response.data
            // } catch (e) {
            //   this.errors.push(e)
            // }
        }
    }
</script>

<style>
    .companies {
        display: table;
        width: 100%;
    }

    .companies .company {
        margin: 10px;
        padding: 10px;
        display: table;
        width: 480px;
        height: 100px;
        border: 1px solid #eee;
        background: #fff;
        box-shadow: 0 0 1px 0 rgba(0, 0, 0, .2);
        border-radius: 6px;
    }

    .companies .company .title h2 {
        font-size: 20px;
    }

    .companies .company .title a {

    }
</style>
