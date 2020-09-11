package me.topits.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.experimental.Accessors;
import me.topits.enums.BaseResponseStatusEnum;

import java.io.Serializable;

/**
 * @author QingKe
 * @date 2020-09-08 21:01
 **/
@Data
@Accessors(chain = true)
public class BaseResponse implements Serializable {

    private Object data;
    private String code;
    private String message;
    private String subCode;
    private String subMessage;
    private Long timestamp;

    public BaseResponse() {
    }

    public static  BaseResponse success(Object data) {
        return new BaseResponse()
                .setCode(BaseResponseStatusEnum.SUCCESS.getCode())
                .setMessage(BaseResponseStatusEnum.SUCCESS.getMessage())
                .setData(data)
                .setTimestamp(System.currentTimeMillis());
    }

    public static BaseResponse success() {
        return new BaseResponse()
                .setCode(BaseResponseStatusEnum.SUCCESS.getCode())
                .setMessage(BaseResponseStatusEnum.SUCCESS.getMessage())
                .setData(new JSONObject())
                .setTimestamp(System.currentTimeMillis());
    }

    public static BaseResponse failure(String message) {
        return new BaseResponse()
                .setCode(BaseResponseStatusEnum.FAILURE.getCode())
                .setMessage(message)
                .setData(new JSONObject())
                .setTimestamp(System.currentTimeMillis());
    }

}
