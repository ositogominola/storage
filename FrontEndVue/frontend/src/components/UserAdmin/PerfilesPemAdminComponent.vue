<template>
    <Card>
        <template #title>Perfiles</template>
        <template #content>
            <TabView v-model:active-index="activeIndex">
                <TabPanel header="Perfiles">
                    <Toolbar class="mb-4">
                        <template #start>
                            <Button label="Perfil Nuevo" icon="pi pi-plus" severity="success" class="mr-2"
                                @click="CreatePerfilVisible = true;" />
                            <Button label="Eliminar" icon="pi pi-trash" severity="danger"
                                :disabled="!PerfilesSelects || !PerfilesSelects.length" @click="confirmDelete" />
                        </template>
                    </Toolbar>
                    <Toast />
                    <ConfirmDialog></ConfirmDialog>
                    <DataTable :value="PerfilesData" paginator :rows="5" :rowsPerPageOptions="[5, 10, 20, 50]" sortable
                        v-model:filters="filtersPermisos" filterDisplay="menu" selectionMode="multiple"
                        v-model:selection="PerfilesSelects">

                        <Column selectionMode="multiple" style="width: 3rem" :exportable="false"></Column>

                        <Column field="idPerfil" header="Id" style="width: 20px" sortable>
                            <template #filter="{ filterModel }">
                                <InputText v-model="filterModel.value" type="text" class="p-column-filter"
                                    placeholder="Buscar por Id" />
                            </template>
                        </Column>

                        <Column field="nombre" header="Nombre" style="width: 50%" sortable>
                            <template #filter="{ filterModel }">
                                <InputText v-model="filterModel.value" type="text" class="p-column-filter"
                                    placeholder="Buscar por Id" />
                            </template>
                        </Column>

                        <Column field="idPerfil" header="Acciones">
                            <template #body="slotProps">
                                <Button icon="pi pi-eye" @click="getPeriflesItems(slotProps.data.idPerfil, slotProps.data.nombre)"/>
                                <Button icon="pi pi-trash" severity="danger" />
                                <Button icon="pi pi-pencil" severity='secondary' />
                            </template>
                        </Column>
                    </DataTable>

                    <Dialog v-model:visible="CreatePerfilVisible" modal header="Crear permiso"
                        :style="{ width: '70rem' }">
                        <InlineMessage v-if="mesageError">comprete los datos</InlineMessage>
                        <span class="p-text-secondary block mb-5">Ingresar los datos.</span>
                        <div class="flex align-items-center gap-3 mb-3">
                            <label for="url" class="font-semibold w-6rem">URL</label>
                            <InputText id="url" class="flex-auto" autocomplete="off" v-model="url" />
                        </div>
                        <div class="flex justify-content-end gap-2">
                            <Button type="button" label="Cancel" severity="secondary"
                                @click="CreatePerfilVisible = false"></Button>
                            <Button type="button" label="Crear" @click="CreatePermiso"></Button>
                        </div>
                    </Dialog>
                </TabPanel>
                <TabPanel header="Perfiles Items">
                    <DataTable>
                        <Card>
                            <template #title>Perfiles Items de Perfil {{ nombrePerfil }}</template>

                            <template #content>
                                <DataTable :value="PerfilesItemsData">
                                    <Column field="ID", header="ID"></Column>
                                </DataTable>
                            </template>

                        </Card>
                    </DataTable>
                </TabPanel>
            </TabView>


        </template>
    </Card>
</template>

<script setup>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { FilterMatchMode } from 'primevue/api';
import { useToast } from "primevue/usetoast";
import { useConfirm } from "primevue/useconfirm";

import Toast from 'primevue/toast';
import Card from 'primevue/card';
import DataTable from 'primevue/datatable';
import Button from 'primevue/button';
import Toolbar from 'primevue/toolbar';
import ConfirmDialog from 'primevue/confirmdialog';
import InlineMessage from 'primevue/inlinemessage';
import TabView from 'primevue/tabview';
import TabPanel from 'primevue/tabpanel';
import Column from 'primevue/column';

const toast = useToast();
const confirm = useConfirm();
const activeIndex = ref(0);

//Variables Entorno
const idPerfilSel=ref();
const nombrePerfil=ref();

//DatosTablas
const PermisosData = ref();
const PerfilesData = ref();
const PerfilesItemsData = ref();

//Recurso
const url = ref();
const metodo = ref();
const mesageError = ref(false);

//datosFaltantes



