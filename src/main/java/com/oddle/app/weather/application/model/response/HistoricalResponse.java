package com.oddle.app.weather.application.model.response;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author cuongnd9
 * @date 19/02/2023
 * @project weather
 * @package com.oddle.app.weather.application.model.response
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricalResponse implements Serializable {

    private static final long serialVersionUID = 1l;

    private Integer cnt;
    private List<WeatherResponse> list;

}
