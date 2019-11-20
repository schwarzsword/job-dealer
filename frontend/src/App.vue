<template>
    <div id="app">
        <navigation/>
        <div class="wrapper">
            <div class="container">
                <div class="left-sidebar">
                    <!--Left-->
                </div>
                <div class="content">
                    <router-view/>
                </div>
                <div class="right-sidebar">
                    <RightSidebar/>
                </div>
            </div>
        </div>
        <Footer/>
    </div>
</template>

<script>
  import Navigation from './components/Navigation'
  import {USER_REQUEST} from './store/actions/user'
  // import '@/assets/css/bootstrap.min.css';
  import Footer from "./components/Footer";
  import RightSidebar from "./components/RightSidebar"

  export default {
        components: {
            Footer,
            Navigation,
            RightSidebar
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

    /* wrapper */
    .wrapper {
        display: table;
        width: 100%;
        height: auto;
        min-height: 500px;
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
        /*background-color: #eee;*/
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
        /*background-color: #eee;*/
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
