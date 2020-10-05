package me.topits.exception;

import me.topits.enums.BaseStatusEnum;
import me.topits.utils.ParseUtil;

/**
 * @author QingKe
 * @date 2020-09-14 12:02
 **/
public class BaseException extends RuntimeException {

    private final BaseStatusEnum statusEnum;

    public BaseException(String message) {
        super(message);
        this.statusEnum = BaseStatusEnum.FAILURE;
    }

    public BaseException(String format, Object... args) {
        super(ParseUtil.format(format, args));
        this.statusEnum = BaseStatusEnum.FAILURE;
    }

    public BaseException(BaseStatusEnum statusEnum) {
        super(statusEnum.getMessage());
        this.statusEnum = statusEnum;
    }

    public BaseException(BaseStatusEnum statusEnum, String message) {
        super(message);
        this.statusEnum = statusEnum;
    }

    public BaseStatusEnum getStatusEnum() {
        return statusEnum;
    }
}
