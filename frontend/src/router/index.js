import Vue from 'vue'
import Router from 'vue-router'
import Home from "../components/Home/index"
import Rules from "../components/Rules/index"
import About from "../components/About/index";
import Login from "../components/Login/index";
import SignUp from "../components/SignUp/index";
import store from '../store';
import Resume from "../components/Resume/index";
import ResumeForm from "../components/Resume/resumeForm";

Vue.use(Router);

const ifNotAuthenticated = (to, from, next) => {
    if (!store.getters.isAuthenticated) {
        next();
        return
    }
    next('/')
}

const ifAuthenticated = (to, from, next) => {
    if (store.getters.isAuthenticated) {
        next();
        return
    }
    next('/login')
}


export default new Router({
    mode: 'history',
    routes: [
        {
            path: '/',
            name: 'home',
            component: Home
        },
        {
            path: '/rules',
            name: 'rules',
            component: Rules
        },
        {
            path: '/about',
            name: 'about',
            component: About
        },
        {
            path: '/login',
            name: 'login',
            component: Login,
            beforeEnter: ifNotAuthenticated
        },
        {
            path: '/signup',
            name: 'signup',
            component: SignUp,
            beforeEnter: ifNotAuthenticated
        },
        {
            path: '/resumes',
            name: 'resume',
            component: Resume,
            //beforeEnter: ifAuthenticated
        },
        {
            path: '/resumeForm',
            name: 'resumeForm',
            component: ResumeForm,
            //beforeEnter: ifAuthenticated
        }
        // {
        //     path: '/profile',
        //     name: 'profile',
        //     component: Profile,
        //     beforeEnter: ifAuthenticated
        // },
    ]
})