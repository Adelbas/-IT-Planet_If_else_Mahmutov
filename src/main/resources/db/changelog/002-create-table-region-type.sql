--liquibase formatted sql

--changeset adel:create-table-region-type failOnError=true
--comment: Create table region_type
CREATE TABLE IF NOT EXISTS region_type
(
    id   BIGSERIAL,
    type VARCHAR UNIQUE NOT NULL,

    CONSTRAINT pk_region_type PRIMARY KEY (id)
);