<template>
    <Card>
        <template #title>Roles</template>

        <template #content>

            <ProgressSpinner v-if="loadingPage" />

            <TabView v-else v-model:activeIndex="activeIndex" v-if="comprobarPermiso(44)">
                <ConfirmDialog />
                <TabPanel header="Ver Roles">
                    <Toast />
                    <Toolbar class="mb-4">
                        <template #start>
                            <Button label="Rol Nuevo" icon="pi pi-plus" severity="success" class="mr-2"
                                @click="CreateRolvisible = true;" v-if="comprobarPermiso(32)" />
                        </template>
                    </Toolbar>
                    <div class="card">
                        <Skeleton v-if="loading" type="Datatable" size="large" />
                        <DataTable v-else :value="roles" paginator :rows="5" :rowsPerPageOptions="[5, 10, 20, 50]"
                            tableStyle="min-width: 50rem">

                            <Column field="idRol" header="Id" style="width: 25%"></Column>
                            <Column field="name" header="Nombre" style="width: 25%"></Column>
                            <Column header="Acciones" style="width: 25%">

                                <template #body="slotProps">
                                    <Button @click="getPerfilesRol(slotProps.data.idRol, slotProps.data.name)">
                                        ver perfiles
                                    </Button>
                                    <Button v-if="comprobarPermiso(74)" @click="confirmDeleteRol(slotProps.data.idRol)"
                                        severity="danger">
                                        Eliminar Rol
                                    </Button>
                                </template>
                            </Column>
                        </DataTable>
                    </div>

                    <Dialog v-model:visible="CreateRolvisible" modal header="Create Rol" :style="{ width: '70rem' }">
                        <span class="p-text-secondary block mb-5">Ingresar los datos.</span>
                        <div class="flex align-items-center gap-3 mb-3">
                            <label for="RolName" class="font-semibold w-6rem">Nombre</label>
                            <InputText id="RolName" class="flex-auto" autocomplete="off" v-model="RolNameCreate" />
                        </div>
                        <div class="flex justify-content-end gap-2">
                            <Button type="button" label="Cancel" severity="secondary"
                                @click="CreateRolvisible = false"></Button>
                            <Button type="button" label="Crear" @click="CreateRol"></Button>
                        </div>
                    </Dialog>
                </TabPanel>
                <TabPanel header="Administral Perfiles">
                    <Card>
                        <Toast />

                        <template #title>Perfiles del rol {{ rol }}</template>

                        <template #content>
                            <Toolbar class="mb-4">
                                <template #start>
                                    <Button v-if="comprobarPermiso(72)" label="Añadir Perfil" icon="pi pi-plus"
                                        severity="success" class="mr-2" @click="getPerfilesFaltantes"
                                        :disabled="!idRolSelect" />
                                    <Button v-if="comprobarPermiso(75)" label="Delete" icon="pi pi-trash"
                                        severity="danger" :disabled="!selectedPeriles || !selectedPeriles.length"
                                        @click="confirmDelete" />
                                </template>
                            </Toolbar>
                            <Skeleton v-if="loading" :type="Datatable" />
                            <DataTable v-else :value="perfiles" paginator :rows="5"
                                :rowsPerPageOptions="[5, 10, 20, 50]" sortable v-model:filters="filters"
                                filterDisplay="menu" selectionMode="multiple" v-model:selection="selectedPeriles"
                                :globalFilterFields="['perfil', 'perfilItem', 'urlFront']">

                                <Column selectionMode="multiple" style="width: 3rem" :exportable="false"></Column>
                                <Column field="perfil" header="Perfil" sortable style="width: 50px">

                                    <template #filter="{ filterModel }">
                                        <Dropdown v-model="filterModel.value" :options="perfilesFilter"
                                            placeholder="Select One" class="p-column-filter" showClear>
                                            <template #option="slotProps">
                                                {{ slotProps.option }}
                                            </template>
                                        </Dropdown>
                                    </template>
                                </Column>

                                <Column field="perfilItem" header="PerfilItem" sortable>

                                    <template #body="{ data }">
                                        {{ data.perfilItem }}
                                    </template>

                                    <template #filter="{ filterModel, filterCallback }">
                                        <InputText v-model="filterModel.value" type="text" @input="filterCallback()"
                                            class="p-column-filter" placeholder="Search by Name" />
                                    </template>
                                </Column>

                                <Column field="urlFront" header="URL FRONT" sortable filterMatchMode="equals"
                                    style="width: 25%">

                                    <template #body="{ data }">
                                        {{ data.urlFront }}
                                    </template>

                                    <template #filter="{ filterModel, filterCallback }">
                                        <InputText v-model="filterModel.value" type="text" @input="filterCallback()"
                                            class="p-column-filter" placeholder="Search by url" />
                                    </template>
                                </Column>

                                <Column field="idPerfil" header="Recursos">

                                    <template #body="slotProps">
                                        <Button label="Ver Recursos" @click="getRecursosRolPerfil(slotProps.data);" />
                                        <Button v-if="comprobarPermiso(75)" severity="danger" label="Eliminar Perfil"
                                            @click="confirmDeletePerfilRol(slotProps.data);" />
                                    </template>
                                </Column>

                            </DataTable>
                        </template>
                    </Card>
                    <Dialog v-model:visible="PerfilesVisible" header="Añadir perifles" :style="{ width: '75vw' }"
                        maximizable modal :contentStyle="{ height: '400px' }">
                        <DataTable :value="perfilesFaltantes" sortable tableStyle="min-width: 50rem" paginator :rows="3"
                            :rowsPerPageOptions="[3, 6, 10, 50]" filterDisplay="menu"
                            v-model:selection="selectPerfilesAdd" selectionMode="multiple" v-model:filters="filters"
                            :globalFilterFields="['URL', 'NOMBREPERFIL', 'NAME']">

                            <Column selectionMode="multiple" style="width: 3rem" :exportable="false"></Column>
                            <Column field="ID" header="ID" sortable></Column>
                            <Column field="NAME" header="Nombre" sortable>
                                <template #filter="{ filterModel, filterCallback }">
                                    <InputText v-model="filterModel.value" type="text" @input="filterCallback()"
                                        class="p-column-filter" placeholder="Search by Name" />
                                </template>
                            </Column>

                            <Column field="URL" header="URL" sortable>
                                <template #filter="{ filterModel, filterCallback }">
                                    <InputText v-model="filterModel.value" type="text" @input="filterCallback()"
                                        class="p-column-filter" placeholder="Search by url" />
                                </template>
                            </Column>

                            <Column field="NOMBREPERFIL" header="Perfil" sortable>
                                <template #filter="{ filterModel }">
                                    <Dropdown v-model="filterModel.value" :options="perfilesFilterFaltantes"
                                        placeholder="Select One" class="p-column-filter" showClear>
                                        <template #option="slotProps">
                                            {{ slotProps.option }}
                                        </template>
                                    </Dropdown>
                                </template>
                            </Column>


                            <Column field="ID" header="Opciones">

                                <template #body="slotProps">
                                    <Button label="Añadir" @click="AñadirPerfil(slotProps.data.ID, idRolSelect)" />
                                </template>
                            </Column>
                        </DataTable>

                        <template #footer>
                            <Button label="Añadir Seleccionados"
                                :disabled="!selectPerfilesAdd || !selectPerfilesAdd.length"
                                @click="addPerfilesSelect" />
                            <Button label="Ok" icon="pi pi-check" @click="PerfilesVisible = false" />
                        </template>
                    </Dialog>
                </TabPanel>
                <TabPanel header="Añadir Recursos">
                    <Card>
                        <Toast />

                        <template #title>Recursos del perfilItem {{ rol }}</template>

                        <template #content>
                            <ConfirmDialog></ConfirmDialog>
                            <Toolbar class="mb-4">
                                <template #start>
                                    <Button v-if="comprobarPermiso(76)" label="Añadir Recurso" icon="pi pi-plus"
                                        severity="success" class="mr-2" @click="getRecursosFaltantes()"
                                        :disabled="!idRolSelect" />
                                    <Button v-if="comprobarPermiso(78)" label="Delete" icon="pi pi-trash"
                                        severity="danger" :disabled="!selectedRecursos || !selectedRecursos.length"
                                        @click="confirmDeleteRecursosSelect" />
                                </template>
                            </Toolbar>
                            <Skeleton v-if="loading" :type="Datatable" />

                            <DataTable v-else :value="recursosPerfil" paginator :rows="5"
                                :rowsPerPageOptions="[5, 10, 20, 50]" sortable v-model:filters="filters"
                                filterDisplay="menu" v-model:selection="selectedRecursos"
                                :expandedRows="getpermisosRecursos" @rowExpand="getPermission"
                                @rowCollapse="onRowCollapse" :globalFilterFields="['ID', 'NAME', 'URL']">

                                <Column :bodyStyle="{ backgroundColor: isExpanded ? '#f0f0f0' : 'inherit' }" expander style="width: 5rem" />
                                <Column selectionMode="multiple" style="width: 3rem" :exportable="false" />
                                <Column field="ID" header="id" sortable style="width: 50px">

                                    <template #filter="{ filterModel }">
                                        <Dropdown v-model="filterModel.value" :options="perfilesFilter"
                                            placeholder="Select One" class="p-column-filter" showClear>
                                            <template #option="slotProps">
                                                {{ slotProps.option }}
                                            </template>
                                        </Dropdown>
                                    </template>
                                </Column>

                                <Column field="NAME" header="Name" sortable>

                                    <template #body="{ data }">
                                        {{ data.NAME }}
                                    </template>

                                    <template #filter="{ filterModel, filterCallback }">
                                        <InputText v-model="filterModel.value" type="text" @input="filterCallback()"
                                            class="p-column-filter" placeholder="Search by url" />
                                    </template>
                                </Column>

                                <Column field="URL" header="URL FRONT" sortable filterMatchMode="equals"
                                    style="width: 25%">

                                    <template #body="{ data }">
                                        {{ data.URL }}
                                    </template>

                                    <template #filter="{ filterModel, filterCallback }">
                                        <InputText v-model="filterModel.value" type="text" @input="filterCallback()"
                                            class="p-column-filter" placeholder="Search by url" />
                                    </template>
                                </Column>

                                <Column field="ID" header="Acciones">

                                    <template #body="slotProps">
                                        <Button v-if="comprobarPermiso(3)" label="Asignar Permisos"
                                            @click="getPermisosFaltantes(slotProps.data.ID)" />
                                        <Button label="Eliminar Recurso"
                                            @click="confirmDeleteRecursos(slotProps.data.ID)" severity="danger" />
                                    </template>
                                </Column>

                                <template #expansion>
                                    <DataTable style="background-color: #ad8989;" :value="permisosRecursos" v-model:selection="selectedPermisos" paginator
                                        :rows="5" :rowsPerPageOptions="[5, 10, 20, 50]" filterDisplay="menu"
                                        v-model:filters="filtersPermisos" :globalFilterFields="['METHOD']">
                                        <template #header>
                                            <div style="text-align: left">
                                                <Button icon="pi pi-trash" severity="danger" label="delete"
                                                    :disabled="!selectedPermisos || !selectedPermisos.length"
                                                    @click="confirmDeletePermisoSelect" />
                                            </div>
                                        </template>
                                        <Column selectionMode="multiple" style="width: 3rem" :exportable="false" />
                                        <Column field="ID" header="Id" sortable></Column>
                                        <Column field="NAME" header="nombre" sortable></Column>
                                        <Column field="URL" header="url" sortable></Column>
                                        <Column field="METHOD" header="Metodo" sortable>
                                            <template #body="slotProps">
                                                <Tag :value="slotProps.data.METHOD"
                                                    :severity="getSeverity(slotProps.data.METHOD)" />
                                            </template>
                                            <template #filter="{ filterModel, filterCallback }">
                                                <MultiSelect v-model="filterModel.value" @change="filterCallback()"
                                                    :options="methods" placeholder="Any" class="p-column-filter"
                                                    style="min-width: 14rem" :maxSelectedLabels="1">
                                                    <template #option="slotProps">
                                                        <Tag :value="slotProps.option" :severity="getSeverity(slotProps.option)" />
                                                    </template>
                                                </MultiSelect>
                                            </template>
                                        </Column>

                                        <Column field="ID" header="Acciones">
                                            <template #body="slotProps">
                                                <Button v-if="comprobarPermiso(50)" icon="pi pi-trash"
                                                    @click="confirmDeletePermiso(slotProps.data.ID)"
                                                    severity="danger" />
                                            </template>
                                        </Column>
                                    </DataTable>
                                </template>
                            </DataTable>

                            <Dialog v-model:visible="RecursosVisible" header="Añadir Recursos"
                                :style="{ width: '75vw' }" maximizable modal :contentStyle="{ height: '400px' }">
                                <DataTable :value="recursosFaltantes" sortable tableStyle="min-width: 50rem" paginator
                                    :rows="3" :rowsPerPageOptions="[3, 6, 10, 50]" filterDisplay="menu"
                                    v-model:selection="selectedRecursosAdd" selectionMode="multiple">


                                    <Column selectionMode="multiple" style="width: 3rem" :exportable="false"></Column>
                                    <Column field="ID" header="id" sortable style="width: 50px" />
                                    <Column field="NAME" header="Nombre" sortable></Column>
                                    <Column field="URL" header="URL" sortable></Column>


                                    <Column field="ID" header="Opciones">

                                        <template #body="slotProps">
                                            <Button label="Añadir"
                                                @click="AñadirRecursoRol(slotProps.data.ID, idRolSelect)" />
                                        </template>
                                    </Column>
                                </DataTable>

                                <template #footer>
                                    <Button label="Añadir Seleccionados"
                                        :disabled="!selectedRecursosAdd || !selectedRecursosAdd.length"
                                        @click="addRecursosSelect" />
                                    <Button label="Ok" icon="pi pi-check" @click="RecursosVisible = false" />
                                </template>
                            </Dialog>

                            <Dialog v-model:visible="PermisosVisible" header="Añadir Permiso" :style="{ width: '75vw' }"
                                maximizable modal :contentStyle="{ height: '400px' }">
                                <DataTable :value="permisosFaltantes" sortable tableStyle="min-width: 50rem" paginator
                                    :rows="3" :rowsPerPageOptions="[3, 6, 10, 50]" v-model:selection="selectPermisosAdd"
                                    filterDisplay="menu" v-model:filters="filters" :globalFilterFields="['METHOD']">

                                    <Column selectionMode="multiple" style="width: 3rem" :exportable="false" />
                                    <Column field="ID" header="Id" sortable></Column>
                                    <Column field="NAME" header="nombre" sortable></Column>
                                    <Column field="URL" header="url" sortable></Column>
                                    <Column field="METHOD" header="Metodo" sortable>
                                        <template #body="{ data }">
                                            <div class="flex align-items-center gap-2">
                                                <Tag :value="data.METHOD" :severity="getSeverity(data.METHOD)" />
                                            </div>
                                        </template>
                                        <template #filter="{ filterModel, filterCallback }">
                                            <MultiSelect v-model="filterModel.value" @change="filterCallback()"
                                                :options="methods" placeholder="Any" class="p-column-filter"
                                                style="min-width: 14rem" :maxSelectedLabels="1">
                                                <template #option="slotProps">
                                                    <Tag :value="slotProps.option" :severity="getSeverity(slotProps.option)" />
                                                </template>
                                            </MultiSelect>
                                        </template>
                                    </Column>
                                    <Column field="GROUP" header="Opciones">
                                        <template #body="slotProps">
                                            <Button label="Añadir" @click="AñadirPermisosRol(slotProps.data.GROUP)" />
                                        </template>
                                    </Column>
                                </DataTable>

                                <template #footer>
                                    <Button label="Añadir Seleccionados"
                                        :disabled="!selectPermisosAdd || !selectPermisosAdd.length"
                                        @click="addPermisosSelect" />
                                    <Button label="Ok" icon="pi pi-check" @click="PermisosVisible = false" />
                                </template>
                            </Dialog>

                        </template>
                    </Card>
                </TabPanel>
            </TabView>
        </template>
    </Card>
