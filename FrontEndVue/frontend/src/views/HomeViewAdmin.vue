<template>
    <HomeComponent />
</template>
  
<script>
import HomeComponent from '../components/HomeComponent.vue'

export default {
    components: {
        HomeComponent
    },
    created() {
        if (!localStorage.getItem("token")) {
            this.$router.push('/login');
        }
    }
}
</script>
  