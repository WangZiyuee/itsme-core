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
    SUCCESS("0000", "success"),
    FAILURE("0001", "failure"),
    API_NO_FOUND("0002", "api not found"),
    PARAMS_INVALID("0003", "request params invalid"),
    TOKEN_INVALID("0004", "token invalid"),
    SERVICE_ERROR("0010", "service error");

    private final String code;
    private final String message;


}
