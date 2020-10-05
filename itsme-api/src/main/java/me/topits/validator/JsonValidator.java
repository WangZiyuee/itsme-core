package me.topits.validator;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.topits.enums.BaseStatusEnum;
import me.topits.exception.BaseException;


/**
 * @author wangziyue
 */
@Slf4j
public class JsonValidator {

    public static <T> T toJavaObject(JSONObject json, Class<T> tClass) throws BaseException {
        try {
            T target = JSONObject.toJavaObject(json, tClass);
            BeanValidator.validate(target);
            return target;
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            throw new BaseException(BaseStatusEnum.ILLEGAL_ARGUMENT);
        }
    }

}
