<template>
    <Card>
        <template #title>Perfiles</template>
        <template #content>
            <TabView v-model:active-index="activeIndex">
                <TabPanel header="Perfiles">
                    <Toolbar class="mb-4">
                        <template #start>
                            <Button label="Perfil Nuevo" icon="pi pi-plus" severity="success" class="mr-2"
                                @click="createPerfilVisible()" />
                            <Button label="Permisos globales" icon="pi pi-plus" severity="info" class="mr-2"
                                @click="getPermisosGlobales" />
                            <Button label="Eliminar" icon="pi pi-trash" severity="danger"
                                :disabled="!PerfilesSelects || !PerfilesSelects.length" @click="confirmDelete" />
                        </template>
                    </Toolbar>
                    <Toast />
                    <ConfirmDialog></ConfirmDialog>
                    <DataTable :value="PerfilesData" paginator :rows="5" :rowsPerPageOptions="[5, 10, 20, 50]" sortable
                        v-model:filters="filtersPerfiles" filterDisplay="menu" selectionMode="multiple"
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
                                <Button icon="pi pi-eye" label="Perfiles"
                                    @click="getPeriflesItems(slotProps.data.idPerfil, slotProps.data.nombre)" />
                                <Button icon="pi pi-trash" severity="danger" />
                                <Button icon="pi pi-pencil" severity='secondary'
                                    @click="updatePerfilVisible(slotProps.data)" />
                            </template>
                        </Column>
                    </DataTable>

                    <Dialog v-model:visible="CreatePerfilVisible" modal header="Crear perfil"
                        :style="{ width: '70rem' }">
                        <InlineMessage v-if="mesageError">comprete los datos</InlineMessage>
                        <span class="p-text-secondary block mb-5">Ingresar los datos.</span>
                        <div class="flex align-items-center gap-3 mb-3">
                            <label for="Name" class="font-semibold w-6rem">Nombre</label>
                            <InputText id="NameNewPerfil" class="flex-auto" autocomplete="off"
                                v-model="nombrePerfilCreate" />
                        </div>
                        <div class="flex justify-content-end gap-2">
                            <Button type="button" label="Cancel" severity="secondary"
                                @click="CreatePerfilVisible = false"></Button>
                            <Button type="button" label="Crear" @click="CreatePerfil" />
                        </div>
                    </Dialog>

                    <Dialog v-model:visible="PermisosGlobalesVisible" modal header="Permisos Globales"
                        :style="{ width: '100rem' }">
                        <Toolbar class="mb-4">
                            <template #start>
                                <Button label="Nuevo Permiso" icon="pi pi-plus" severity="success" class="mr-2"
                                    @click="createPermisosVisible" />
                                <Button label="Eliminar" icon="pi pi-trash" severity="danger"
                                    :disabled="!PermisosGlobalesSelects || !PermisosGlobalesSelects.length"
                                    @click="confirmDelete" />
                            </template>
                        </Toolbar>
                        <DataTable :value="PermisosGlobalesData" paginator :rows="5"
                            :rowsPerPageOptions="[5, 10, 20, 50]" sortable v-model:filters="filtersPermisos"
                            filterDisplay="menu" v-model:selection="PermisosGlobalesSelects"
                            :globalFilterFields="['idPermission', 'nombre', 'url', 'method', 'recursoNombre', 'grupoPermiso']">

                            <Column selectionMode="multiple" style="width: 3rem" :exportable="false"></Column>

                            <Column field="idPermission" header="Id" sortable>
                                <template #filter="{ filterModel }">
                                    <InputText v-model="filterModel.value" type="text" class="p-column-filter"
                                        placeholder="Buscar por Id" />
                                </template>
                            </Column>

                            <Column field="nombre" header="Nombre" sortable>
                                <template #filter="{ filterModel, filterCallback }">
                                    <InputText v-model="filterModel.value" type="text" class="p-column-filter"
                                        placeholder="Buscar por nombre" @input="filterCallback()" />
                                </template>
                            </Column>

                            <Column field="url" header="URL" sortable>
                                <template #filter="{ filterModel, filterCallback }">
                                    <InputText v-model="filterModel.value" type="text" class="p-column-filter"
                                        placeholder="Buscar por Url" @input="filterCallback()" />
                                </template>
                            </Column>

                            <Column field="method" header="Metodo" sortable>
                                <template #body="slotProps">
                                    <Tag :value="slotProps.data.method"
                                        :severity="getSeverity(slotProps.data.method)" />
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

                            <Column field="urlIgnore" header="URL Libre" sortable>
                                <template #body="slotProps">
                                    <i v-if="slotProps.data.urlIgnore == true" class="fa fa-check"></i>
                                    <i v-else class="fa fa-times"></i>
                                </template>
                            </Column>

                            <Column field="permisoRequerido" header="Requerido" sortable>
                                <template #body="slotProps">
                                    <i v-if="slotProps.data.permisoRequerido == true" class="fa fa-check"></i>
                                    <i v-else class="fa fa-times"></i>
                                </template>
                            </Column>

                            <Column field="pertRecurso" header="Pertenece recurso" sortable>
                                <template #body="slotProps">
                                    <i v-if="slotProps.data.pertRecurso == true" class="fa fa-check"></i>
                                    <i v-else class="fa fa-times"></i>
                                </template>
                            </Column>

                            <Column field="recursoNombre" header="Recurso" sortable>
                                <template #filter="{ filterModel, filterCallback }">
                                    <MultiSelect v-model="filterModel.value" @change="filterCallback()"
                                        :options="recursosNombres" placeholder="Any" class="p-column-filter"
                                        style="min-width: 14rem" :maxSelectedLabels="1">
                                        <template #option="slotProps">
                                            {{ slotProps.option }}
                                        </template>
                                    </MultiSelect>
                                </template>
                            </Column>

                            <Column field="grupoPermiso" header="Grupo" sortable>
                                <template #body="slotProps">
                                    {{ (slotProps.data.grupoPermiso == -1) ? '' : slotProps.data.grupoPermiso }}
                                </template>
                                <template #filter="{ filterModel, filterCallback }">
                                    <InputText v-model="filterModel.value" type="text" class="p-column-filter"
                                        placeholder="Buscar por Grupo" @input="filterCallback()" />
                                </template>
                            </Column>

                            <Column field="idPermission" header="Acciones">
                                <template #body="slotProps">
                                    <Button icon="pi pi-trash" severity="danger"
                                        @click="confirmDeletePermiso(slotProps.data.idPermission)" />
                                    <Button icon="pi pi-pencil" severity='secondary'
                                        @click="updatePermisoVisible(slotProps.data)" />
                                </template>
                            </Column>

                        </DataTable>
                        <div class="flex justify-content-end gap-2">
                            <Button type="button" label="Cancel" severity="secondary"
                                @click="PermisosGlobalesVisible = false"></Button>
                        </div>
                    </Dialog>

                    <Dialog v-model:visible="PermisoUpdateVisible" modal header="Actualizar permiso"
                        :style="{ width: '75rem' }">

                        <div class="row">
                            <div class="col">
                                <InputGroup>
                                    <InputGroupAddon>
                                        <i class="pi pi-angle-right"></i>
                                    </InputGroupAddon>
                                    <InputText placeholder="Nombre" v-model="permisoUpdate.nombre"
                                        :disabled="!permisoUpdate.pertRecurso" />
                                </InputGroup>
                            </div>
                            <div class="col">
                                <InputGroup>
                                    <InputGroupAddon>
                                        <i class="pi pi-bars"></i>
                                    </InputGroupAddon>
                                    <InputText placeholder="Url" v-model="permisoUpdate.url" />
                                </InputGroup>
                            </div>
                            <div class="col">
                                <InputGroup>
                                    <InputGroupAddon>
                                        <i class="pi pi-flag"></i>
                                    </InputGroupAddon>
                                    <Dropdown v-model="permisoUpdate.method" :options="methods">
                                        <template #option="slotProps">
                                            <div>
                                                <Tag :value="slotProps.option"
                                                    :severity="getSeverity(slotProps.option)" />
                                            </div>
                                        </template>
                                    </Dropdown>
                                </InputGroup>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col">
                                <InputGroup>
                                    <InputGroupAddon>
                                        <i class="pi pi-book"></i>
                                    </InputGroupAddon>
                                    <AutoComplete placeholder="Recurso" v-model="permisoUpdate.recursoNombre"
                                        @item-select="onSelect" dropdown :suggestions="ItemsRecursos" @complete="search"
                                        :disabled="!permisoUpdate.pertRecurso" style="color: black;">
                                        <template #option="slotProps">
                                            <div class="flex align-options-center">
                                                <div> {{ slotProps.option.nameFront }} </div>
                                            </div>
                                        </template>
                                    </AutoComplete>
                                </InputGroup>
                            </div>

                            <div class="col">
                                <InputGroup>
                                    <InputGroupAddon>
                                        <i class="pi pi-th-large"></i>
                                    </InputGroupAddon>
                                    <InputNumber v-model="displayedGrupoPermiso" inputId="integeronly" />
                                </InputGroup>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col">
                                <div class="flex align-items-center">
                                    <InputSwitch v-model="permisoUpdate.permisoRequerido" />
                                    <label for="ingredient1" class="ml-2"> Requerido </label>
                                </div>
                            </div>
                            <div class="col">
                                <div class="flex align-items-center">
                                    <InputSwitch v-model="permisoUpdate.pertRecurso" />
                                    <label for="ingredient1" class="ml-2"> Recurso </label>
                                </div>
                            </div>
                            <div class="col">
                                <div class="flex align-items-center">
                                    <InputSwitch v-model="permisoUpdate.urlIgnore" />
                                    <label for="ingredient1" class="ml-2"> URL libre </label>
                                </div>
                            </div>
                            <div class="col">
                                <div class="flex align-items-center">
                                    <Button label="Guardar" icon="pi pi-check" @click="updatePermiso()" />
                                </div>
                            </div>
                        </div>

                    </Dialog>

                    <Dialog v-model:visible="PermisoCreateVisible" modal header="Crear permiso"
                        :style="{ width: '75rem' }">
                        <InlineMessage v-if="mesageError">comprete los datos</InlineMessage>
                        <div class="row">
                            <div class="col">
                                <InputGroup>
                                    <InputGroupAddon>
                                        <i class="pi pi-angle-right"></i>
                                    </InputGroupAddon>
                                    <InputText placeholder="Nombre" v-model="permisoCreate.nombre"
                                        :disabled="!permisoCreate.pertRecurso" />
                                </InputGroup>
                            </div>
                            <div class="col">
                                <InputGroup>
                                    <InputGroupAddon>
                                        <i class="pi pi-bars"></i>
                                    </InputGroupAddon>
                                    <InputText placeholder="Url" v-model="permisoCreate.url" />
                                </InputGroup>
                            </div>
                            <div class="col">
                                <InputGroup>
                                    <InputGroupAddon>
                                        <i class="pi pi-flag"></i>
                                    </InputGroupAddon>
                                    <Dropdown v-model="permisoCreate.method" :options="methods">
                                        <template #option="slotProps">
                                            <div>
                                                <Tag :value="slotProps.option"
                                                    :severity="getSeverity(slotProps.option)" />
                                            </div>
                                        </template>
                                    </Dropdown>
                                </InputGroup>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col">
                                <InputGroup>
                                    <InputGroupAddon>
                                        <i class="pi pi-book"></i>
                                    </InputGroupAddon>
                                    <AutoComplete placeholder="Recurso" v-model="permisoCreate.recursoNombre"
                                        @item-select="onSelectCreacte" dropdown :suggestions="ItemsRecursos"
                                        @complete="search" :disabled="!permisoCreate.pertRecurso" style="color: black;">
                                        <template #option="slotProps">
                                            <div class="flex align-options-center">
                                                <div> {{ slotProps.option.nameFront }} </div>
                                            </div>
                                        </template>
                                    </AutoComplete>
                                </InputGroup>
                            </div>

                            <div class="col">
                                <InputGroup>
                                    <InputGroupAddon>
                                        <i class="pi pi-th-large"></i>
                                    </InputGroupAddon>
                                    <InputNumber v-model="displayedGrupoPermisoCreate" inputId="integeronly" />
                                </InputGroup>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col">
                                <div class="flex align-items-center">
                                    <InputSwitch v-model="permisoCreate.permisoRequerido" />
                                    <label for="ingredient1" class="ml-2"> Requerido </label>
                                </div>
                            </div>
                            <div class="col">
                                <div class="flex align-items-center">
                                    <InputSwitch v-model="permisoCreate.pertRecurso" />
                                    <label for="ingredient1" class="ml-2"> Recurso </label>
                                </div>
                            </div>
                            <div class="col">
                                <div class="flex align-items-center">
                                    <InputSwitch v-model="permisoCreate.urlIgnore" />
                                    <label for="ingredient1" class="ml-2"> URL libre </label>
                                </div>
                            </div>
                            <div class="col">
                                <div class="flex align-items-center">
                                    <Button label="Crear" icon="pi pi-check" @click="CreatePermiso()" />
                                </div>
                            </div>
                        </div>

                    </Dialog>

                    <Dialog v-model:visible="PerfilUpdateVisible" modal header="Actualizar perfil">
                        <InlineMessage v-if="mesageError">comprete los datos</InlineMessage>
                        <span class="p-text-secondary block mb-5">Ingresar los datos.</span>
                        <div class="flex align-items-center gap-3 mb-3">
                            <label for="Name" class="font-semibold w-6rem">Nombre</label>
                            <InputText id="nombrePerfilCreate" class="flex-auto" autocomplete="off"
                                v-model="perfilUpdate.nombre" />
                        </div>
                        <div class="flex justify-content-end gap-2">
                            <Button type="button" label="Cancel" severity="secondary"
                                @click="PerfilUpdateVisible = false"></Button>
                            <Button type="button" label="Actualizar" @click="updatePerfil" />
                        </div>
                    </Dialog>

                </TabPanel>
                <TabPanel header="Perfiles Items">
                    <Card>
                        <template #title>Perfiles Items de Perfil {{ nombrePerfil }}</template>

                        <template #content>
                            <Toolbar class="mb-4">
                                <template #start>
                                    <Button label="PerfilItem Nuevo" icon="pi pi-plus" severity="success" class="mr-2"
                                        @click="PerfilesCreateVisible = true" :disabled="idPerfilSel==null"/>
                                    <Button label="Eliminar" icon="pi pi-trash" severity="danger" />
                                </template>
                            </Toolbar>
                            <Toast />
                            <DataTable :value="PerfilesItemsData">
                                <Column field="ID" , header="ID"></Column>
                                <Column field="NOMBRE" header="Nombre"></Column>
                                <Column field="URL" header="URL Front"></Column>
                                <Column field="ICON" header="Icon">
                                    <template #body="slotProps">
                                        <span :class="slotProps.data.ICON" />
                                    </template>
                                </Column>
                                <Column field="ID" header="Acciones">
                                    <template #body="slotProps">
                                        <Button icon="pi pi-eye" label="Recursos"
                                            @click="getRecursosPerfilItem(slotProps.data.ID, slotProps.data.NOMBRE)"></Button>
                                        <Button icon="pi pi-trash" severity="danger" />
                                        <Button icon="pi pi-pencil" severity='secondary'></Button>
                                    </template>
                                </Column>
                            </DataTable>

                            <Dialog v-model:visible="PerfilesCreateVisible" modal header="Crear perfilItem"
                                :style="{ width: '75rem' }">

                                <div class="row">
                                    <div class="col">
                                        <InputGroup>
                                            <InputGroupAddon>
                                                <i class="pi pi-angle-right"></i>
                                            </InputGroupAddon>
                                            <InputText placeholder="Nombre" v-model="perfilCreate.nombre" />
                                        </InputGroup>
                                    </div>
                                    <div class="col">
                                        <InputGroup>
                                            <InputGroupAddon>
                                                <i class="pi pi-angle-right"></i>
                                            </InputGroupAddon>
                                            <InputText placeholder="url" v-model="perfilCreate.url" />
                                        </InputGroup>
                                    </div>

                                </div>
                                <div class="row">
                                    <div class="col">
                                        <InputGroup>
                                            <InputGroupAddon>
                                                <i class="pi pi-flag"></i>
                                            </InputGroupAddon>
                                            <Dropdown :options="icons" v-model="perfilCreate.icon" optionLabel="name" placeholder="Icon">
                                                <template #value="slotProps">
                                                    <div v-if="slotProps.value" class="flex align-items-center">
                                                        <i :class="slotProps.value.icon" style="margin-right: 2px;"></i>
                                                        <div>{{ slotProps.value.name }}</div>
                                                    </div>
                                                    <span v-else>
                                                        {{ slotProps.placeholder }}
                                                    </span>
                                                </template>
                                                <template #option="slotProps">
                                                    <div class="flex align-items-center">
                                                        <i :class="slotProps.option.icon"
                                                            style="margin-right: 2px;"></i>
                                                        <div>{{ slotProps.option.name }}</div>
                                                    </div>
                                                </template>
                                            </Dropdown>
                                        </InputGroup>
                                    </div>
                                </div>
                            </Dialog>

                        </template>
                    </Card>
                </TabPanel>
                <TabPanel header="Recursos">
                    <Card>
                        <template #title>Recursos del PerfilItem {{ nombrePerfilItem }}</template>

                        <template #content>
                            <Toolbar class="mb-4">
                                <template #start>
                                    <Button label="Recurso Nuevo" icon="pi pi-plus" severity="success" class="mr-2" />
                                    <Button label="Eliminar" icon="pi pi-trash" severity="danger" />
                                </template>
                            </Toolbar>
                            <Toast />
                            <DataTable :value="RecursoData">
                                <Column field="ID" , header="ID"></Column>
                                <Column field="NOMBRE" header="Nombre"></Column>
                                <Column field="URL" header="URL Front"></Column>
                                <Column field="COLOR" header="Color">
                                    <template #body="slotProps">
                                        <div
                                            :style="{ backgroundColor: slotProps.data.COLOR, width: '35px', height: '35px', border: '1px solid white', borderRadius: '5px' }">
                                        </div>
                                    </template>
                                </Column>
                                <Column field="ICON" header="Icon">
                                    <template #body="slotProps">
                                        <span :class="slotProps.ICON" />
                                    </template>
                                </Column>
                                <Column field="ID" header="Acciones">
                                    <template #body="slotProps">
                                        <Button icon="pi pi-eye" label="Permisos"></Button>
                                        <Button icon="pi pi-trash" severity="danger" />
                                        <Button icon="pi pi-pencil" severity='secondary'></Button>
                                    </template>
                                </Column>
                            </DataTable>
                        </template>

                    </Card>
                </TabPanel>
            </TabView>


        </template>
    </Card>
