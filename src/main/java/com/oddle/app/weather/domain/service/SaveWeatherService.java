package com.oddle.app.weather.domain.service;

import com.oddle.app.weather.application.model.response.Response;
import com.oddle.app.weather.application.model.response.ResponseFactory;
import com.oddle.app.weather.domain.entity.Weather;
import com.oddle.app.weather.domain.repository.IWeatherRepository;
import com.oddle.app.weather.domain.service.dispatcher.ISaveWeatherService;
import com.oddle.app.weather.infrastructure.enumurate.ResponseStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cuongnd9
 * @date 19/02/2023
 * @project weather
 * @package com.oddle.app.weather.domain.service
 */
@RequiredArgsConstructor
@Slf4j
public class SaveWeatherService implements ISaveWeatherService {

    private List<Weather> nonExistedRecords = new LinkedList<>();

    private final IWeatherRepository weatherRepository;

    @Override
    public void validate(List<Weather> request) {
        List<Long> reqDts = request
                .stream()
                .map(Weather::getDt)
                .collect(Collectors.toList());

        List<Long> existedDts = weatherRepository.findByDtIn(reqDts).stream().map(Weather::getDt).collect(Collectors.toList());

        log.info("saveWeatherService.validate.existedRecordsSize: {}", existedDts.size());

        reqDts.removeAll(existedDts);

        this.nonExistedRecords = request.stream().filter(r -> reqDts.contains(r.getDt())).collect(Collectors.toList());

    }

    @Override
    public Response process(List<Weather> request) {

        log.info("saveWeatherService.process.nonExistedRecords: {}", this.nonExistedRecords.size());

        if (CollectionUtils.isEmpty(nonExistedRecords)) {
            log.info("saveWeatherService.process.all records existed");
            return ResponseFactory.produce(ResponseStatusEnum.SUCCESS, null, List.of());
        }

        return ResponseFactory.produce(ResponseStatusEnum.SUCCESS, weatherRepository.saveAll(nonExistedRecords), List.of());
    }
}
