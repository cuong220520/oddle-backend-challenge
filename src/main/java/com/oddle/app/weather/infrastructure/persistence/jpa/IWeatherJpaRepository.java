package com.oddle.app.weather.infrastructure.persistence.jpa;

import com.oddle.app.weather.domain.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author cuongnd9
 * @date 19/02/2023
 * @project weather
 * @package com.oddle.app.weather.infrastructure.persistence.jpa
 */
@Repository
public interface IWeatherJpaRepository extends JpaRepository<Weather, Long> {

    List<Weather> findByDtIn(List<Long> dts);

}
