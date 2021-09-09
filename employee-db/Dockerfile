ARG BASE_CONTAINER=mysql:5.7
FROM $BASE_CONTAINER

LABEL maintainer="Che-Hung Lin <edward730109@gmail.com>"

COPY ./my.cnf /etc/mysql/conf.d/my.cnf
COPY ./db.sql /docker-entrypoint-initdb.d/db.sql

WORKDIR /root
RUN chmod 644 /etc/mysql/conf.d/my.cnf && chmod 777 /docker-entrypoint-initdb.d/db.sql

EXPOSE 3306

CMD ["mysqld"]
