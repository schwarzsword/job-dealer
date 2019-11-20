<template>
    <div class="footer">
        <div class="container">
            <router-link class="nav-link" to="/about">About</router-link>
            <router-link class="nav-link" to="/rules">Rules</router-link>
            <router-link class="nav-link" to="/companies">Companies</router-link>
            <router-link class="nav-link" to="/applicants">Applicants</router-link>
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
    .footer a {
        float: left;
        font-size: 14px;
    }
</style>