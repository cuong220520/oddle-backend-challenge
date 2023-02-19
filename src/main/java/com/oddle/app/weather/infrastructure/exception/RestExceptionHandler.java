package com.oddle.app.weather.infrastructure.exception;

import com.oddle.app.weather.application.model.response.ErrorResponse;
import com.oddle.app.weather.application.model.response.Response;
import com.oddle.app.weather.application.model.response.ResponseFactory;
import com.oddle.app.weather.infrastructure.enumurate.ResponseStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author cuongnd9
 * @date 15/02/2023
 * @project weather
 * @package com.oddle.app.weather.infrastructure.exception
 */
@ControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleDefaultException(Exception ex) {
        return getErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleInternalServerException(InternalServerException ex) {
        return getErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        return getErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<Response.ErrorItem> errors = new LinkedList<>();
        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        if (!CollectionUtils.isEmpty(objectErrors)) {
            objectErrors.stream().forEach((e) -> {
                String message = messageSource.getMessage(e.getDefaultMessage(), null, LocaleContextHolder.getLocale());
                String fieldName = ((FieldError) e).getField();
                errors.add(getErrorResponseFromMessage(message, fieldName));
            });
        }

        return new ResponseEntity(
                ResponseFactory.produce(
                        ResponseStatusEnum.FAIL,
                        null,
                        errors
                ), HttpStatus.BAD_REQUEST
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(ConstraintViolationException ex) {

        Set<ConstraintViolation<?>> objectErrors = ex.getConstraintViolations();
        List<Response.ErrorItem> errors = new LinkedList<>();

        if (!CollectionUtils.isEmpty(objectErrors)) {
            objectErrors.stream().forEach(e -> {
                String message = messageSource.getMessage(e.getMessage(), null, LocaleContextHolder.getLocale());
                String fieldName = e.getPropertyPath().toString();
                errors.add(getErrorResponseFromMessage(message, fieldName));
            });
        }

        return new ResponseEntity(
                ResponseFactory.produce(
                        ResponseStatusEnum.FAIL,
                        null,
                        errors
                ), HttpStatus.BAD_REQUEST
        );
    }

    private ResponseEntity<ErrorResponse> getErrorResponse(Exception ex, HttpStatus status) {
        String message = messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale());
        return new ResponseEntity(
                ResponseFactory.produce(
                        ResponseStatusEnum.FAIL,
                        null,
                        Collections.singletonList(getErrorResponseFromMessage(message))
                ), status
        );
    }

    private Response.ErrorItem getErrorResponseFromMessage(String message) {
        String errorMessage = message;
        String errorCode = "999";

        if (message.contains("-")) {
            errorMessage = message.substring(message.indexOf("-") + 1);
            errorCode = message.substring(0, message.indexOf("-"));
        }

        return Response.ErrorItem.builder().message(errorMessage).code(errorCode).build();
    }

    private Response.ErrorItem getErrorResponseFromMessage(String message, String field) {
        String errorMessage = new StringBuilder().append(field).append(" ").append(message).toString();
        String errorCode = "999";

        if (message.contains("-")) {
            errorMessage =
                    new StringBuilder()
                            .append(field)
                            .append(" ")
                            .append(message.substring(message.indexOf("-") + 1)).toString();
            errorCode = message.substring(0, message.indexOf("-"));
        }

        return Response.ErrorItem.builder().message(errorMessage).code(errorCode).build();
    }

}
