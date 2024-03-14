<template>
    <Card>
        <template #title>Administracion de usuarios</template>
        <template #content>
            <DataTable :value="usuarios" paginator :rows="5" :rowsPerPageOptions="[5, 10, 20, 50]"
                tableStyle="min-width: 50rem">
                <Column field="idUser" header="id" style="width: 350px;"></Column>
                <Column field="name" header="nombre" sortable></Column>
                <Column field="email" header="correo" sortable></Column>
                <Column field="roles.name" header="rol" sortable></Column>
                <Column header="Acciones">
                    <template #body="slotProps">
                        <Button label="Cambiar rol" @click="cargarRoles(slotProps.data.idUser)" />
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
                        <Tag :value="slotProps.option.name" />
                    </template>
                </Dropdown>
                <Button type="button" label="Save" @click="asignarRolUsuario" />

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
import Column from 'primevue/column';

const usuarios = ref();
const rolVisible = ref(false);
const roles = ref();
const rolAsignado = ref();
const idUser = ref();



async function cargarUsuarios() {
    await axios.get('http://127.0.0.1:7777/getAllUsers', { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                usuarios.value = response.data['users'];
            } else {
                alert(response.data['message']);
            }

        }
    }).catch(error => {
        // Manejar errores de la solicitud
        console.log(error);
    });
}

async function cargarRoles(idUserSelect) {
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

async function asignarRolUsuario() {
    console.log('http://127.0.0.1:7777/addRol/' + rolAsignado.value.idRol + '/us/' + idUser.value);
    await axios.put('http://127.0.0.1:7777/addRol/' + rolAsignado.value.idRol + '/us/' + idUser.value, null, { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                cargarUsuarios();
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


onMounted(cargarUsuarios);
</script>