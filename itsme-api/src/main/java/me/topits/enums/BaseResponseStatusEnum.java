package me.topits.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Wang Ziyue
 * @since 2020/5/20 21:22
 */
@Getter
@AllArgsConstructor
public enum BaseResponseStatusEnum {

    /** API响应基础枚举 */
    SUCCESS("0000", "Success"),
    FAILURE("0001", "Failure"),
    ACCESS_TOKEN_INVALID("0002", "Access Token Invalid"),
    API_NO_FOUND("0003", "Api Not Found"),
    ILLEGAL_ARGUMENT("0004", "Illegal Argument");

    private final String code;
    private final String message;

}
