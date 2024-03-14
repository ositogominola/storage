<template>
    <div style="height: 100%;">
        <Menu :model="items" class="bg-dark text-white contPage">
            <template #start>
                <span class="inline-flex align-items-center contPageLogo">ShopManager</span>
            </template>
            <template #submenuheader="{ item }">
                <span>{{ item.label }}</span>
            </template>
            <template #item="{ item }">
                <router-link class="flex align-items-center itemMenu" :to="item.href === 'ItemsUserPerfil' ? {name: item.href, params: { id: item.id, rol: userInfo[5] }} : item.href">
                    <span :class="item.icon" />
                    <span class="ml-2">{{ item.label }}</span>
                    <Badge v-if="item.badge" class="ml-auto" :value="item.badge" />
                    <span v-if="item.shortcut" class="ml-auto border-1 surface-border border-round surface-100 text-xs p-1">
                        {{ item.shortcut }}
                    </span>
                </router-link>
            </template>
            <template #end>
                <Button v-ripple class="user overflow-hidden p-link flex align-items-center text-color">
                    <Avatar image="https://primefaces.org/cdn/primevue/images/avatar/amyelsner.png" class="mr-4"
                        shape="circle" />
                    <span class="inline-flex flex-column">
                        <span class="font-bold">{{ userInfo[1] }} {{ userInfo[2] }}</span>
                        <span class="text-sm">{{ userInfo[4] }}</span>
                    </span>
                </Button>
                <Button @click="logout" class="userLog p-link flex align-items-center text-color">
                    <span style="color: white;" class="pi pi-power-off inline-flex flex-column" />
                </Button>
            </template>
        </Menu>
    </div>
</template>

<script setup>
import axios from 'axios';
import router from '@/router';
import { ref, onBeforeMount } from 'vue';

let userInfoString = localStorage.getItem('userInfo');
let userInfo = ref(userInfoString.split(','));

const items = ref();

async function getPerfiles() {
    await axios.get('http://127.0.0.1:7777/getPerfiles', { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']){
                items.value=response.data['user'];
            }else{
                alert(response.data['message']);
            }
        }
    }).catch(error => {
        // Manejar errores de la solicitud
        console.log(error);
    });
}

onBeforeMount(getPerfiles);

async function logout() {
    await axios.post('http://127.0.0.1:7777/logoutUser', null, { withCredentials: true }).then(response => {
        if (response.status === 200) {
            // Redirigir al usuario a la página de inicio de sesión
            router.push("/login");
        }
    }).catch(error => {
        // Manejar errores de la solicitud
        console.log(error);
    });
}
</script>

<style scoped>
:deep(.p-submenu-header) {
    background-color: #343a40 !important;
    color: white;
    font-size: 14px;
}

:deep(.p-button) {
    border: 0cap;
}
</style>
