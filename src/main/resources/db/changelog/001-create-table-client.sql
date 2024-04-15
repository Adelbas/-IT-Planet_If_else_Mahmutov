--liquibase formatted sql

--changeset adel:create-table-client failOnError=true
--comment: Create table client
CREATE TABLE IF NOT EXISTS client
(
    id         BIGSERIAL,
    first_name VARCHAR(30)        NOT NULL,
    last_name  VARCHAR(30)        NOT NULL,
    email      VARCHAR(30) UNIQUE NOT NULL,
    password   VARCHAR(16)        NOT NULL,

    CONSTRAINT pk_client PRIMARY KEY (id)
);