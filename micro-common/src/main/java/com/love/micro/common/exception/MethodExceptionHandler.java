package com.love.micro.common.exception;

import com.love.common.exception.BizException;
import com.love.common.result.Result;
import io.sentry.Sentry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class MethodExceptionHandler implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(MethodExceptionHandler.class);

    @Autowired
    private Environment environment;

    private boolean prod;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (Arrays.asList(environment.getActiveProfiles()).contains("prod")) {
            this.prod = true;
        }
    }

    @ExceptionHandler(BizException.class)
    public Object handleError(BizException exception) {
        return Result.fail(exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public Result<Map<String, Object>> handleError(BindException exception) {
        Map<String, Object> errors = new HashMap<>();
        BindingResult bindingResult = exception.getBindingResult();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return Result.fail(String.valueOf(HttpStatus.BAD_REQUEST.value()), "validation error", errors);
    }

    @ExceptionHandler(ServletException.class)
    public Result<String> handleError(ServletException exception) {
        Sentry.captureException(exception);
        return Result.fail(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<String> handleError(Exception exception) {
        logger.error("", exception);
        Sentry.captureException(exception);
        if (!prod) {
            return Result.fail(exception.getMessage());
        }
        return Result.fail("unknown error");
    }

}
