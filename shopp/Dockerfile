FROM python:3.10-alpine

WORKDIR /ventas

COPY . /ventas

RUN apk update && \
    apk add mysql-client && \
    pip install --trusted-host pypi.python.org -r requerimient.txt

ENV DB_USER=geuraxann3rpwcgw
ENV DB_PASSWORD=n4hs2igx3xzyccv2
ENV DB_HOST=ohunm00fjsjs1uzy.cbetxkdyhwsb.us-east-1.rds.amazonaws.com
ENV DB_PORT=3306
ENV DB_NAME=cvt1me1ujktroqvm

EXPOSE 8080

ENV NAME ventas

# Run main.py when the container launches
CMD ["python", "main.py"]