</template>

<script setup>
import { nextTick, computed } from 'vue';
import axios from 'axios';
import Button from 'primevue/button';
import Card from 'primevue/card';
import DataTable from 'primevue/datatable';
import TabView from 'primevue/tabview';
import TabPanel from 'primevue/tabpanel';
import { ref, onMounted } from 'vue';
import { FilterMatchMode, FilterOperator } from 'primevue/api';
import Toolbar from 'primevue/toolbar';
import { useToast } from "primevue/usetoast";
import { useConfirm } from "primevue/useconfirm";
import Toast from 'primevue/toast';
import ConfirmDialog from 'primevue/confirmdialog';
import Skeleton from 'primevue/skeleton';
import ProgressSpinner from 'primevue/progressspinner';
import { useRouter } from 'vue-router';


import { useStore } from 'vuex'

// Usa la tienda Vuex
const store = useStore()
const router = useRouter();

const roles = ref();
const perfiles = ref();
const activeIndex = ref(0);
const rol = ref();
const toast = useToast();
const confirm = useConfirm();
const loading = ref(false);
const RolNameCreate = ref(null);
const perfilesFilter = ref([]);
const recursosPerfil = ref([]);
const permisosRecursos = ref([]);
const idPerfil = ref();
const idRecursoSelect = ref();
const perfilesFilterFaltantes = ref([]);
const perfilSlc = ref([]);

