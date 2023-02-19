package com.oddle.app.weather.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author cuongnd9
 * @date 16/02/2023
 * @project weather
 * @package com.oddle.app.weather.domain.entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "weather")
public class Weather implements Serializable {

    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "field.not.blank")
    @Column(name = "main_status")
    private String mainStatus;

    @Column(name = "description")
    private String description;

    @Column(name = "icon")
    private String icon;

    @NotNull(message = "field.not.null")
    @Column(name = "temp")
    private Double temp;

    @NotNull(message = "field.not.null")
    @Column(name = "feels_like")
    private Double feelsLike;

    @NotNull(message = "field.not.null")
    @Column(name = "temp_min")
    private Double tempMin;

    @NotNull(message = "field.not.null")
    @Column(name = "temp_max")
    private Double tempMax;

    @NotNull(message = "field.not.null")
    @Column(name = "pressure")
    private Double pressure;

    @NotNull(message = "field.not.null")
    @Column(name = "humidity")
    private Double humidity;

    @Column(name = "sea_level")
    private Double seaLevel;

    @Column(name = "grnd_level")
    private Double grndLevel;

    @NotNull(message = "field.not.null")
    @Column(name = "wind_speed")
    private Double windSpeed;

    @NotNull(message = "field.not.null")
    @Column(name = "wind_deg")
    private Double windDeg;

    @NotNull(message = "field.not.null")
    @Column(name = "wind_gust")
    private Double windGust;

    @Column(name = "visibility")
    private Integer visibility;

    @Column(name = "rain")
    private Double rain;

    @Column(name = "cloud")
    private Integer cloud;

    @Column(name = "sunrise")
    private String sunrise;

    @Column(name = "sunset")
    private String sunset;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @CreationTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "dt")
    private Long dt;

    @Transient
    private String dateTime;

}
