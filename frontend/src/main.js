import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import Loading from './components/lib/Loading'
import Center from './components/lib/CenterContainer'
import Resume from "./components/Resume/index"
import ResumeShortDescription from "./components/Resume/resumeShortDescription"

Vue.config.productionTip = false;

Vue.component('Resume', Resume)
Vue.component('ResumeShortDescription', ResumeShortDescription)
Vue.component('loading', Loading)
Vue.component('center-container', Center);
/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    store,
    template: '<App/>',
    components: {App},
    render: h => h(App)
});
