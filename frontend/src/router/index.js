import Vue from 'vue'
import Router from 'vue-router'

import Home from "../components/Home"
import Rules from "../components/Rules"
import About from "../components/About";
import Login from "../components/Login";
import SignUp from "../components/SignUp";
import Account from "../components/Account";
import ApplicantOwnerPage from "../components/Applicant/ApplicantOwnerPage";
import Company from "../components/Company";
import CompanyPage from "../components/Company/page";
import Message from "../components/Message"
import store from '../store'
import Vacancies from "../components/Vacancies"
import Resume from "../components/Resume/index";
import ResumeAdd from "../components/Resume/add";
import ResumePage from "../components/Resume/page";
import ResumeForm from "../components/Resume/resumeForm";
import CompanyVacancies from "../components/CompanyVacancies"
import VacancyPage from "../components/VacancyPage"
import CompanyVacancyPage from "../components/VacancyPage/CompanyVacancyPage";
import MessageDialog from "../components/Message/dialog"
import NotFound from "../components/NotFound"

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
            path: '/vacancies/:id',
            name: 'vacancyPage',
            component: VacancyPage
        },

        {
            path: '/my/vacancies/:id',
            name: 'companyVacancyPage',
            component: CompanyVacancyPage
        },
        {
            path: '/my/vacancies',
            name: 'companyVacancies',
            component: CompanyVacancies
        },
        {
            path: '/profile',
            name: 'applicant',
            component: ApplicantOwnerPage
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
            //beforeEnter: ifAuthenticated,
            props: (route) => ({ query: route.query.q })
        },
        {
            path: '/resumes/add',
            name: 'addResume',
            component: ResumeAdd,
            beforeEnter: ifAuthenticated,
            props: (route) => ({ query: route.query.q })
        },
        {
            path: '/resumes/:id',
            name: 'resumePage',
            component: ResumePage,
        },
        {
            path: '/resumeForm',
            name: 'resumeForm',
            component: ResumeForm,
            //beforeEnter: ifAuthenticated
        },
        {
            path: '/vacancies',
            name: 'vacancies',
            component: Vacancies,
        },
        {
            path: '/my/messages',
            name: 'messages',
            component: Message,
        },
        {
            path: '/my/messages/:opponentId',
            name: 'messageDialog',
            component: MessageDialog,
        },
        {
            path: '*',
            name: 'notFound',
            component: NotFound,
        }
    ]
})
