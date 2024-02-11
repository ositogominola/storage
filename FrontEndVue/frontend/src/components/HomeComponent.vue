<template>
    <div class="container-fluid">
        <div class="row">
            <!-- Menú -->
            <div class="col-md-2 menu">
                <MenuComponentVue />
            </div>
            <!-- Panel 2 -->
            <div class="col-md-10">
                <RouterView/>
            </div>
        </div>

    </div>
</template>
  

<script setup>
import { RouterView } from 'vue-router';
import MenuComponentVue from './MenuComponent.vue'
</script>