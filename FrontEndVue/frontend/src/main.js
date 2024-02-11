import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios';
import PrimeVue from 'primevue/config';
import "./assets/main.css"
import "primeflex/primeflex.css";
import "primevue/resources/themes/lara-light-green/theme.css";
import "primeicons/primeicons.css";

const app = createApp(App)

app.use(router)
app.use(PrimeVue);
app.mount('#app')
app.config.globalProperties.$axios = axios;

