# Use an official Python runtime as a parent image
FROM python:3.10-alpine

# Set the working directory to /app
WORKDIR /Articulos

# Copy the current directory contents into the container at /app
COPY . /Articulos

# Install any needed packages specified in requirements.txt
RUN apk update && \
    apk add mysql-client && \
    pip install --trusted-host pypi.python.org -r requirements.txt


ENV DB_USER=uaoe1ai9g86ussnu
ENV DB_PASSWORD=vnqboe00okipsivq
ENV DB_HOST=g84t6zfpijzwx08q.cbetxkdyhwsb.us-east-1.rds.amazonaws.com
ENV DB_PORT=3306
ENV DB_NAME=fdljasv4hhcovafw


# Make port 5555 available to the world outside this container
EXPOSE 5555

# Define environment variable
ENV NAME Articulos

# Run main.py when the container launches
CMD ["python", "main.py"]