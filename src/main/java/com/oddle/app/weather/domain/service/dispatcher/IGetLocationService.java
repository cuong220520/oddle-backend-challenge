package com.oddle.app.weather.domain.service.dispatcher;

import com.oddle.app.weather.application.model.response.LocationResponse;
import com.oddle.app.weather.infrastructure.cqrs.Query;

/**
 * @author cuongnd9
 * @date 19/02/2023
 * @project weather
 * @package com.oddle.app.weather.domain.service.dispatcher
 */
public interface IGetLocationService extends Query<String, LocationResponse> {
}
