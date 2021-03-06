package me.topits.exception;

import me.topits.enums.BaseStatusEnum;

/**
 * @author QingKe
 * @date 2020-09-14 17:55
 **/
public class InvalidTokenException extends RuntimeException{

    public InvalidTokenException() {
        super(BaseStatusEnum.ACCESS_TOKEN_INVALID.getMessage());
    }

    public InvalidTokenException(String message) {
        super(message);
    }

}
