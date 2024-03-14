<template>
    <Card>
        <template #title>Permisos</template>
        <template #content>
            <Toolbar class="mb-4">
                <template #start>
                    <Button label="Permiso Nuevo" icon="pi pi-plus" severity="success" class="mr-2"
                        @click="CreatePermisoVisible = true;" />
                    <Button label="Eliminar" icon="pi pi-trash" severity="danger"
                        :disabled="!PermisosSelect || !PermisosSelect.length" @click="confirmDelete" />
                </template>
            </Toolbar>
            <Toast />
            <ConfirmDialog></ConfirmDialog>
            <DataTable :value="Permisos" paginator :rows="5" :rowsPerPageOptions="[5, 10, 20, 50]" sortable
                v-model:filters="filtersPermisos" filterDisplay="menu" selectionMode="multiple"
                v-model:selection="PermisosSelect" :globalFilterFields="['idPermission', 'url', 'method']">

                <Column selectionMode="multiple" style="width: 3rem" :exportable="false"></Column>

                <Column field="idPermission" header="Id" style="width: 20px" sortable>
                    <template #filter="{ filterModel }">
                        <InputText v-model="filterModel.value" type="text" class="p-column-filter"
                            placeholder="Buscar por Id" />
                    </template>
                </Column>

                <Column field="method" header="Metodo" sortable>
                    <template #body="{ data }">
                        <Tag :value="data.method" :severity="getSeverity(data.method)" />
                    </template>
                    <template #filter="{ filterModel }">
                        <Dropdown v-model="filterModel.value" :options="methods" placeholder="Select One"
                            class="p-column-filter" showClear>
                            <template #option="slotProps">
                                <Tag :value="slotProps.option" :severity="getSeverity(slotProps.option)" />
                            </template>
                        </Dropdown>
                    </template>
                </Column>

                <Column field="url" header="URL" sortable>
                    <template #filter="{ filterModel, filterCallback }">
                        <InputText v-model="filterModel.value" type="text" @input="filterCallback()" class="p-column-filter"
                            placeholder="Search by url" />
                    </template>
                </Column>

                <Column field="idPermission" header="Acciones">
                    <template #body="slotProps">
                        <Button label="Modificar" />
                    </template>
                </Column>
            </DataTable>

            <Dialog v-model:visible="CreatePermisoVisible" modal header="Crear permiso" :style="{ width: '70rem' }">
                <InlineMessage v-if="mesageError">comprete los datos</InlineMessage>
                <span class="p-text-secondary block mb-5">Ingresar los datos.</span>
                <div class="flex align-items-center gap-3 mb-3">
                    <label for="url" class="font-semibold w-6rem">URL</label>
                    <InputText id="url" class="flex-auto" autocomplete="off" v-model="url" />
                </div>
                <div class="flex align-items-center gap-3 mb-3">
                    <label for="method" class="font-semibold w-6rem">Metodo</label>
                    <Dropdown v-model="metodo" :options="methods" placeholder="Select One" showClear>
                        <template #option="slotProps">
                            <Tag :value="slotProps.option" :severity="getSeverity(slotProps.option)" />
                        </template>
                    </Dropdown>
                </div>
                <div class="flex justify-content-end gap-2">
                    <Button type="button" label="Cancel" severity="secondary"
                        @click="CreatePermisoVisible = false"></Button>
                    <Button type="button" label="Crear" @click="CreatePermiso"></Button>
                </div>
            </Dialog>
        </template>
    </Card>
</template>

<script setup>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { FilterMatchMode} from 'primevue/api';
import { useToast } from "primevue/usetoast";
import { useConfirm } from "primevue/useconfirm";

import Toast from 'primevue/toast';
import Card from 'primevue/card';
import DataTable from 'primevue/datatable';
import Button from 'primevue/button';
import Toolbar from 'primevue/toolbar';
import ConfirmDialog from 'primevue/confirmdialog';
import InlineMessage from 'primevue/inlinemessage';

const toast = useToast();
const confirm = useConfirm();

const Permisos = ref();
const PermisosSelect = ref();
const CreatePermisoVisible = ref(false);
const url = ref();
const metodo = ref();
const mesageError = ref(false);

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

const CreatePermiso = async () => {
    if (url.value != null && metodo.value != null) {
        const json = {
            url: url.value,
            method: metodo.value
        }
        await axios.post('http://127.0.0.1:7777/create_permission', json, { withCredentials: true }).then(response => {
            if (response.status === 200) {
                if (response.data['successful']) {
                    cargarPermisos();
                    toast.add({ severity: 'success', summary: 'Rol creado', detail: 'El permiso fue creado', life: 3000 });
                    CreatePermisoVisible.value = false;
                } else {
                    toast.add({ severity: 'error', summary: 'Error', detail: response.data['message'], life: 3000 });
                }

            }
        }).catch(error => {
            // Manejar errores de la solicitud
            toast.add({ severity: 'contrast', summary: 'Error', detail: 'Error del servidor', life: 3000 });
        });
    }else{
        mesageError.value=true;
    }

}

async function cargarPermisos() {
    await axios.get('http://127.0.0.1:7777/getAllPermission', { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                Permisos.value = response.data['user'];
            } else {
                alert(response.data['message']);
            }
        }
    }).catch(error => {
        // Manejar errores de la solicitud
        console.log(error);
    });
}

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
    const promises = PermisosSelect.value.map(item => deletePermiso(item['idPermission']));
    await Promise.all(promises);
    toast.add({ severity: 'Info', summary: 'Permisos eliminados', detail: 'los permisos fueron eliminados', life: 3000 });
    cargarPermisos();
}

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

onMounted(cargarPermisos);

</script>