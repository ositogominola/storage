<template>
    <div class="container-fluid">
        <div class="row">
            <!-- Menú -->
            <div class="col-md-2 menu">
                <MenuComponentVue />
            </div>
            <!-- Panel 2 -->
            <div class="col-md-10">
                <Breadcrumb :home="home" :model="items" style="margin-bottom: 20px; margin-top: 20px;">
                    <template #item="{ item }">
                        <router-link v-if="item.route" v-slot="{ href, navigate, isActive }" :to="item.route" custom>
                            <a :href="href" @click="navigate">
                                <span :class="[item.icon, 'text-color']" />
                                <span
                                    :class="{ 'text-secondary font-semibold': !isActive, 'text-success font-semibold': isActive }">
                                    {{ item.label }}
                                </span>
                            </a>
                        </router-link>
                    </template>
                </Breadcrumb>



                <RouterView :key="$route.fullPath" />
            </div>
        </div>

    </div>
</template>


<script setup>
import { RouterView } from 'vue-router';
import MenuComponentVue from './Utils/MenuComponent.vue'
import { ref, computed, onMounted, watch } from "vue";
import { useRoute } from 'vue-router';
import axios from 'axios';


const route = useRoute();


const home = ref({
    icon: 'pi pi-home',
    route: '/'
});

const items = ref([]); // Inicializa items como una referencia reactiva

const loadingRouters = async () => {
    let routePath = route.path;
    let breadcrumbArray = [];
    if (route.path != '/') {
        let userInfoString = localStorage.getItem('userInfo');
        let userInfo = userInfoString.split(',');

        await axios.post('http://127.0.0.1:7777/getRutas/' + userInfo[5], { UrlRecurso: route.path }, { withCredentials: true }).then(response => {
            if (response.status === 200) {
                if (response.data['successful']) {
                    breadcrumbArray = response.data['data'].map(item => ({
                        label: item.label,
                        route: item.route
                    }));
                } else {
                    breadcrumbArray = [];
                    toast.add({ severity: 'error', summary: 'Error', detail: response.data['message'], life: 3000 });
                }
            }
        }).catch(error => {
            // Manejar errores de la solicitud
            console.log(error);
        });
    }
    items.value = breadcrumbArray;
}
watch(route, loadingRouters, { immediate: true });
onMounted(loadingRouters);

</script>