//datosFaltantes
const recursosFaltantes = ref([]);
const permisosFaltantes = ref();
const perfilesFaltantes = ref();

//DialogVisible
const RecursosVisible = ref(false);
const PermisosVisible = ref(false);
const PerfilesVisible = ref(false);
const CreateRolvisible = ref(false);

//Selects
const idRolSelect = ref();
const selectedPeriles = ref();
const selectedRecursos = ref();
const selectedPermisos = ref();
const selectPerfilesAdd = ref();
const selectedRecursosAdd = ref();
const selectPermisosAdd = ref();

//recurso
const permisosRecursoActual = ref();
const loadingPage = ref(true);
const isExpanded=false;
//GET
async function getRoles() {
    loading.value = true;
    await axios.get('http://127.0.0.1:7777/getRoles', { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                roles.value = response.data['roles'];
                loading.value = false;
            } else {
                alert(response.data['message']);
            }

        }
    }).catch(error => {
        // Manejar errores de la solicitud
        console.log(error);
    });
}

async function getRecursosFaltantes() {
    RecursosVisible.value = true;
    await axios.get('http://127.0.0.1:7777/getRecursosRolesFalt/' + idRolSelect.value + '/perfil/' + idPerfil.value, { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                toast.add({ severity: 'success', summary: 'success', detail: response.data['message'], life: 3000 });
                recursosFaltantes.value = response.data['permisos'].map(lista => {
                    return {
                        ID: lista[0],
                        NAME: lista[1],
                        URL: lista[2],
                    };
                });
                console.log("pueba");
            } else {
                recursosFaltantes.value = null;
                toast.add({ severity: 'error', summary: 'Error', detail: response.data['message'], life: 3000 });
            }

        }
    }).catch(error => {
        // Manejar errores de la solicitud
        console.log(error);
    });
}

