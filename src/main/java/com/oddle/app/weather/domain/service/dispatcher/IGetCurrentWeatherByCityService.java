package com.oddle.app.weather.domain.service.dispatcher;

import com.oddle.app.weather.application.model.response.Response;
import com.oddle.app.weather.infrastructure.cqrs.Query;

/**
 * @author cuongnd9
 * @date 14/02/2023
 * @project weather
 * @package com.oddle.app.weather.domain.service.dispatcher
 */
public interface IGetCurrentWeatherByCityService extends Query<String, Response> {
}
