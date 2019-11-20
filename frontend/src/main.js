import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import Loading from './components/lib/Loading'
import Center from './components/lib/CenterContainer'
import Vuetify from 'vuetify'
// import 'vuetify/dist/vuetify.min.css'


Vue.config.productionTip = false;


Vue.component('loading', Loading);
Vue.component('center-container', Center);

Vue.use(Vuetify);
new Vue({
    el: '#app',
    router,
    store,
    template: '<App/>',
    components: {App},
    render: h => h(App),
    vuetify: new Vuetify()
}).$mount('#app');
