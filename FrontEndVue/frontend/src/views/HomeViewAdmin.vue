<template>
    <Suspense>
        <template #default>
            <HomeComponent />
        </template>
        <template #fallback>
            <div>Loading...</div>
        </template>
    </Suspense>
</template>

<script setup>
import HomeComponent from '../components/HomeComponent.vue'

</script>