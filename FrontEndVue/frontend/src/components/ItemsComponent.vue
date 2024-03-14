<template>
    <div class="container">
        <div class="row">
            <router-link v-for="(item, index) in Recursos" :key="index" :to="item[2]" style="margin: 10px;" class="col-md-4 CardItem">
                <Card :style="{ width: '100%', overflow: 'hidden', backgroundColor: item[3]}">
                    <template #title>{{ item[1] }}</template>
                    <template #subtitle>sub</template>
                    <template #content>
                        <Avatar image="https://primefaces.org/cdn/primevue/images/avatar/amyelsner.png" class="mr-6"
                            shape="circle" />
                    </template>
                </Card>
            </router-link>
        </div>
    </div>
</template>


<script setup>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();

const Recursos = ref();
const id = ref(null);
const rol=ref(null);

async function getRecursos() {
    await axios.get('http://127.0.0.1:7777/getRecursosPerfil/'+id.value+"/rol/"+rol.value, { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                Recursos.value = response.data['user'];
            } else {
                alert(response.data['message']);
            }
        }
    }).catch(error => {
        // Manejar errores de la solicitud
        console.log(error);
    });
}

onMounted(() => {
    id.value = route.params.id;
    rol.value=route.params.rol;
    getRecursos();
});

</script>