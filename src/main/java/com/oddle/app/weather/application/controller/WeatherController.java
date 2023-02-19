package com.oddle.app.weather.application.controller;

import com.oddle.app.weather.domain.entity.Weather;
import com.oddle.app.weather.domain.service.dispatcher.*;
import com.oddle.app.weather.infrastructure.enumurate.HistoricalType;
import com.oddle.app.weather.infrastructure.logger.LogExecutionTime;
import com.oddle.app.weather.infrastructure.validation.Belong;
import com.oddle.app.weather.infrastructure.validation.ValidDate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/weather", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "weather-controller")
@Validated
@RequiredArgsConstructor
public class WeatherController {

    private final IGetCurrentWeatherByCityService getCurrentWeatherByCity;

    private final IGetHistoricalWeatherService getHistoricalWeather;

    private final ISaveWeatherService saveWeatherService;

    private final IUpdateWeatherService updateWeatherService;

    private final IDeleteWeatherService deleteWeatherService;

    @GetMapping("/current")
    @Operation(summary = "get current weather by city", description = "User can get weather by input city name")
    @LogExecutionTime
    public Object getCurrentWeatherByCity(@RequestParam(name = "city") @NotBlank(message = "field.not.blank") String city) {
        return getCurrentWeatherByCity.execute(city);
    }

    @PostMapping("/save")
    @Operation(summary = "save weather data", description = "User can save weather to DB")
    @LogExecutionTime
    public Object saveWeather(@RequestBody @Valid List<Weather> request) {
        return saveWeatherService.execute(request);
    }

    @GetMapping("/history")
    @Operation(summary = "get historical weather data", description = "User can get historical weather data")
    @LogExecutionTime
    public Object getHistoricalWeather(
            @RequestParam(name = "city") @NotBlank(message = "field.not.blank") String city,
            @RequestParam(name = "type") @Belong(enumClazz = HistoricalType.class) String type,
            @RequestParam(name = "start")
            @NotBlank(message = "field.not.blank")
            @ValidDate String start,
            @RequestParam(name = "end")
            @NotBlank(message = "field.not.blank")
            @ValidDate String end) {

        Map<String, String> request = Map.of(
                "city", city,
                "type", type,
                "start", start,
                "end", end
        );

        return getHistoricalWeather.execute(request);
    }

    @PutMapping("/update")
    @Operation(summary = "update weather data", description = "User can update weather to DB")
    @LogExecutionTime
    public Object updateWeather(@RequestBody @Valid Weather request) {

        return updateWeatherService.execute(request);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "update weather data", description = "User can update weather to DB")
    @LogExecutionTime
    public Object deleteWeather(@PathVariable("id") Long id) {
        return deleteWeatherService.execute(id);
    }

}