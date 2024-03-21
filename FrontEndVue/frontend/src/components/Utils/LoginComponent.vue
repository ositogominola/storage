<!-- LoginComponent.vue -->
<template>
    <section class="vh-100 gradient-custom">
        <form @submit.prevent="login">
            <div class="container py-5 h-100">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col-12 col-md-8 col-lg-6 col-xl-5">
                        <div class="card bg-dark text-white" style="border-radius: 1rem;">
                            <div class="card-body p-5 text-center">

                                <div class="mb-md-5 mt-md-4 pb-5">

                                    <h2 class="fw-bold mb-2 text-uppercase">Administracion Inventario</h2>
                                    <p class="text-white-50 mb-5">Bienvenido!</p>

                                    <div class="form-outline form-white mb-4">
                                        <input type="text" id="username" v-model="username"
                                            class="form-control form-control-lg" />
                                        <label class="form-label" for="typeEmailX">Email</label>
                                    </div>

                                    <div class="form-outline form-white mb-4">
                                        <input type="password" id="password" v-model="password"
                                            class="form-control form-control-lg" />
                                        <label class="form-label" for="typePasswordX">Password</label>
                                    </div>

                                    <p class="small mb-5 pb-lg-2"><a class="text-white-50" href="#!">Orvido su
                                            contraseña?</a>
                                    </p>

                                    <button class="btn btn-outline-light btn-lg px-5" type="submit">iniciar
                                        sesion</button>


                                </div>

                                <div>
                                    <p class="mb-0">no tienes una cuenta? <a href="#!"
                                            class="text-white-50 fw-bold">Crear
                                            Cuenta</a>
                                    </p>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </section>
    <div v-if="error" style="color: red;">{{ error }}</div>
</template>



<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const username = ref('');
const password = ref('');
const error = ref('');
const router = useRouter();

const checkAuthentication = async () => {
    try {
        const response = await axios.get('http://127.0.0.1:7777/isAuthenticated', { withCredentials: true });
        if (response.status === 200) {
            router.push('/');
        } else {
            router.push('/login');
        }
    } catch (error) {
        console.error("error: " + error);
        router.push('/login');
    }
};

onMounted(() => {
    checkAuthentication();
});

const login = async () => {
    const credentials = btoa(`${username.value}:${password.value}`);
    const authHeader = `Basic ${credentials}`;

    try {
        const response = await axios.post('http://127.0.0.1:7777/login', null, {
            headers: {
                Authorization: authHeader
            },
            withCredentials: true
        });
        console.log(response.data);
        if (response.data["succesfull"]) {
            localStorage.setItem('userInfo', response.data["user"]);
            checkAuthentication();
        } else {
            alert("Usuario o contraseña incorrecta");
        }
    } catch (error) {
        console.error(error);
    }
};
</script>
