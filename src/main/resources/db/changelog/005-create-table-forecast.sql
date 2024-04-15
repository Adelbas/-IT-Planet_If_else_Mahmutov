--liquibase formatted sql

--changeset adel:create-table-forecast failOnError=true
--comment: Create table forecast
CREATE TABLE IF NOT EXISTS forecast
(
    id                BIGSERIAL,
    region_id         BIGINT    NOT NULL,
    temperature       NUMERIC   NOT NULL,
    date_time         TIMESTAMP NOT NULL,
    weather_condition VARCHAR   NOT NULL,

    CONSTRAINT pk_forecast PRIMARY KEY (id),
    CONSTRAINT fk_region FOREIGN KEY (region_id) REFERENCES region (id),
    CONSTRAINT check_weather_condition CHECK (weather_condition IN ('CLEAR', 'CLOUDY', 'RAIN', 'SNOW', 'FOG', 'STORM'))
);