package com.love.api.common.exception;

import com.love.common.exception.BizException;
import com.love.common.result.Result;
import feign.FeignException;
import feign.codec.DecodeException;
import io.sentry.Sentry;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
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
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.ServletException;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
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
        StringBuilder errors = new StringBuilder();
        BindingResult bindingResult = exception.getBindingResult();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.append(fieldError.getDefaultMessage()).append("\n");
        }
        return Result.fail(String.valueOf(HttpStatus.BAD_REQUEST.value()), errors.toString());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Result<String> handleError(ConstraintViolationException exception) {
        return Result.fail(exception.getMessage());
    }

    @ExceptionHandler(ServletException.class)
    public Object handleError(ServletException exception) {
        Sentry.captureException(exception);
        return Result.fail(exception.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Object handleError(MaxUploadSizeExceededException exception) {
        Throwable ex = exception.getCause();
        long size = 0;
        if (ex instanceof FileSizeLimitExceededException) {
            size = (( FileSizeLimitExceededException ) ex).getPermittedSize();
        } else if (ex instanceof SizeLimitExceededException) {
            size = (( SizeLimitExceededException ) ex).getPermittedSize();
        }
        return Result.fail("max upload size exceeded : " + size + "bytes");
    }

    @ExceptionHandler(FeignException.class)
    public Result<String> handleError(FeignException exception) {
        logger.error("", exception);
        Sentry.captureException(exception);
        int status = exception.status();
        if (status == 503) {
            return Result.fail("503", "service not available");
        } else if (status == 404) {
            return Result.fail("404", "request 404");
        } else if (status == 405) {
            return Result.fail("405", "method not supported");
        }
        return Result.fail(exception.getMessage());
    }

    @ExceptionHandler(DecodeException.class)
    public Result<String> handleError(DecodeException exception) {
        logger.error("", exception);
        Throwable error = exception.getCause();
        if (error instanceof BizException) {
            BizException be = ( BizException ) error;
            return Result.fail(be.getCode(), be.getMessage());
        } else {
            Sentry.captureException(exception);
        }
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
