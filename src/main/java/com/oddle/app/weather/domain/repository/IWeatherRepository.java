package com.oddle.app.weather.domain.repository;

import com.oddle.app.weather.domain.entity.Weather;

import java.util.List;
import java.util.Optional;

/**
 * @author cuongnd9
 * @date 19/02/2023
 * @project weather
 * @package com.oddle.app.weather.domain.repository
 */
public interface IWeatherRepository {

    List<Weather> findByDtIn(List<Long> dts);
    List<Weather> saveAll(List<Weather> weatherList);
    Optional<Weather> findById(Long id);
    Weather save(Weather weather);
    void delete(Long id);

}
