<template>
    <div class="footer">
        <div class="container">
            <router-link class="nav-link" to="/about">About</router-link>
            <router-link class="nav-link" to="/rules">Rules</router-link>
            <router-link class="nav-link" to="/companies">Companies</router-link>
            <router-link class="nav-link" to="/applicants">Applicants</router-link>
            <br>
            (c) JobDealer 2020
        </div>
    </div>
</template>

<script>
    export default {
        name: 'Footer',
        data() {
            return {}
        }
    }
</script>

<style>
    .footer {
        display: table;
        width: 100%;
        font-size: 14px;
        line-height: 22px;
        color: #6c757d;
    }
    .footer .container {
        display: table;
        margin: 0 auto;
        width: 1000px;
        text-align: center;
    }
    .footer .container a {
        font-size: 14px;
        padding: 5px;
        color: #6c757d;
    }
    .footer .container a:hover {
        text-decoration: underline;
    }
</style>
