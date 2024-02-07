<template>
    <ul>
        <li>
            Cuenta
        </li>
        <li>
            empresas
        </li>
        <li>
            <button @click="handleClick">Haz clic aquí</button>
        </li>
    </ul>
</template>

<script>
export default {
    methods: {
        handleClick() {
            localStorage.removeItem("token")
            this.$router.push('/login')
        }
    }
};
</script>