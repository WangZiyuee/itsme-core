package me.topits.model;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import me.topits.enums.BaseResponseStatusEnum;

/**
 * @author Wang Ziyue
 * @since 2020/5/20 21:22
 * 通用API响应
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BaseResponse {
    private String code;
    private String message;
    private String bizCode;
    private String bizMessage;
    private Object data;
    private long timestamp;

    public BaseResponse(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public static BaseResponse parseResponse(String code, String message, Object data) {
        return new BaseResponse(code, message, data);
    }

    public static BaseResponse success() {
        return parseResponse(BaseResponseStatusEnum.SUCCESS.getCode(),
                BaseResponseStatusEnum.SUCCESS.getMessage(),
                new JSONObject());
    }

    public static BaseResponse success(Object data) {
        return parseResponse(BaseResponseStatusEnum.SUCCESS.getCode(),
                BaseResponseStatusEnum.SUCCESS.getMessage(),
                data);
    }

    public static BaseResponse failure() {
        return parseResponse(BaseResponseStatusEnum.FAILURE.getCode(),
                BaseResponseStatusEnum.FAILURE.getMessage(),
                new JSONObject());
    }

    public static <T extends BaseResponseStatusEnum> BaseResponse failure(T statusEnum) {
        return parseResponse(statusEnum.getCode(),
                statusEnum.getMessage(),
                new JSONObject());
    }

    public static BaseResponse failure(String message) {
        return parseResponse(BaseResponseStatusEnum.FAILURE.getCode(),
                message,
                new JSONObject());
    }

    public void setBiz(String bizCode, String bizMessage) {
        this.bizCode = bizCode;
        this.bizMessage = bizMessage;
    }


}
