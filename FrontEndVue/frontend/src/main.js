import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios';
import PrimeVue from 'primevue/config';
import ToastService from 'primevue/toastservice';
import ConfirmationService from 'primevue/confirmationservice';
import store from './assets/store' 

import "./assets/main.css"
import "primeflex/primeflex.css";
import "primevue/resources/themes/lara-light-green/theme.css";
import "primeicons/primeicons.css";
import '@fortawesome/fontawesome-free/css/all.css'


const app = createApp(App)

app.use(router);
app.use(store);
app.use(PrimeVue);
app.use(ToastService);
app.use(ConfirmationService);
app.mount('#app')
app.config.globalProperties.$axios = axios;

