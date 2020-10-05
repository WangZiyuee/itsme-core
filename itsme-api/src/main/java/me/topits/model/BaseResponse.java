package me.topits.model;

import lombok.Data;
import lombok.experimental.Accessors;
import me.topits.enums.BaseStatusEnum;

import java.io.Serializable;

/**
 * @author QingKe
 * @date 2020-09-08 21:01
 **/
@Data
@Accessors(chain = true)
public class BaseResponse<T> implements Serializable {

    private String code;
    private String message;
    private String subCode;
    private String subMessage;
    private T data;
    private Long timestamp;

    public BaseResponse(String code, String message, String subCode, String subMessage, T data) {
        this.code = code;
        this.message = message;
        this.subCode = subCode;
        this.subMessage = subMessage;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public <E extends BaseStatusEnum> BaseResponse(E statusEnum, T data) {
        this(statusEnum.getCode(), statusEnum.getMessage(), null, null, data);
    }

    private static ResponseBuilder status(String code, String message) {
        return new DefaultBuilder(code, message);
    }

    public static <E extends BaseStatusEnum> ResponseBuilder status(E statusEnum) {
        return new DefaultBuilder(statusEnum.getCode(), statusEnum.getMessage());
    }

    public static <T> BaseResponse<T> success(T data) {
        return status(BaseStatusEnum.SUCCESS).data(data);
    }

    public static <T> BaseResponse<T> success() {
        return status(BaseStatusEnum.SUCCESS).data(null);
    }

    public static <T, E extends BaseStatusEnum> BaseResponse<T> failure(E statusEnum) {
        return status(statusEnum).data(null);
    }

    public static <T, E extends  BaseStatusEnum> BaseResponse<T> failure(String message) {
        return status(BaseStatusEnum.FAILURE.getCode(), message).data(null);
    }

    public static <T, E extends  BaseStatusEnum> BaseResponse<T> failure(String code, String message) {
        return status(code, message).data(null);
    }

    public static <T, E extends BaseStatusEnum> BaseResponse<T> failure() {
        return status(BaseStatusEnum.FAILURE).data(null);
    }

    public interface ResponseBuilder {

        /**
         * 设置subCode
         *
         * @param subCode subCode
         * @return ResponseBuilder
         */
        ResponseBuilder subCode(String subCode);

        /**
         * 设置subMessage
         *
         * @param subMessage subMessage
         * @return ResponseBuilder
         */
        ResponseBuilder subMessage(String subMessage);

        /**
         * 设置数据并构建响应
         *
         * @param data data
         * @param <T>  T
         * @return BaseResponse
         */
        <T> BaseResponse<T> data(T data);

        /**
         * 构建响应
         *
         * @param <T> T
         * @return BaseResponse
         */
        <T> BaseResponse<T> build();
    }

    /**
     * 默认实现
     */
    private static class DefaultBuilder implements ResponseBuilder {

        private final String code;
        private final String message;
        private String subCode = null;
        private String subMessage = null;

        public DefaultBuilder(String code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public ResponseBuilder subCode(String subCode) {
            this.subCode = subCode;
            return this;
        }

        @Override
        public ResponseBuilder subMessage(String subMessage) {
            this.subMessage = subMessage;
            return this;
        }

        @Override
        public <T> BaseResponse<T> data(T data) {
            return new BaseResponse<>(this.code, this.message, this.subCode, this.subMessage, data);
        }

        @Override
        public <T> BaseResponse<T> build() {
            return new BaseResponse<>(this.code, this.message, this.subCode, this.subMessage, null);
        }
    }

}