//DialogVisible

const CreatePerfilVisible = ref(false);

//Selects
const PerfilesSelects = ref();



//GET

async function getAllPerfiles() {
    await axios.get('http://127.0.0.1:7777/getAllPerfiles', { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                PerfilesData.value = response.data['data']
            } else {
                alert(response.data['message']);
            }
        }
    }).catch(error => {
        // Manejar errores de la solicitud
        console.log(error);
    });
}

async function getPermisos() {
    await axios.get('http://127.0.0.1:7777/getAllPermission', { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                PermisosData.value = response.data['user'];
            } else {
                alert(response.data['message']);
            }
        }
    }).catch(error => {
        // Manejar errores de la solicitud
        console.log(error);
    });
}

async function getPeriflesItems(idPerfil, nombre){
    activeIndex.value = 1;
    idPerfilSel.value=idPerfil;
    nombrePerfil.value=nombre;

    await axios.get('http://127.0.0.1:7777/getAllPerfilesItemsByPerfil/'+idPerfil, { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                PerfilesItemsData.value = response.data['data'].map(item=>{
                    return {
                        ID: item[0],
                        NOMBRE: item[1],
                        URL: item[2]
                    }
                }
                );;
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

const CreatePermiso = async () => {
    if (url.value != null && metodo.value != null) {
        const json = {
            url: url.value,
        }
        await axios.post('http://127.0.0.1:7777/create_permission', json, { withCredentials: true }).then(response => {
            if (response.status === 200) {
                if (response.data['successful']) {
                    getPermisos();
                    toast.add({ severity: 'success', summary: 'Rol creado', detail: 'El permiso fue creado', life: 3000 });
                    CreatePerfilVisible.value = false;
                } else {
                    toast.add({ severity: 'error', summary: 'Error', detail: response.data['message'], life: 3000 });
                }

            }
        }).catch(error => {
            // Manejar errores de la solicitud
            toast.add({ severity: 'contrast', summary: 'Error', detail: 'Error del servidor', life: 3000 });
        });
    } else {
        mesageError.value = true;
    }

}

//PUT



//DELETE

async function deletePermiso(idPermission) {
    await axios.delete('http://127.0.0.1:7777/deletePermission/' + idPermission, { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                toast.add({ severity: 'success', summary: 'Permiso Eliminado', detail: 'El permiso fue eliminado', life: 3000 });
            } else {
                toast.add({ severity: 'error', summary: 'Error', detail: response.data['message'], life: 3000 });
            }

        }
    }).catch(error => {
        // Manejar errores de la solicitud
        toast.add({ severity: 'contrast', summary: 'Error', detail: 'Error del servidor', life: 3000 });
    });
}

//UTILS

const methods = [
    'GET',
    'POST',
    'PUT',
    'PATCH',
    'DELETE'
];

const filtersPermisos = ref({
    idPermission: { value: null, matchMode: FilterMatchMode.STARTS_WITH },
    url: { value: null, matchMode: FilterMatchMode.CONTAINS },
    method: { value: null, matchMode: FilterMatchMode.EQUALS },
});

const confirmDelete = () => {
    confirm.require({
        message: 'Seguro que quiere eliminar estos permisos?',
        header: 'Danger Zone',
        icon: 'pi pi-info-circle',
        rejectLabel: 'Cancelar',
        acceptLabel: 'Eliminar',
        rejectClass: 'p-button-secondary p-button-outlined',
        acceptClass: 'p-button-danger',
        accept: () => {
            DeletePermissionSelect();
            toast.add({ severity: 'info', summary: 'Confirmed', detail: 'Eliminando permisos', life: 3000 });
        },
        reject: () => {
            toast.add({ severity: 'error', summary: 'Rejected', detail: 'Proceso cancelado', life: 3000 });
        }
    });
};

const DeletePermissionSelect = async () => {
    const promises = PerfilesSelects.value.map(item => deletePermiso(item['idPermission']));
    await Promise.all(promises);
    toast.add({ severity: 'Info', summary: 'Permisos eliminados', detail: 'los permisos fueron eliminados', life: 3000 });
    getPermisos();
}

const getSeverity = (status) => {
    switch (status) {
        case 'DELETE':
            return 'danger';

        case 'GET':
            return 'success';

        case 'POST':
            return 'warning';

        case 'PUT':
            return 'info';

        case 'PATCH':
            return 'secondary';
    }
};

//FLUJO

onMounted(getAllPerfiles);

</script>