async function traerPermisosAsignados(id, rol) {
    try {
        const response = await axios.get(`http://127.0.0.1:7777/getRecursosPermisos/${id}/rol/${rol}`, { withCredentials: true });

        if (response.status === 200 && response.data['successful']) {
            toast.add({ severity: 'info', summary: response.data['message'], life: 3000 });
            return response.data['permisos'].map(lista => ({
                ID: lista[0],
                URL: lista[1],
                METHOD: lista[2],
                NAME: lista[3]
            }));
        } else {
            toast.add({ severity: 'error', summary: response.data['message'], life: 3000 });
            return []; // Retorna un array vacío en caso de error
        }
    } catch (error) {
        console.error('Error al obtener los datos:', error);
        toast.add({ severity: 'error', summary: 'Error', detail: 'Error al obtener los datos', life: 3000 });
        return []; // Retorna un array vacío en caso de error
    }
}

async function getPerfilesRol(idRol, nombre) {
    loading.value = true;
    activeIndex.value = 1;
    rol.value = nombre;
    idRolSelect.value = idRol;
    await axios.get('http://127.0.0.1:7777/getPerfilesRoles/' + idRol, { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                perfiles.value = response.data['perfilesRol'].map(lista => {
                    const perfil = lista[0];
                    if (!perfilesFilter.value.includes(perfil)) {
                        perfilesFilter.value.push(perfil);
                    }
                    return {
                        perfil: lista[0],
                        perfilItem: lista[1],
                        urlFront: lista[3],
                        idPerfil: lista[4]
                    };
                });
                loading.value = false;
            } else {
                alert(response.data['message']);
            }

        }
    }).catch(error => {
        // Manejar errores de la solicitud
        console.log(error);
    });
}

