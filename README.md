**##Descripción del Proyecto**  
Este proyecto es una aplicación web dividida en microservicios diseñados para manejar diferentes aspectos de la plataforma. Cada microservicio está escrito en un marco de trabajo específico y se conecta a una base de datos diferente para ofrecer una arquitectura modular y escalable.

**###Microservicios**  
1. Servicio de Seguridad (Spring):  

    -Este microservicio se encarga de gestionar usuarios, autenticaciones y empresas.  
    -Implementado en Spring Framework.  
   
2. Servicio de Artículos (Flask):

    -Gestiona los artículos asociados a las empresas.
    -Implementado en Flask.
   
3. Servicio de Ventas (Flask):

    -Administración de las ventas realizadas.
    -Desarrollado en Flask.

4. Gateway y Frontend
Gateway (Flask):
El gateway es responsable de solicitar cada servicio y conectarlos al frontend.
Implementado en Flask para una integración fluida con los microservicios.
Frontend (Vue.js):
La interfaz de usuario está desarrollada en Vue.js para una experiencia interactiva y amigable.
Arquitectura de Datos
Cada microservicio se conecta a una base de datos diferente para garantizar una separación clara de responsabilidades y una gestión eficiente de los datos.

Instrucciones de Uso
Para ejecutar cada microservicio, sigue las instrucciones proporcionadas en su respectiva documentación. Asegúrate de configurar las conexiones a las bases de datos según sea necesario.

Autores
Haner Alejandro Ahumada Tovar ([enlace al perfil de GitHub](https://github.com/ositogominola)https://github.com/ositogominola)
