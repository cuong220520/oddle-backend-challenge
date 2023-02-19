package com.oddle.app.weather.application.model.response;

import com.oddle.app.weather.infrastructure.enumurate.ResponseStatusEnum;
import com.oddle.app.weather.infrastructure.util.DateUtil;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author cuongnd9
 * @date 14/02/2023
 * @project weather
 * @package com.oddle.app.weather.application.model.response
 */
@Getter
@Setter
public abstract class Response implements Serializable {

    private static final long serialVersionUID = 1l;

    public Response(ResponseStatusEnum status) {
        this.status = status;
        this.id = System.currentTimeMillis();
        this.timeStamp = DateUtil.formatDateTime(DateUtil.getCurrentDateTime(), "yyyy-MM-dd HH:mm:ss");
    }

    public Response(ResponseStatusEnum status, Object data) {
        this.status = status;
        this.id = System.currentTimeMillis();
        this.timeStamp = DateUtil.formatDateTime(DateUtil.getCurrentDateTime(), "yyyy-MM-dd HH:mm:ss");
        this.data = data;
    }

    public Response(ResponseStatusEnum status, List<ErrorItem> errors) {
        this.status = status;
        this.id = System.currentTimeMillis();
        this.timeStamp = DateUtil.formatDateTime(DateUtil.getCurrentDateTime(), "yyyy-MM-dd HH:mm:ss");
        this.errors = errors;
    }

    public Response(ResponseStatusEnum status, Object data, List<ErrorItem> errors) {
        this.status = status;
        this.id = System.currentTimeMillis();
        this.timeStamp = DateUtil.formatDateTime(DateUtil.getCurrentDateTime(), "yyyy-MM-dd HH:mm:ss");
        this.errors = errors;
        this.data = data;
    }

    protected long id;
    protected ResponseStatusEnum status;
    protected String timeStamp;
    protected Object data;
    protected List<ErrorItem> errors;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ErrorItem {
        private String code;
        private String message;
    }

}
