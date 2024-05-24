--liquibase formatted sql

--changeset adel:create-table-region failOnError=true
--comment: Create table region
CREATE TABLE IF NOT EXISTS region
(
    id               BIGSERIAL,
    name             VARCHAR UNIQUE NOT NULL,
    region_type_id   BIGINT,
    parent_region_id BIGINT,
    client_id        BIGINT,
    latitude         NUMERIC        NOT NULL,
    longitude        NUMERIC        NOT NULL,

    CONSTRAINT pk_region PRIMARY KEY (id),
    CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES client (id) ON DELETE SET NULL,
    CONSTRAINT fk_parent_region FOREIGN KEY (parent_region_id) REFERENCES region (id),
    CONSTRAINT fk_region_type FOREIGN KEY (region_type_id) REFERENCES region_type (id),
    CONSTRAINT unique_coordinates UNIQUE (latitude, longitude)
);