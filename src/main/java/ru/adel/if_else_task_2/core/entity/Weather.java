package ru.adel.if_else_task_2.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.adel.if_else_task_2.core.entity.enums.WeatherCondition;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    @Column(name = "temperature", nullable = false)
    private Float temperature;

    @Column(name = "humidity", nullable = false)
    private Float humidity;

    @Column(name = "wind_speed", nullable = false)
    private Float windSpeed;

    @Column(name = "precipitation_amount", nullable = false)
    private Float precipitationAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "weather_condition", nullable = false)
    private WeatherCondition weatherCondition;

    @Column(name = "measurement_date_time", nullable = false)
    private LocalDateTime measurementDateTime;

}
