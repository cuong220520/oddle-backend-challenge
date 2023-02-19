package com.oddle.app.weather.domain.service.dispatcher;

import com.oddle.app.weather.application.model.response.Response;
import com.oddle.app.weather.infrastructure.cqrs.Query;

import java.util.Map;

/**
 * @author cuongnd9
 * @date 19/02/2023
 * @project weather
 * @package com.oddle.app.weather.domain.service.dispatcher
 */
public interface IGetHistoricalWeatherService extends Query<Map<String, String>, Response> {
}
