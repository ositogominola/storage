# Usa una imagen base de Python
FROM python:3.8

# Establece el directorio de trabajo
WORKDIR /GATEWAY

# Copia los archivos necesarios al contenedor
COPY . /GATEWAY

# Instala las dependencias de Python
RUN pip install --no-cache-dir -r requirements.txt

# Expone el puerto en el que Flask ejecuta la aplicación
EXPOSE 7777

# Define la variable de entorno de Flask
ENV NAME GATEWAY

# Comando para ejecutar la aplicación cuando el contenedor se inicia
CMD ["python", "main.py"]
