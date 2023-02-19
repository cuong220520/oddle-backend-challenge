package com.oddle.app.weather.infrastructure.config;

import com.oddle.app.weather.domain.repository.IWeatherRepository;
import com.oddle.app.weather.domain.service.*;
import com.oddle.app.weather.domain.service.dispatcher.*;
import com.oddle.app.weather.infrastructure.constant.WeatherConfigConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cuongnd9
 * @date 16/02/2023
 * @project weather
 * @package com.oddle.app.weather.infrastructure.config
 */
@Configuration
public class BeanConfig {

    @Bean
    public IGetLocationService getLocation(WeatherConfigConstant weatherConfigConstant) {
        return new GetLocationService(weatherConfigConstant);
    }

    @Bean
    public IGetCurrentWeatherByCityService getCurrentWeatherByCity(WeatherConfigConstant weatherConfigConstant,
                                                                   IGetLocationService getLocation) {
        return new GetCurrentWeatherByCityService(weatherConfigConstant, getLocation);
    }

    @Bean
    public IGetHistoricalWeatherService getHistoricalWeather(WeatherConfigConstant weatherConfigConstant,
                                                             IGetLocationService getLocation) {
        return new GetHistoricalWeatherService(weatherConfigConstant, getLocation);
    }

    @Bean
    public ISaveWeatherService saveWeatherService(IWeatherRepository weatherRepository) {
        return new SaveWeatherService(weatherRepository);
    }

    @Bean
    public IUpdateWeatherService updateWeatherService(IWeatherRepository weatherRepository) {
        return new UpdateWeatherService(weatherRepository);
    }

    @Bean
    public IDeleteWeatherService deleteWeatherService(IWeatherRepository weatherRepository) {
        return new DeleteWeatherService(weatherRepository);
    }

}
