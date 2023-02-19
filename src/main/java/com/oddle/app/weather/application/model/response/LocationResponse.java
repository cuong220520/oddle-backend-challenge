package com.oddle.app.weather.application.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author cuongnd9
 * @date 16/02/2023
 * @project weather
 * @package com.oddle.app.weather.application.model.response
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationResponse implements Serializable {

    private static final long serialVersionUID = 1l;

    private String name;
    private BigDecimal lat;
    private BigDecimal lon;
    private String country;

}
