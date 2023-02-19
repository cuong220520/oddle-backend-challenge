package com.oddle.app.weather.domain.service;

import com.oddle.app.weather.application.model.response.Response;
import com.oddle.app.weather.application.model.response.ResponseFactory;
import com.oddle.app.weather.domain.entity.Weather;
import com.oddle.app.weather.domain.repository.IWeatherRepository;
import com.oddle.app.weather.domain.service.dispatcher.IDeleteWeatherService;
import com.oddle.app.weather.infrastructure.builder.Gson;
import com.oddle.app.weather.infrastructure.enumurate.ResponseStatusEnum;
import com.oddle.app.weather.infrastructure.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * @author cuongnd9
 * @date 19/02/2023
 * @project weather
 * @package com.oddle.app.weather.domain.service
 */
@Slf4j
@RequiredArgsConstructor
public class DeleteWeatherService implements IDeleteWeatherService {

    private final IWeatherRepository weatherRepository;

    @Override
    public void validate(Long id) {
        Optional<Weather> weatherOptional = weatherRepository.findById(id);

        log.info("updateWeatherService.validate.weatherOptional: {}", Gson.getInstance().toJson(weatherOptional));

        if (!weatherOptional.isPresent()) throw new BadRequestException("error.update_weather.weather_not_exist");
    }

    @Override
    public Response process(Long id) {

        weatherRepository.delete(id);

        return ResponseFactory.produce(ResponseStatusEnum.SUCCESS, null, List.of());
    }
}
