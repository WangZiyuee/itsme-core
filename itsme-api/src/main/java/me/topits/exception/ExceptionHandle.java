package me.topits.exception;

import lombok.extern.slf4j.Slf4j;
import me.topits.enums.BaseStatusEnum;
import me.topits.model.BaseResponse;
import org.springframework.http.HttpStatus;
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
    public BaseResponse<?> exception(Exception e) {
        if (e instanceof BaseException) {
            log.error("ExceptionHandle BaseException: ", e);
            BaseException baseException = ((BaseException) e);
            return BaseResponse.failure(baseException.getStatusEnum().getCode(), baseException.getMessage());
        }

        if (e instanceof IllegalArgumentException) {
            return BaseResponse.failure(BaseStatusEnum.ILLEGAL_ARGUMENT);
        }
        if (e instanceof NoHandlerFoundException) {
            return BaseResponse.failure(BaseStatusEnum.API_NO_FOUND);
        }

        log.error("ExceptionHandle Unknown", e);
        return BaseResponse.failure(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }
}