async function getRecursosRolPerfil(data) {
    recursosPerfil.value = null;
    perfilSlc.value = data;
    idPerfil.value = data.idPerfil;
    await axios.get('http://127.0.0.1:7777/getRecursosPerfilItem/' + data.idPerfil + "/rol/" + idRolSelect.value, { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                recursosPerfil.value = response.data['recursos'].map(lista => {
                    return {
                        ID: lista[0],
                        NAME: lista[1],
                        URL: lista[2],
                        expanded: false
                    };
                });
                loading.value = false;
                console.log(perfilesFilter.value);
            } else {
                toast.add({ severity: 'error', summary: 'Error', detail: response.data['message'], life: 3000 });
            }

        }
    }).catch(error => {
        // Manejar errores de la solicitud
        console.log(error);
    });

    activeIndex.value = 2;
}

async function getPerfilesFaltantes() {
    PerfilesVisible.value = true;
    perfilesFaltantes.value = null;
    await axios.get('http://127.0.0.1:7777/getPerfilesRolFaltante/' + idRolSelect.value, { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                perfilesFaltantes.value = response.data['permisos'].map(item => {
                    const perfil = item[3];
                    if (!perfilesFilterFaltantes.value.includes(perfil)) {
                        perfilesFilterFaltantes.value.push(perfil);
                    }
                    return {
                        ID: item[0],
                        NAME: item[1],
                        URL: item[2],
                        NOMBREPERFIL: item[3]
                    };
                });
            } else {
                perfilesFaltantes.value = null;
                toast.add({ severity: 'error', summary: 'Error', detail: response.data['message'], life: 3000 });
            }

        }
    }).catch(error => {
        // Manejar errores de la solicitud
        console.log(error);
    });
}

async function getPermisosFaltantes(id) {
    PermisosVisible.value = true;
    await axios.get('http://127.0.0.1:7777/getPermisosRecursoRolFaltante/' + id + '/rol/' + idRolSelect.value, { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                permisosFaltantes.value = response.data['permisos'].map(lista => ({
                    ID: lista[0],
                    URL: lista[1],
                    METHOD: lista[2],
                    NAME: lista[3],
                    GROUP: lista[4]
                }));
            } else {
                permisosFaltantes.value = null;
                toast.add({ severity: 'error', summary: 'Error', detail: response.data['message'], life: 3000 });
            }

        }
    }).catch(error => {
        // Manejar errores de la solicitud
        console.log(error);
    });
}

