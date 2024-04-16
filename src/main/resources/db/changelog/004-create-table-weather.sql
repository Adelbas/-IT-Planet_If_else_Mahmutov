--liquibase formatted sql

--changeset adel:create-table-weather failOnError=true
--comment: Create table weather
CREATE TABLE IF NOT EXISTS weather
(
    id                    BIGSERIAL,
    region_id             BIGINT,
    temperature           NUMERIC   NOT NULL,
    humidity              NUMERIC   NOT NULL,
    wind_speed            NUMERIC   NOT NULL,
    precipitation_amount  NUMERIC   NOT NULL,
    weather_condition     VARCHAR   NOT NULL,
    measurement_date_time TIMESTAMP NOT NULL,

    CONSTRAINT pk_weather PRIMARY KEY (id),
    CONSTRAINT fk_region FOREIGN KEY (region_id) REFERENCES region (id),
    CONSTRAINT check_weather_condition CHECK (weather_condition IN ('CLEAR', 'CLOUDY', 'RAIN', 'SNOW', 'FOG', 'STORM'))
);