</template>

<script setup>
import axios from 'axios';
import { ref, onMounted, computed } from 'vue';
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
import InputNumber from 'primevue/inputnumber';
import ColorPicker from 'primevue/colorpicker';
import AutoComplete from 'primevue/autocomplete';
import InputGroup from 'primevue/inputgroup';
import InputGroupAddon from 'primevue/inputgroupaddon';
import Dialog from 'primevue/dialog';


const toast = useToast();
const confirm = useConfirm();
const activeIndex = ref(0);

//Variables Entorno
const idPerfilSel = ref();
const nombrePerfil = ref();
const idPerfilItemsel = ref();
const permisoUpdate = ref();
const AllRecursos = ref([]);
const ItemsRecursos = ref([]);
const recursosNombres = ref([]);
const perfilUpdate = ref();
const perfilCreate = ref({
    "icon": null,
    "nombre": null,
    "url_front": null,
    "perfiles_id": null
});
const permisoCreate = ref({
    "grupoPermiso": null,
    "method": null,
    "nombre": null,
    "permisoRequerido": false,
    "pertRecurso": false,
    "recursoId": null,
    "recursoNombre": null,
    "url": null,
    "urlIgnore": false
}
);
const icons = ref([
    { name: 'pi pi-home', icon: 'pi pi-home' },
    { name: 'pi pi-calendar', icon: 'pi pi-calendar' },
    { name: 'pi pi-times', icon: 'pi pi-times' },
    { name: 'pi pi-check', icon: 'pi pi-check' },
    { name: 'pi pi-info', icon: 'pi pi-info' },
    { name: 'pi pi-pencil', icon: 'pi pi-pencil' },
    { name: 'pi pi-trash', icon: 'pi pi-trash' },
    { name: 'pi pi-search', icon: 'pi pi-search' },
    { name: 'pi pi-user', icon: 'pi pi-user' },
    { name: 'pi pi-users', icon: 'pi pi-users' },
    { name: 'pi pi-lock', icon: 'pi pi-lock' },
    { name: 'pi pi-unlock', icon: 'pi pi-unlock' },
    { name: 'pi pi-bell', icon: 'pi pi-bell' },
    { name: 'pi pi-cog', icon: 'pi pi-cog' },
    { name: 'pi pi-power-off', icon: 'pi pi-power-off' },
    { name: 'pi pi-signal', icon: 'pi pi-signal' },
    { name: 'pi pi-refresh', icon: 'pi pi-refresh' },
    { name: 'pi pi-list', icon: 'pi pi-list' },
    { name: 'pi pi-cloud', icon: 'pi pi-cloud' },
    { name: 'pi pi-heart', icon: 'pi pi-heart' },
    { name: 'pi pi-folder', icon: 'pi pi-folder' },
    { name: 'pi pi-folder-open', icon: 'pi pi-folder-open' },
    { name: 'pi pi-upload', icon: 'pi pi-upload' },
    { name: 'pi pi-download', icon: 'pi pi-download' },
    { name: 'pi pi-book', icon: 'pi pi-book' },
    { name: 'pi pi-bookmark', icon: 'pi pi-bookmark' },
    { name: 'pi pi-print', icon: 'pi pi-print' },
    { name: 'pi pi-camera', icon: 'pi pi-camera' },
    { name: 'pi pi-video', icon: 'pi pi-video' },
    { name: 'pi pi-music', icon: 'pi pi-music' },
    { name: 'pi pi-volume-up', icon: 'pi pi-volume-up' },
    { name: 'pi pi-volume-down', icon: 'pi pi-volume-down' },
    { name: 'pi pi-headphones', icon: 'pi pi-headphones' },
    { name: 'pi pi-clock', icon: 'pi pi-clock' },
    { name: 'pi pi-play', icon: 'pi pi-play' },
    { name: 'pi pi-pause', icon: 'pi pi-pause' },
    { name: 'pi pi-stop', icon: 'pi pi-stop' },
    { name: 'pi pi-fast-forward', icon: 'pi pi-fast-forward' },
    { name: 'pi pi-rewind', icon: 'pi pi-rewind' },
    { name: 'pi pi-skip-next', icon: 'pi pi-skip-next' },
    { name: 'pi pi-skip-previous', icon: 'pi pi-skip-previous' },
    { name: 'pi pi-step-forward', icon: 'pi pi-step-forward' },
    { name: 'pi pi-step-backward', icon: 'pi pi-step-backward' },
    { name: 'pi pi-crop', icon: 'pi pi-crop' },
    { name: 'pi pi-frame', icon: 'pi pi-frame' },
    { name: 'pi pi-clone', icon: 'pi pi-clone' },
    { name: 'pi pi-refresh', icon: 'pi pi-refresh' },
    { name: 'pi pi-repeat', icon: 'pi pi-repeat' },
    { name: 'pi pi-replay', icon: 'pi pi-replay' },
    { name: 'pi pi-sync', icon: 'pi pi-sync' },
]);