async function getPermisosRecurso() {
    const recursoIdSe = computed(() => store.state.idRecurso)
    let userInfoString = localStorage.getItem('userInfo');
    let userInfo = userInfoString.split(',');

    await axios.post('http://127.0.0.1:7777/getPermisoRecursos/rol/' + userInfo[5], { UrlRecurso: router.currentRoute.value.path }, { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                loadingPage.value = false;
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

//POST

const CreateRol = async () => {
    const json = {
        name: RolNameCreate.value
    }
    await axios.post('http://127.0.0.1:7777/create_role', json, { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                getRoles();
                toast.add({ severity: 'success', summary: 'Rol creado', detail: 'El rol fue creado', life: 3000 });
                CreateRolvisible.value = false;
            } else {
                toast.add({ severity: 'error', summary: 'Error', detail: response.data['message'], life: 3000 });
            }

        }
    }).catch(error => {
        // Manejar errores de la solicitud
        toast.add({ severity: 'contrast', summary: 'Error', detail: 'Error del servidor', life: 3000 });
    });
}

async function AñadirPerfil(idPermission, idRol) {
    PerfilesVisible.value = true;
    await axios.post('http://127.0.0.1:7777/addPerfilRol/' + idRol + '/perfil/' + idPermission, null, { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                getPerfilesRol(idRolSelect.value, rol.value);
                getPerfilesFaltantes();
                toast.add({ severity: 'success', summary: 'Perfil Añadido', detail: response.data['message'], life: 3000 });
            } else {
                toast.add({ severity: 'error', summary: 'Error', detail: response.data['message'], life: 3000 });
            }

        }
    }).catch(error => {
        // Manejar errores de la solicitud
        toast.add({ severity: 'contrast', summary: 'Error', detail: 'Error del servidor', life: 3000 });
    });
}

async function AñadirRecursoRol(idRecurso, idRol) {
    await axios.post('http://127.0.0.1:7777/addRecursoRol/' + idRecurso + '/rol/' + idRol, null, { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                getRecursosFaltantes();
                const perfil = { idPerfil: idPerfil.value }
                getRecursosRolPerfil(perfil);
                toast.add({ severity: 'success', summary: 'Permiso Añadido', detail: 'El permiso fue Añadido', life: 3000 });
            } else {
                toast.add({ severity: 'error', summary: 'Error', detail: 'No se pudo añadir el Permiso', life: 3000 });
            }

        }
    }).catch(error => {
        // Manejar errores de la solicitud
        toast.add({ severity: 'contrast', summary: 'Error', detail: 'Error del servidor', life: 3000 });
    });
}

async function AñadirPermisosRol(idPermissionGroup) {
    recursosPerfil.value.forEach(item => {
        item.expanded = false;
    });
    await axios.post('http://127.0.0.1:7777/addPermission/' + idPermissionGroup + '/rol/' + idRolSelect.value, null, { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {

                getPermisosFaltantes(idRecursoSelect.value);
                toast.add({ severity: 'success', summary: 'Permiso Añadido', detail: response.data['message'], life: 3000 });
            } else {
                toast.add({ severity: 'error', summary: 'Error', detail: response.data['message'], life: 3000 });
            }

        }
    }).catch(error => {
        // Manejar errores de la solicitud
        toast.add({ severity: 'contrast', summary: 'Error', detail: 'Error del servidor', life: 3000 });
    });
}

//PUT



//DELETE
async function deleteRecursos(idRecurso) {
    await axios.delete('http://127.0.0.1:7777/deleteRecursosRol/' + idRecurso + '/rol/' + idRolSelect.value, { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                toast.add({ severity: 'success', summary: 'Permiso Eliminado', detail: response.data['message'], life: 3000 });
                getRecursosRolPerfil(perfilSlc.value);
            } else {
                toast.add({ severity: 'error', summary: 'Error', detail: response.data['message'], life: 3000 });
            }

        }
    }).catch(error => {
        // Manejar errores de la solicitud
        toast.add({ severity: 'contrast', summary: 'Error', detail: 'Error del servidor', life: 3000 });
    });
}

async function deletePermisoRol(idPermission) {
    await axios.delete('http://127.0.0.1:7777/deletePermisoRol/' + idPermission + '/rol/' + idRolSelect.value, { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                toast.add({ severity: 'success', summary: 'Permiso Eliminado', detail: response.data['message'], life: 3000 });
            } else {
                toast.add({ severity: 'error', summary: 'Error', detail: 'No se pudo eliminar el Permiso', life: 3000 });
            }
        }
    }).catch(error => {
        // Manejar errores de la solicitud
        toast.add({ severity: 'contrast', summary: 'Error', detail: 'Error del servidor: ' + error, life: 3000 });
    });
}

async function deleteRol(idRol) {
    await axios.delete('http://127.0.0.1:7777/deleteRol/' + idRol, { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                toast.add({ severity: 'success', summary: 'Permiso Eliminado', detail: response.data['message'], life: 3000 });
                getRoles();
            } else {
                toast.add({ severity: 'error', summary: 'Error', detail: response.data['message'], life: 3000 });
            }

        }
    }).catch(error => {
        // Manejar errores de la solicitud
        toast.add({ severity: 'contrast', summary: 'Error', detail: 'Error del servidor', life: 3000 });
    });
}

