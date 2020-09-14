package me.topits.exception;

import lombok.extern.slf4j.Slf4j;
import me.topits.enums.BaseResponseStatusEnum;
import me.topits.model.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @author QingKe
 * @date 2020-09-14 12:08
 **/
@Slf4j
@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = {Exception.class, Error.class})
    @ResponseBody
    public BaseResponse exception(Exception e) {
        if (e instanceof InvalidTokenException) {
            InvalidTokenException invalidTokenException = ((InvalidTokenException) e);
            return BaseResponse.failure(BaseResponseStatusEnum.ACCESS_TOKEN_INVALID.getCode(),
                    invalidTokenException.getMessage());
        }

        if (e instanceof BaseException) {
            log.error("ExceptionHandle BaseException: ", e);
            BaseException baseException = ((BaseException) e);
            return BaseResponse.failure(baseException.getCode(), baseException.getMessage());
        }

        if (e instanceof IllegalArgumentException) {
            log.error("ExceptionHandle IllegalArgumentException: ", e);
            IllegalArgumentException illegalArgumentException = ((IllegalArgumentException) e);
            return BaseResponse.failure(BaseResponseStatusEnum.ILLEGAL_ARGUMENT.getCode(),
                    illegalArgumentException.getMessage());
        }

        if (e instanceof MethodArgumentNotValidException || e instanceof BindException) {
            log.error("ExceptionHandle MethodArgumentNotValidException or BindException: ", e);
            return BaseResponse.failure(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS.getReasonPhrase());
        }

        if (e instanceof NoHandlerFoundException) {
            return BaseResponse.failure(HttpStatus.NOT_FOUND.getReasonPhrase());
        }
        if (e instanceof HttpRequestMethodNotSupportedException) {
            return BaseResponse.failure(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase());
        }
        if (e instanceof HttpMessageNotReadableException) {
            return BaseResponse.failure(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase());
        }
        if (e instanceof HttpMediaTypeNotSupportedException) {
            return BaseResponse.failure(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase());
        }
        log.error("ExceptionHandle Unknown: ", e);
        return BaseResponse.failure(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }
}
