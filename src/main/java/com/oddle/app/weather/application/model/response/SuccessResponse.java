package com.oddle.app.weather.application.model.response;

import com.oddle.app.weather.infrastructure.enumurate.ResponseStatusEnum;

import java.io.Serializable;
import java.util.List;

/**
 * @author cuongnd9
 * @date 14/02/2023
 * @project weather
 * @package com.oddle.app.weather.application.model.response
 */
public class SuccessResponse extends Response implements Serializable {

    private static final long serialVersionUID = 1l;

    public SuccessResponse(ResponseStatusEnum status) {
        super(status);
    }

    public SuccessResponse(ResponseStatusEnum status, Object data) {
        super(status, data);
    }

    public SuccessResponse(ResponseStatusEnum status, List<ErrorItem> errors) {
        super(status, errors);
    }

    public SuccessResponse(ResponseStatusEnum status, Object data, List<ErrorItem> errors) {
        super(status, data, errors);
    }
}