//DatosTablas
const PermisosGlobalesData = ref();
const PerfilesData = ref();
const PerfilesItemsData = ref();
const RecursoData = ref();

//Recurso
const nombrePerfilCreate = ref();
const mesageError = ref(false);

//datosFaltantes



//DialogVisible
const CreatePerfilVisible = ref(false);
const PermisosGlobalesVisible = ref(false);
const PermisoUpdateVisible = ref(false);
const PermisoCreateVisible = ref(false);
const PerfilUpdateVisible = ref(false);
const PerfilesCreateVisible = ref(false);

//Selects
const PerfilesSelects = ref();
const PermisosGlobalesSelects = ref();


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

async function getPermisosGlobales() {
    PermisosGlobalesVisible.value = true;
    await axios.get('http://127.0.0.1:7777/getAllPermission', { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                PermisosGlobalesData.value = response.data['data'];
                PermisosGlobalesData.value.forEach(addRecursoRol);

            } else {
                alert(response.data['message']);
            }
        }
    }).catch(error => {
        // Manejar errores de la solicitud
        console.log(error);
    });
}

async function getPeriflesItems(idPerfil, nombre) {
    PerfilesItemsData.value = null;
    activeIndex.value = 1;
    idPerfilSel.value = idPerfil;
    nombrePerfil.value = nombre;

    await axios.get('http://127.0.0.1:7777/getAllPerfilesItemsByPerfil/' + idPerfil, { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                PerfilesItemsData.value = response.data['data'].map(item => {
                    return {
                        ID: item[0],
                        NOMBRE: item[1],
                        URL: item[2],
                        ICON: item[3]
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

async function getRecursosPerfilItem(idPerfilItem, nombrePI) {
    activeIndex.value = 2;
    idPerfilItemsel.value = idPerfilItem;
    nombrePerfilItem.value = nombrePI;
    await axios.get('http://127.0.0.1:7777/getRecursosByPerfilItem/' + idPerfilItem, { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                RecursoData.value = response.data['data'].map(item => {
                    return {
                        ID: item[0],
                        NOMBRE: item[1],
                        URL: item[2],
                        COLOR: item[3],
                        ICON: item[4]
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

async function getAllRecursos() {
    await axios.get('http://127.0.0.1:7777/getAllRecursos', { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                AllRecursos.value = response.data['data'];
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
    if (permisoCreate.value != null) {

        await axios.post('http://127.0.0.1:7777/create_permission', permisoCreate.value, { withCredentials: true }).then(response => {
            if (response.status === 200) {
                if (response.data['successful']) {
                    toast.add({ severity: 'success', summary: 'Rol creado', detail: response.data['message'], life: 3000 });
                    PermisoCreateVisible.value = false;
                    getPermisosGlobales();
                    permisoCreate.value = {
                        "grupoPermiso": null,
                        "method": null,
                        "nombre": null,
                        "permisoRequerido": false,
                        "pertRecurso": false,
                        "recursoId": null,
                        "recursoNombre": null,
                        "url": null,
                        "urlIgnore": false
                    };
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

const CreatePerfil = async () => {
    if (nombrePerfilCreate.value != null) {
        const json = {
            nombre: nombrePerfilCreate.value,
        }
        await axios.post('http://127.0.0.1:7777/createPeril', json, { withCredentials: true }).then(response => {
            if (response.status === 200) {
                if (response.data['successful']) {
                    toast.add({ severity: 'success', summary: 'Rol creado', detail: response.data['message'], life: 3000 });
                    CreatePerfilVisible.value = false;
                    getAllPerfiles();
                    nombrePerfilCreate.value = null;
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

const updatePermiso = async () => {
    if (!permisoUpdate.value.pertRecurso) {
        permisoUpdate.value.recursoId = null;
        permisoUpdate.value.recursoNombre = null;
    }
    await axios.put('http://127.0.0.1:7777/updatePermission', permisoUpdate.value, { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                getPermisosGlobales();
                toast.add({ severity: 'success', summary: 'Rol actualizado', detail: response.data['message'], life: 3000 });
                PermisoUpdateVisible.value = false;
                updatePermiso.value = null;
            } else {
                toast.add({ severity: 'error', summary: 'Error', detail: response.data['message'], life: 3000 });
            }

        }
    }).catch(error => {
        // Manejar errores de la solicitud
        toast.add({ severity: 'contrast', summary: 'Error', detail: 'Error del servidor', life: 3000 });
    });
}

const updatePerfil = async () => {
    let json = {
        nombre: perfilUpdate.value.nombre,
        id: perfilUpdate.value.idPerfil
    }
    await axios.put('http://127.0.0.1:7777/updatePerfil', json, { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                getAllPerfiles();
                toast.add({ severity: 'success', summary: 'Rol actualizado', detail: response.data['message'], life: 3000 });
                PerfilUpdateVisible.value = false;
                perfilUpdate.value = null;
            } else {
                toast.add({ severity: 'error', summary: 'Error', detail: response.data['message'], life: 3000 });
            }

        }
    }).catch(error => {
        // Manejar errores de la solicitud
        toast.add({ severity: 'contrast', summary: 'Error', detail: 'Error del servidor', life: 3000 });
    });
}

//DELETE

async function deletePermiso(idPermission) {
    await axios.delete('http://127.0.0.1:7777/deletePermission/' + idPermission, { withCredentials: true }).then(response => {
        if (response.status === 200) {
            if (response.data['successful']) {
                getPermisosGlobales();
                toast.add({ severity: 'success', summary: 'Permiso Eliminado', detail: response.data['message'], life: 3000 });
            } else {
                toast.add({ severity: 'error', summary: 'Error', detail: response.data['message'], life: 3000 });
            }

        }
    }).catch(error => {
        // Manejar errores de la solicitud
        toast.add({ severity: 'contrast', summary: 'Error', detail: 'Error del servidor', life: 3000 });
    });
}

async function deletePerfil(idPerfil) {

}

//UTILS

const updatePerfilVisible = (data) => {
    perfilUpdate.value = data;
    PerfilUpdateVisible.value = true;
}

const updatePermisoVisible = (data) => {
    permisoUpdate.value = data;
    PermisoUpdateVisible.value = true;
    getAllRecursos();
}

const createPermisosVisible = () => {
    PermisoCreateVisible.value = true;
    getAllRecursos();
}

const createPerfilVisible = () => {
    CreatePerfilVisible.value = true;
}

const search = (event) => {
    let _items = AllRecursos.value.map(item => item);

    ItemsRecursos.value = event.query ? _items.filter(item => item.nameFront.toLowerCase().includes(event.query.toLowerCase())) : _items;
}

function onSelect(e) {
    permisoUpdate.value.recursoId = e.value.idRecurso;
    permisoUpdate.value.recursoNombre = e.value.nameFront;
}

function onSelectCreacte(e) {
    permisoCreate.value.recursoId = e.value.idRecurso;
    permisoCreate.value.recursoNombre = e.value.nameFront;
}

let displayedGrupoPermisoCreate = computed({
    get: () => permisoCreate.value.grupoPermiso !== -1 ? permisoCreate.value.grupoPermiso : '',
    set: value => permisoCreate.value.grupoPermiso = value
});

let displayedGrupoPermiso = computed({
    get: () => permisoUpdate.value.grupoPermiso !== -1 ? permisoUpdate.value.grupoPermiso : '',
    set: value => permisoUpdate.value.grupoPermiso = value
});

const methods = [
    'GET',
    'POST',
    'PUT',
    'PATCH',
    'DELETE'
];

const filtersPermisos = ref({
    idPermission: { value: null, matchMode: FilterMatchMode.STARTS_WITH },
    nombre: { value: null, matchMode: FilterMatchMode.STARTS_WITH },
    url: { value: null, matchMode: FilterMatchMode.CONTAINS },
    method: { value: null, matchMode: FilterMatchMode.EQUALS },
    recursoNombre: { value: null, matchMode: FilterMatchMode.IN },
    grupoPermiso: { value: null, matchMode: FilterMatchMode.EQUALS }
});

const filtersPerfiles = ref(
    {

    }
);

function addRecursoRol(item) {
    if (!recursosNombres.value.includes(item.recursoNombre)) {
        if (item.recursoNombre != '' && item.recursoNombre != null) {
            recursosNombres.value.push(item.recursoNombre);
        }
    }
}

const confirmDeletePermiso = (idPermiso) => {
    confirm.require({
        message: 'Seguro que quiere eliminar este permisos?',
        header: 'Danger Zone',
        icon: 'pi pi-info-circle',
        rejectLabel: 'Cancelar',
        acceptLabel: 'Eliminar',
        rejectClass: 'p-button-secondary p-button-outlined',
        acceptClass: 'p-button-danger',
        accept: () => {
            deletePermiso(idPermiso);
            toast.add({ severity: 'info', summary: 'Confirmed', detail: 'Eliminando permisos', life: 3000 });
        },
        reject: () => {
            toast.add({ severity: 'error', summary: 'Rejected', detail: 'Proceso cancelado', life: 3000 });
        }
    });
};

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
    const promises = PermisosGlobalesSelects.value.map(item => deletePermiso(item['idPermission']));
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