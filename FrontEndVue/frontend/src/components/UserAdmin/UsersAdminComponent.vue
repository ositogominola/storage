<template>
    <Card>
        <template #title>Administracion de usuarios</template>
        <template #content>
            <ProgressSpinner v-if="loadingPage" />
            <DataTable v-else :value="DatosUsuarios" paginator :rows="5" :rowsPerPageOptions="[5, 10, 20, 50]"
                tableStyle="min-width: 50rem">
                <Column field="idUser" header="id" style="width: 350px;"></Column>
                <Column field="name" header="nombre" sortable></Column>
                <Column field="email" header="correo" sortable></Column>
                <Column field="roles.name" header="rol" sortable></Column>
                <Column header="Acciones">
                    <template #body="slotProps">
                        <Button v-if="comprobarPermiso(58)" label="Cambiar rol" @click="getRoles(slotProps.data.idUser)" />
                    </template>
                </Column>
            </DataTable>
            <Dialog v-model:visible="rolVisible" header="Asignar roles" modal :style="{ width: '25rem' }">
                <span class="p-text-secondary block mb-5">Selecciona rol</span>
                <Dropdown v-model="rolAsignado" :options="roles" placeholder="Select One" class="p-column-filter" showClear>
                    <template #value="slotProps">
                        <div v-if="slotProps.value" class="flex align-items-center">
                            <div>{{ slotProps.value.name }}</div>
                        </div>
                    </template>
                    <template #option="slotProps">
                        <Tag :value="slotProps.option.name" :severity="none"/>
                    </template>
                </Dropdown>
                <Button type="button" label="Save" @click="actualizarRolUsuario" />

            </Dialog>
        </template>
    </Card>
</template>

<script setup>
import Card from 'primevue/card';
import DataTable from 'primevue/datatable';
import Button from 'primevue/button';
import axios from 'axios';
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import Column from 'primevue/column';

//TABLASDATOS

const DatosUsuarios = ref();
const roles = ref();


//Selects



//DialogVisible
const rolVisible = ref(false);
const rolAsignado = ref();

//Otros
const idUser = ref();
const router = useRouter();
const permisosRecursoActual = ref();
const loadingPage = ref(true);


//GET

async function getPermisosRecurso() {
    let userInfoString = localStorage.getItem('userInfo');
    let userInfo = userInfoString.split(',');
    let currentPath = router.currentRoute.value.path;
    await axios.post('http://127.0.0.1:7777/getPermisoRecursos/rol/' + userInfo[5], { UrlRecurso: router.currentRoute.value.path } ,{ withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                loadingPage.value=false;
                permisosRecursoActual.value = response.data['permisos'].map(lista => ({
                    ID: lista[0],
                    NAME: lista[1]
                }));
            } else {
                permisosRecursoActual.value = null;
                toast.add({ severity: 'error', summary: 'Error', detail: response.data['message'], life: 3000 });
            }

        }
    }).catch(error => {
        // Manejar errores de la solicitud
        console.log(error);
    });
}

async function getUsuarios() {
    await axios.get('http://127.0.0.1:7777/getAllUsers', { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                DatosUsuarios.value = response.data['users'];
            } else {
                alert(response.data['message']);
            }

        }
    }).catch(error => {
        // Manejar errores de la solicitud
        console.log(error);
    });
}

async function getRoles(idUserSelect) {
    idUser.value = idUserSelect;
    await axios.get('http://127.0.0.1:7777/getRoles', { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                roles.value = response.data['roles'];
                rolVisible.value = true;
            } else {
                alert(response.data['message']);
            }

        }
    }).catch(error => {
        // Manejar errores de la solicitud
        console.log(error);
    });
}


//POST



//PUT

async function actualizarRolUsuario() {
    console.log('http://127.0.0.1:7777/addRol/' + rolAsignado.value.idRol + '/us/' + idUser.value);
    await axios.put('http://127.0.0.1:7777/addRol/' + rolAsignado.value.idRol + '/us/' + idUser.value, null, { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                getUsuarios();
                rolVisible.value = false;
            } else {
                alert(response.data['message']);
            }

        }
    }).catch(error => {
        // Manejar errores de la solicitud
        console.log(error);
    });
}

//DELETE



//UTILS

function comprobarPermiso(id) {
    return permisosRecursoActual.value.some(objeto => objeto.ID == id);
}

//FLUJO
onMounted(()=>{
    getPermisosRecurso();
    getUsuarios();
} )

</script>