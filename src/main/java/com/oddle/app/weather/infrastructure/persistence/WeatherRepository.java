package com.oddle.app.weather.infrastructure.persistence;

import com.oddle.app.weather.domain.entity.Weather;
import com.oddle.app.weather.domain.repository.IWeatherRepository;
import com.oddle.app.weather.infrastructure.persistence.jpa.IWeatherJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author cuongnd9
 * @date 19/02/2023
 * @project weather
 * @package com.oddle.app.weather.infrastructure.persistence
 */
@Repository
@RequiredArgsConstructor
public class WeatherRepository implements IWeatherRepository {

    private final IWeatherJpaRepository weatherJpaRepository;

    @Override
    public List<Weather> findByDtIn(List<Long> dts) {
        return weatherJpaRepository.findByDtIn(dts);
    }

    @Override
    public List<Weather> saveAll(List<Weather> weatherList) {
        return weatherJpaRepository.saveAll(weatherList);
    }

    @Override
    public Optional<Weather> findById(Long id) {
        return weatherJpaRepository.findById(id);
    }

    @Override
    public Weather save(Weather weather) {
        return weatherJpaRepository.save(weather);
    }

    @Override
    public void delete(Long id) {
        weatherJpaRepository.deleteById(id);
    }
}
