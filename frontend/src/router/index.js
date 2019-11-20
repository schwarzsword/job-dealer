import Vue from 'vue'
import Router from 'vue-router'
import Home from "../components/Home"
import Rules from "../components/Rules"
import About from "../components/About";
import Login from "../components/Login";
import SignUp from "../components/SignUp";
import Account from "../components/Account";
import Applicant from "../components/Applicant";
import Company from "../components/Company";
import CompanyPage from "../components/Company/page";
import store from '../store'
import Vacancies from "../components/Vacancies"

Vue.use(Router);

const ifNotAuthenticated = (to, from, next) => {
    if (!store.getters.isAuthenticated) {
        next();
        return
    }
    next('/')
};

const ifAuthenticated = (to, from, next) => {
    if (store.getters.isAuthenticated) {
        next();
        return
    }
    next('/login')
};


export default new Router({
    name: 'Router',
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
            path: '/account',
            name: 'account',
            component: Account
        },
        {
            path: '/companies',
            name: 'companies',
            component: Company
        },
        {
            path: '/companies/:id',
            name: 'CompanyPage',
            component: CompanyPage
        },
        {
            path: '/applicants',
            name: 'applicants',
            component: Applicant
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
        // {
        //     path: '/profile',
        //     name: 'profile',
        //     component: Profile,
        //     beforeEnter: ifAuthenticated
        // },
        {
            path: '/vacancies',
            name: 'vacancies',
            component: Vacancies,
        }
    ]
})
