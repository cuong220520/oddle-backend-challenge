package com.oddle.app.weather.application.model.response;

import com.oddle.app.weather.infrastructure.enumurate.ResponseStatusEnum;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author cuongnd9
 * @date 14/02/2023
 * @project weather
 * @package com.oddle.app.weather.application.model.response
 */
public class ResponseFactory implements Serializable {

    private static final long serialVersionUID = 1l;

    public static Response produce(ResponseStatusEnum status,
                                   Object data,
                                   List<Response.ErrorItem> errors) {

        if (status.equals(ResponseStatusEnum.SUCCESS)) {
            return new SuccessResponse(ResponseStatusEnum.SUCCESS, data, Collections.emptyList());
        }

        return new ErrorResponse(ResponseStatusEnum.FAIL, null, errors);

    }

}
