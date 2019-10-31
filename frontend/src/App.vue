<template>
    <div id="app">
        <navigation/>
        <div class="main-container">
            <div class="wrapper">
                <div class="container">
                    <div class="left-sidebar">
                        Left
                    </div>
                    <div class="content">
                        <router-view/>
                    </div>
                    <div class="right-sidebar">
                        Right
                    </div>
                </div>
            </div>
            <div class="footer">
            </div>
        </div>
    </div>
</template>

<script>
    import Navigation from './components/Navigation'
    import {USER_REQUEST} from './store/actions/user'

    export default {
        components: {
            Navigation
        },
        name: 'app',
        created: function () {
            if (this.$store.getters.isAuthenticated) {
                this.$store.dispatch(USER_REQUEST)
            }
        }
    }
</script>

<style>
    a {
        color: #3E5F8A;
    }

    a:hover {
        color: #007bff;
    }

    .header {
        display: table;
        width: 100%;
        height: 60px;
        line-height: 60px;
        background-color: #fff;
        /*box-shadow: 0 0 5px 2px rgba(200, 200, 200, .6);*/
    }

    .header .container {
        display: block;
        width: 1000px;
        height: inherit;
        line-height: inherit;
    }

    /* logo */
    .header .container .logo {
        display: block;
        width: 200px;
        height: inherit;
        line-height: inherit;
        float: left;
    }

    .header .container .logo .link {
        display: block;
        width: 200px;
        height: inherit;
        line-height: inherit;
        float: left;
    }

    .header .container .logo .link a {
        display: block;
        width: 200px;
        height: inherit;
        line-height: inherit;
        float: left;
        text-align: center;
        background: url("/public/logo.png") no-repeat center;
        background-size: auto 50px;
    }

    .header .container .logo .link a:hover {
        text-decoration: none;
    }

    /* menu */
    .header .container .menu {
        display: block;
        width: auto;
        height: inherit;
        float: left;
    }

    .header .container .menu ul {
        display: block;
        float: left;
        width: auto;
        height: inherit;
        margin: 0;
        padding: 0;
    }

    .header .container .menu ul li {
        display: block;
        float: left;
        width: auto;
        height: inherit;
        line-height: inherit;
    }

    .header .container .menu ul li a {
        display: block;
        float: left;
        width: auto;
        margin: 15px 0;
        height: 30px;
        padding: 0 15px;
        line-height: 30px;
        color: #000;
        border-radius: 30px;
        transition: background-color 0.15s ease 0s;
    }

    .header .container .menu ul li a:hover {
        background-color: rgba(0, 0, 0, 0.07);
        text-decoration: none;
    }

    /* find */
    .header .container .find {
        display: block;
        width: 400px;
        height: inherit;
        line-height: inherit;
        float: left;
        margin: 0;
    }

    .header .container .find input {
        width: 380px;
        height: 40px;
        border: 0;
        border-radius: 20px;
        padding: 0 20px;
        margin: 0 10px;
        background-color: rgba(0, 0, 0, 0.07);
        transition: border-color 0.1s ease-out 0s, border-width, background-color;
    }

    .header .container .find input::placeholder {
        text-align: center;
        color: #000;
        opacity: 0.4;
        transition-delay: 2s;
        transition: width 2s ease-in-out;
        transition-duration: 5s;
    }

    .header .container .find input:focus {
        outline: none;
    }

    .header .container .find input:focus::placeholder {
        text-align: left;
        color: #000;
        opacity: 0.2;
    }

    /* user */
    .header .container .user {
        display: block;
        width: auto;
        height: inherit;
        line-height: inherit;
        float: right;
        margin: 0;
        text-align: center;
    }

    .header .container .user a.sign_in {
        line-height: inherit;
        color: #000;
        border: 2px rgba(0, 0, 0, 0.07) solid;
        padding: 5px 20px;
        border-radius: 20px;
    }

    .header .container .user a.sign_in:hover {
        text-decoration: none;
        color: rgba(0, 0, 0, 0.9);
        background-color: rgba(0, 0, 0, 0.03);
        border-color: rgba(0, 0, 0, 0.03);
    }

    /* wrapper */
    .wrapper {
        display: table;
        width: 100%;
        height: auto;
    }

    .wrapper .container {
        display: table;
        margin: 60px auto 0 auto;
        width: 1000px;
        height: auto;
        padding: 0;
    }

    .wrapper .container .left-sidebar {
        display: block;
        float: left;
        width: 200px;
        min-height: 100px;
        background-color: #eee;
    }

    .wrapper .container .content {
        display: block;
        float: left;
        width: 500px;
        min-height: 100px;
    }

    .wrapper .container .right-sidebar {
        display: block;
        float: left;
        width: 300px;
        min-height: 100px;
        background-color: #eee;
    }

    /*Small devices (landscape phones, 0px and up)*/
    @media (min-width: 0px) {
        .header .container {
            width: 100%;
        }

        .wrapper .container {
            width: 100%;
        }

        .footer .container {
            width: 100%;
        }
    }

    /*Small devices (landscape phones, 576px and up)*/
    @media (min-width: 576px) {
        .header .container {
            width: 100%;
        }

        .wrapper .container {
            width: 100%;
        }

        .footer .container {
            width: 100%;
        }
    }

    /*Medium devices (tablets, 768px and up)*/
    @media (min-width: 768px) {
        .header .container {
            width: 640px;
        }

        .wrapper .container {
            width: 640px;
        }

        .footer .container {
            width: 640px;
        }
    }

    /*Large devices (desktops, 992px and up)*/
    @media (min-width: 992px) {
        .header .container {
            width: 720px;
        }

        .wrapper .container {
            width: 720px;
        }

        .footer .container {
            width: 720px;
        }
    }

    /*Extra large devices (large desktops, 1200px and up)*/
    @media (min-width: 1000px) {
        .header .container {
            width: 1000px;
        }

        .wrapper .container {
            width: 1000px;
        }

        .footer .container {
            width: 1000px;
        }
    }
</style>