async function deletePerfilRol(idPerfilSl) {
    await axios.delete('http://127.0.0.1:7777/deletePerfilRol/' + idPerfilSl.idPerfil + '/rol/' + idRolSelect.value, { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                toast.add({ severity: 'success', summary: 'Permiso Eliminado', detail: response.data['message'], life: 3000 });
                getPerfilesRol(idRolSelect.value, rol.value);
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

const filters = ref({
    perfil: { value: null, matchMode: FilterMatchMode.EQUALS },
    perfilItem: { value: null, matchMode: FilterMatchMode.CONTAINS },
    urlFront: { value: null, matchMode: FilterMatchMode.CONTAINS },
    URL: { value: null, matchMode: FilterMatchMode.CONTAINS },
    NOMBREPERFIL: { value: null, matchMode: FilterMatchMode.CONTAINS },
    NAME: { value: null, matchMode: FilterMatchMode.CONTAINS },
    METHOD: { value: null, matchMode: FilterMatchMode.IN },
});

const filtersPermisos = ref({
    METHOD: { value: null, matchMode: FilterMatchMode.IN },
});

const onRowCollapse = (event) => {
    const id = event.data.ID;
    const index = recursosPerfil.value.findIndex(item => item.ID === id);
    permisosRecursos.value = [];
    if (index !== -1) {
        recursosPerfil.value[index].expanded = false; // Actualiza la propiedad expanded
    }
};

const methods = [
    'GET',
    'POST',
    'PUT',
    'PATCH',
    'DELETE'
];

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

const getpermisosRecursos = computed(() => {
    if (!Array.isArray(recursosPerfil.value)) {
        // Verifica si recursosPerfil no es un array
        console.error('recursosPerfil no es un array');
        return []; // Retorna un array vacío si recursosPerfil no es un array válido
    }
    return recursosPerfil.value.filter(item => item.expanded);
});

const addPerfilesSelect = async () => {
    PerfilesVisible.value = true;
    const promises = selectPerfilesAdd.value.map(item => AñadirPerfil(item['ID'], idRolSelect.value));
    await Promise.all(promises);
    toast.add({ severity: 'info', summary: 'perfiles Añadidos', detail: 'los perfiles fueron añadidos', life: 3000 });
    getPerfilesRol(idRolSelect.value, rol.value);
    getPerfilesFaltantes();
    selectPerfilesAdd.value = null;
}

const addPermisosSelect = async () => {
    PermisosVisible.value = true;
    const promises = selectPermisosAdd.value.map(item => AñadirPermisosRol(item['GROUP']));
    await Promise.all(promises);
    toast.add({ severity: 'info', summary: 'permisos Añadidos', detail: 'los perfiles fueron añadidos', life: 3000 });
    selectPermisosAdd.value = null;
}

const addRecursosSelect = async () => {
    const promises = selectedRecursosAdd.value.map(item => AñadirRecursoRol(item['ID'], idRolSelect.value));
    await Promise.all(promises);
    toast.add({ severity: 'info', summary: 'Recursos Añadidos', detail: 'los perfiles fueron añadidos', life: 3000 });
    selectedRecursosAdd.value = null;
}

const confirmDelete = () => {
    confirm.require({
        message: 'Seguro que quiere eliminar estos perfiles?',
        header: 'Danger Zone',
        icon: 'pi pi-info-circle',
        rejectLabel: 'Cancelar',
        acceptLabel: 'Eliminar',
        rejectClass: 'p-button-secondary p-button-outlined',
        acceptClass: 'p-button-danger',
        accept: () => {
            DeletePerfilesSelect();
            toast.add({ severity: 'info', summary: 'Confirmed', detail: 'Eliminando permisos', life: 3000 });
        },
        reject: () => {
            toast.add({ severity: 'error', summary: 'Rejected', detail: 'Proceso cancelado', life: 3000 });
        }
    });
};

const confirmDeleteRol = (idRol) => {
    confirm.require({
        message: 'Esto tambien eliminara todos los perfiles, recursos y permisos que el rol tiene asignados',
        header: 'Seguro que quiere eliminar el rol?',
        icon: 'pi pi-info-circle',
        rejectLabel: 'Cancelar',
        acceptLabel: 'Eliminar',
        rejectClass: 'p-button-secondary p-button-outlined',
        acceptClass: 'p-button-danger',
        accept: () => {
            deleteRol(idRol);
        },
        reject: () => {
            toast.add({ severity: 'error', summary: 'Rejected', detail: 'Proceso cancelado', life: 3000 });
        }
    });
};

const confirmDeletePerfilRol = (idPerfilSl) => {
    confirm.require({
        message: 'Esto tambien eliminara todos los recursos y permisos que el perfil tiene asignados',
        header: 'Seguro que quiere eliminar el perfil?',
        icon: 'pi pi-info-circle',
        rejectLabel: 'Cancelar',
        acceptLabel: 'Eliminar',
        rejectClass: 'p-button-secondary p-button-outlined',
        acceptClass: 'p-button-danger',
        accept: () => {
            deletePerfilRol(idPerfilSl);
        },
        reject: () => {
            toast.add({ severity: 'error', summary: 'Rejected', detail: 'Proceso cancelado', life: 3000 });
        }
    });
};

const confirmDeleteRecursos = (idRecursos) => {
    confirm.require({
        message: 'Esto tambien eliminara todos los permisos asignados al recurso',
        header: 'Seguro que quiere eliminar el recurso?',
        icon: 'pi pi-info-circle',
        rejectLabel: 'Cancelar',
        acceptLabel: 'Eliminar',
        rejectClass: 'p-button-secondary p-button-outlined',
        acceptClass: 'p-button-danger',
        accept: () => {
            deleteRecursos(idRecursos);
        },
        reject: () => {
            toast.add({ severity: 'error', summary: 'Rejected', detail: 'Proceso cancelado', life: 3000 });
        }
    });
};

const confirmDeletePermiso = (idPermission) => {
    confirm.require({
        header: 'Seguro que quiere eliminar el permiso?',
        icon: 'pi pi-info-circle',
        rejectLabel: 'Cancelar',
        acceptLabel: 'Eliminar',
        rejectClass: 'p-button-secondary p-button-outlined',
        acceptClass: 'p-button-danger',
        accept: () => {
            deletePermisoRol(idPermission);
            recursosPerfil.value.forEach(item => {
                item.expanded = false;
            });
        },
        reject: () => {
            toast.add({ severity: 'error', summary: 'Rejected', detail: 'Proceso cancelado', life: 3000 });
        }
    });
};

const confirmDeletePermisoSelect = () => {
    confirm.require({
        header: 'Seguro que quiere eliminar los permisos seleccionados?',
        icon: 'pi pi-info-circle',
        rejectLabel: 'Cancelar',
        acceptLabel: 'Eliminar',
        rejectClass: 'p-button-secondary p-button-outlined',
        acceptClass: 'p-button-danger',
        accept: () => {
            DeletedPermisosSelect();
            recursosPerfil.value.forEach(item => {
                item.expanded = false;
            });
        },
        reject: () => {
            toast.add({ severity: 'error', summary: 'Rejected', detail: 'Proceso cancelado', life: 3000 });
        }
    });
};

const confirmDeleteRecursosSelect = () => {
    confirm.require({
        header: 'Seguro que quiere eliminar los recursos seleccionados??',
        icon: 'pi pi-info-circle',
        rejectLabel: 'Cancelar',
        acceptLabel: 'Eliminar',
        rejectClass: 'p-button-secondary p-button-outlined',
        acceptClass: 'p-button-danger',
        accept: () => {
            deleteRecursosSelect();
        },
        reject: () => {
            toast.add({ severity: 'error', summary: 'Rejected', detail: 'Proceso cancelado', life: 3000 });
        }
    });
}

const deleteRecursosSelect = async () => {
    const promises = selectedRecursos.value.map(item => deleteRecursos(item['ID']));
    await Promise.all(promises);
    selectedRecursos.value = null;
    toast.add({ severity: 'success', summary: 'Recursos eliminados', detail: 'los recursos fueron eliminados', life: 3000 });
}

const DeletedPermisosSelect = async () => {
    const promises = selectedPermisos.value.map(item => deletePermisoRol(item['ID']));
    await Promise.all(promises);
    selectedPermisos.value = null;
    toast.add({ severity: 'success', summary: 'Perfiles eliminados', detail: 'los perfiles fueron eliminados', life: 3000 });
}

const DeletePerfilesSelect = async () => {
    const promises = selectedPeriles.value.map(item => deletePerfilRol(item));
    await Promise.all(promises);
    selectedPeriles.value = null;
    toast.add({ severity: 'success', summary: 'Perfiles eliminados', detail: 'los perfiles fueron eliminados', life: 3000 });
    getPerfilesRol(idRolSelect.value, rol.value);
}

const getPermission = async (event) => {
    try {
        event.originalEvent.preventDefault();
        const id = event.data.ID;
        idRecursoSelect.value = id;
        recursosPerfil.value.forEach(item => {
            if (item.ID !== id && item.expanded) {
                item.expanded = false;
            }
        });
        isExpanded: false
        const index = recursosPerfil.value.findIndex(item => item.ID === id);
        if (index !== -1) {
            const rol = idRolSelect.value;
            const data = await traerPermisosAsignados(id, rol);
            permisosRecursos.value = data;
            recursosPerfil.value[index].expanded = true; // Actualiza la propiedad expanded
        }
    } catch (error) {
        alert(error.message);
    }
};

function comprobarPermiso(id) {
    return permisosRecursoActual.value.some(objeto => objeto.ID == id);
}

function getDireccion(){
    
}

//FLUJO
onMounted(() => {
    getPermisosRecurso();
    getRoles();
});


</script>

<style scoped>
/* Estilos CSS personalizados para resaltar la columna expandida */
.expanded-table {
  background-color: #ad8989;
}
</style>