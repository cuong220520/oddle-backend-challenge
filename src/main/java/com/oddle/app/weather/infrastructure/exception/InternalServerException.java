package com.oddle.app.weather.infrastructure.exception;

/**
 * @author cuongnd9
 * @date 15/02/2023
 * @project weather
 * @package com.oddle.app.weather.infrastructure.exception
 */
public class InternalServerException extends RuntimeException {

    public InternalServerException() {
        super();
    }

    public InternalServerException(String message) {
        super(message);
    }

    public InternalServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalServerException(Throwable cause) {
        super(cause);
    }

}
