package me.topits.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Wang Ziyue
 * @since 2020/5/20 21:22
 */
@Getter
@AllArgsConstructor
public enum BaseStatusEnum {

    /** API响应基础枚举 */
    SUCCESS("0000", "Success"),
    FAILURE("0001", "Failure"),
    API_NO_FOUND("0002", "Api Not Found"),
    ILLEGAL_ARGUMENT("0003", "Illegal Argument"),
    ACCESS_TOKEN_INVALID("1000", "Access Token Invalid"),
    AES_DECRYPTED_INVALID("1001", "Decrypted Invalid"),
    AES_ENCRYPTED_INVALID("1002", "Encrypted Failure");

    private final String code;
    private final String message;

}
