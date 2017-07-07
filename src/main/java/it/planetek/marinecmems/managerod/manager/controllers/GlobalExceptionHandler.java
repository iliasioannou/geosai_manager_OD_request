package it.planetek.marinecmems.managerod.manager.controllers;

import it.planetek.marinecmems.managerod.processor.exceptions.ProcessingRequestAlreadyInQueueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Francesco Bruni - <bruni@planetek.it> - on 7/6/17.
 */
@ControllerAdvice
@Component
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map handle(MethodArgumentNotValidException exception) {
        return error(exception.getBindingResult().getFieldErrors()
                .stream()
                .map(field -> field.getField().concat(" -> ").concat(field.getDefaultMessage()))
                .collect(Collectors.toList()));
    }


    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map handle(ConstraintViolationException exception) {
        return error(exception.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()));
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map handle(ProcessingRequestAlreadyInQueueException exception) {
        log.error(exception.getMessage());
        return error("You cannot star a new processing until a previous one has not been marked as completed.");
    }

    private Map error(Object message) {
        return Collections.singletonMap("error", message);
    }
}
