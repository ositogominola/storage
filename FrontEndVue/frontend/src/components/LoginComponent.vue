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

                                    <p class="small mb-5 pb-lg-2"><a class="text-white-50" href="#!">Orvido su contraseña?</a>
                                    </p>

                                    <button class="btn btn-outline-light btn-lg px-5" type="submit">iniciar sesion</button>


                                </div>

                                <div>
                                    <p class="mb-0">no tienes una cuenta? <a href="#!" class="text-white-50 fw-bold">Crear Cuenta</a>
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
  
<script>
import { base64Encode } from './utils.js';

export default {
    data() {
        return {
            username: '',
            password: '',
            error: ''
        };
    },
    beforeCreate(){
        if (localStorage.getItem("token")!=null && localStorage.getItem("token")!=""){
            this.$router.push('/');   
        }
    },
    methods: {
        async login() {
            // Codificar el nombre de usuario y la contraseña en base64
            const credentials = base64Encode(`${this.username}:${this.password}`);

            // Construir el encabezado de autorización
            const authHeader = `Basic ${credentials}`;

            try {
                // Realizar la solicitud HTTP con Axios
                const response = await this.$axios.post('http://127.0.0.1:7777/login', null, {
                    headers: {
                        Authorization: authHeader
                    }
                });

                if (response.data["succesfull"]){
                    localStorage.setItem("token",response.data["token"])
                    window.location.href='/';
                }else{
                    alert("Usuario o contraseña incorrecta")
                }
            } catch (error) {
                console.log(error);
            }
        }
    }
};
</script>

  