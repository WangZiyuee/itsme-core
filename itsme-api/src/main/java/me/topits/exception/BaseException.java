package me.topits.exception;

import me.topits.enums.BaseResponseStatusEnum;
import me.topits.utils.ParseUtil;

/**
 * @author QingKe
 * @date 2020-09-14 12:02
 **/
public class BaseException extends RuntimeException {

    private final String code;

    public BaseException(String message) {
        super(message);
        this.code = BaseResponseStatusEnum.FAILURE.getCode();
    }

    public BaseException(String format, Object... args) {
        super(ParseUtil.format(format, args));
        this.code = BaseResponseStatusEnum.FAILURE.getCode();
    }

    public BaseException(BaseResponseStatusEnum statusEnum) {
        super(statusEnum.getMessage());
        this.code = statusEnum.getCode();
    }

    public BaseException(BaseResponseStatusEnum statusEnum, String message) {
        super(message);
        this.code = statusEnum.getCode();
    }

    public String getCode() {
        return code;
    }
}
